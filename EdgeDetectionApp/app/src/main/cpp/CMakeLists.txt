cmake_minimum_required(VERSION 3.10.2)

project("flamapp")

set(OpenCV_DIR ${CMAKE_SOURCE_DIR}/../../opencv/native/jni)
find_package(OpenCV REQUIRED)

add_library(
        native-lib
        SHARED
        native-lib.cpp)

find_library(
        log-lib
        log)

target_link_libraries(
        native-lib
        ${log-lib}
        ${OpenCV_LIBS}
)
