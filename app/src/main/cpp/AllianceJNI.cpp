//
// Created by kaitlyn on 25/09/20.
//
#include <jni.h>
#include <libDS.h>

extern "C"
JNIEXPORT jlong JNICALL
Java_com_redrield_androidds_ds_Alliance_createRed(JNIEnv *env, jobject thiz, jint n) {
    return (jlong) DS_Alliance_new_red(n);
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_redrield_androidds_ds_Alliance_createBlue(JNIEnv *env, jobject thiz, jint n) {
    return (jlong) DS_Alliance_new_blue(n);
}
