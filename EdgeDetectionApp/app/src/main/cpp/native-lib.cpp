#include <jni.h>
#include <opencv2/opencv.hpp>
#include <android/log.h>

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, "JNI_OPENCV", __VA_ARGS__);
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, "JNI_OPENCV", __VA_ARGS__);

extern "C"
JNIEXPORT void JNICALL
Java_com_example_edgedetectionapp_MainActivity_processFrame(
        JNIEnv* env,
        jobject /* this */,
        jbyteArray data,
        jint width,
        jint height
) {
    // 1. Get pointer to Java ByteArray
    jbyte* bytePtr = env->GetByteArrayElements(data, nullptr);
    unsigned char* frameData = reinterpret_cast<unsigned char*>(bytePtr);

    // 2. Wrap into cv::Mat (ARGB_8888 from Bitmap)
    cv::Mat argb(height, width, CV_8UC4, frameData);

    // 3. Convert ARGB → BGRA (OpenCV friendly)
    cv::Mat bgra;
    cv::cvtColor(argb, bgra, cv::COLOR_RGBA2BGRA);

    // (Temporary test – just log)
    LOGI("Frame received and converted to cv::Mat: %dx%d", bgra.cols, bgra.rows);

    // 4. Release buffer
    env->ReleaseByteArrayElements(data, bytePtr, JNI_ABORT);
}
