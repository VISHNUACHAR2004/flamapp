# Path to the folder that contains OpenCVConfig.cmake
set(OpenCV_DIR ${CMAKE_SOURCE_DIR}/../../opencv/native/jni)

find_package(OpenCV REQUIRED)

target_link_libraries(
        native-lib
        ${OpenCV_LIBS}
)
