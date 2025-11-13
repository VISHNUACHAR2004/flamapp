package com.example.edgedetectionapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.os.Build
import android.os.Bundle
import android.util.Size
import android.view.Surface
import android.view.TextureView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.nio.ByteBuffer

class MainActivity : AppCompatActivity() {

    // JNI function
    external fun processFrame(data: ByteArray, width: Int, height: Int)

    // Proper Kotlin initializer block
    init {
        System.loadLibrary("native-lib")
    }

    private lateinit var textureView: TextureView
    private lateinit var cameraManager: CameraManager
    private var cameraDevice: CameraDevice? = null
    private var previewSession: CameraCaptureSession? = null

    private val CAMERA_PERMISSION_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textureView = findViewById(R.id.textureView)
        cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager

        // Detect when TextureView is ready → then ask camera permission
        textureView.surfaceTextureListener = surfaceTextureListener
    }

    // TextureView listener
    private val surfaceTextureListener = object : TextureView.SurfaceTextureListener {
        @RequiresApi(Build.VERSION_CODES.P)
        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            checkCameraPermission()
        }

        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean = true

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
            val bitmap = textureView.bitmap ?: return

            val width = bitmap.width
            val height = bitmap.height

            val buffer = ByteBuffer.allocate(bitmap.byteCount)
            bitmap.copyPixelsToBuffer(buffer)
            val byteArray = buffer.array()

            // Send frame to C++
            processFrame(byteArray, width, height)
        }
    }

    // Permission check
    @RequiresApi(Build.VERSION_CODES.P)
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        } else {
            openCamera()
        }
    }

    // Permission result
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // Open camera
    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("MissingPermission")
    private fun openCamera() {
        val cameraId = cameraManager.cameraIdList[0]

        cameraManager.openCamera(
            cameraId,
            ContextCompat.getMainExecutor(this),
            cameraDeviceCallback
        )
    }

    // Camera state callback
    private val cameraDeviceCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(device: CameraDevice) {
            cameraDevice = device
            startCameraPreview()
        }

        override fun onDisconnected(device: CameraDevice) {
            device.close()
        }

        override fun onError(device: CameraDevice, error: Int) {
            device.close()
        }
    }

    // Start camera preview
    private fun startCameraPreview() {
        val surfaceTexture = textureView.surfaceTexture ?: return
        surfaceTexture.setDefaultBufferSize(1080, 1920)

        val previewSurface = Surface(surfaceTexture)
        val previewRequestBuilder =
            cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)

        previewRequestBuilder.addTarget(previewSurface)

        cameraDevice!!.createCaptureSession(
            listOf(previewSurface),
            object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) {
                    previewSession = session

                    previewRequestBuilder.set(
                        CaptureRequest.CONTROL_MODE,
                        CameraMetadata.CONTROL_MODE_AUTO
                    )

                    session.setRepeatingRequest(previewRequestBuilder.build(), null, null)
                }

                override fun onConfigureFailed(session: CameraCaptureSession) {}
            },
            null
        )
    }

    override fun onDestroy() {
        cameraDevice?.close()
        previewSession?.close()
        super.onDestroy()
    }
}
