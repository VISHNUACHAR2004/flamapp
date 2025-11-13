cmake_minimum_required(VERSION 3.10.2)

project("flamapp")

# ----------- OpenCV -------------
set(OpenCV_DIR ${CMAKE_SOURCE_DIR}/../../opencv/native/jni)
find_package(OpenCV REQUIRED)

# ----------- Native Library -----
add_library(
        native-lib
        SHARED
        native-lib.cpp)

find_library(
        log-lib
        log)

# ----------- Link Libraries -----
target_link_libraries(
        native-lib
        ${log-lib}
        ${OpenCV_LIBS}
)
