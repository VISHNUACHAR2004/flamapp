#include <jni.h>
#include <string>
#include <opencv2/opencv.hpp>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_edgedetectionapp_MainActivity_testOpenCV(JNIEnv* env, jobject /* this */) {

    cv::Mat testMat = cv::Mat::eye(3, 3, CV_8UC1);

    if (!testMat.empty()) {
        return env->NewStringUTF("OpenCV is working!");
    } else {
        return env->NewStringUTF("OpenCV FAILED!");
    }
}
