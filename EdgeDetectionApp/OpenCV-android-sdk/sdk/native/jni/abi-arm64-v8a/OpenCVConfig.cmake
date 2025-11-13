# Path to the folder that contains OpenCVConfig.cmake
set(OpenCV_DIR ${CMAKE_SOURCE_DIR}V:/projects/flamapp/OpenCV-android-sdk/sdk/native/jni/abi-arm64-v8a)

find_package(OpenCV REQUIRED)

target_link_libraries(
        native-lib
        ${OpenCV_LIBS}
)
