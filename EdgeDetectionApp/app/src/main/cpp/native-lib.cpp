#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_myapp_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    return env->NewStringUTF("NDK is working!");
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_edgedetectionapp_MainActivity_processFrame(
        JNIEnv* env,
        jobject /* this */,
        jbyteArray data,
        jint width,
        jint height
) {
    // For now, just verify JNI is working
    jbyte* bytes = env->GetByteArrayElements(data, nullptr);

    // IMPORTANT: Later we will convert ByteArray → cv::Mat here.
    // For now we do nothing.

    env->ReleaseByteArrayElements(data, bytes, 0);
}

