cmake_minimum_required(VERSION 3.10.2)
project("edgedetectionapp")

# OpenCV include directory
include_directories(
        ${CMAKE_SOURCE_DIR}/opencv/include
)

# Add native-lib.cpp
add_library(
        native-lib
        SHARED
        native-lib.cpp
)

# Link all OpenCV .so files in jniLibs
add_library( opencv_java4 SHARED IMPORTED )
set_target_properties( opencv_java4 PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libopencv_java4.so )

find_library(
        log-lib
        log)

target_link_libraries(
        native-lib
        opencv_java4
        ${log-lib})
