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
``

  ## Project Structure
```bash 
  EdgeDetectionApp/
│
├── app/
│   └── src/main/
│       ├── java/com/example/edgedetectionapp/
│       │   └── MainActivity.kt
│       ├── cpp/
│       │   ├── native-lib.cpp
│       │   ├── FrameProcessor.cpp
│       │   └── CMakeLists.txt
│       ├── jniLibs/
│       │   └── arm64-v8a/
│       │       ├── libopencv_java4.so
│       │       └── libc++_shared.so
│       ├── res/layout/activity_main.xml
│       └── AndroidManifest.xml
│
└── README.md
```

###  Setup & Installation
#### 1️⃣ Clone the repository
```bash
git clone https://github.com/your-username/EdgeDetectionApp.git
cd EdgeDetectionApp
```

#### 2️⃣ Download OpenCV Android SDK

Official link:
**https://opencv.org/releases/**

Extract → go to:

**OpenCV-android-sdk/sdk/native/jni/arm64-v8a/**


Copy the following into your project:
```bash
app/src/main/jniLibs/arm64-v8a/
 ├── libopencv_java4.so
 └── libc++_shared.so
```
#### 3️⃣ Sync project

Android Studio → File → Sync Project with Gradle Files

### ▶️ How to Run

- Open project in Android Studio

- Connect a real ARM64 Android device

Enable:

- Developer Mode

- USB Debugging

- Click Run

- Grant camera permission

- App launches with:

- Working camera preview

- Native OpenCV test response (OpenCV is working!)
