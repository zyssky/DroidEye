//
// Created by wand on 2016/12/18.
//

#include "com_example_administrator_droideye_Utils_JniUtils.h"
#include <jni.h>

JNIEXPORT jstring JNICALL Java_com_example_administrator_droideye_Utils_JniUtils_hellojni
        (JNIEnv *env, jobject obj){

    return (*env)->NewStringUTF(env,"Hello~This is a String comes from C~Jni!");
}

int test(int i){

    return i-1;
}
