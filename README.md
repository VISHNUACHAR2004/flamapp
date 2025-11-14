# 📱 EdgeDetectionApp  
Real-time camera processing pipeline using Android + NDK + OpenCV.

This project demonstrates a complete hybrid processing setup including:

- Android Camera2 live preview  
- JNI frame pipeline  
- C++ native processing  
- OpenCV integration  
- Future extensions for WebGL, shaders, and real-time image effects  

The current version includes a working Camera2 preview and fully integrated OpenCV native backend.

---

# 🚀 Features Implemented

## ✔ Android (Kotlin)
- Camera2 API live preview using **TextureView**
- Runtime camera permission handling
- Stable camera session with proper `SurfaceTexture` callbacks
- Activity built with an **AppCompat** theme (fixed crash issue)
- Native bindings through `System.loadLibrary("native-lib")`
- Test bridge between Android ↔ JNI (OpenCV validation)

## ✔ Native (C++ / NDK)
- Full OpenCV Android SDK integration
- All required `.so` native libraries correctly imported (arm64-v8a)
- CMake integration with OpenCV headers and libs
- Sample native function to test OpenCV:
  ```cpp
  cv::Mat::eye(...);
