#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring

JNICALL
Java_frame_de_com_deframe_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_de_frame_activity_MainActivity_stringFromJNI(JNIEnv *env, jobject instance) {

    // TODO


    return env->NewStringUTF(returnValue);
}