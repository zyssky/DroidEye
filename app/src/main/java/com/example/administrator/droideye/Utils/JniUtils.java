package com.example.administrator.droideye.Utils;

/**
 * Created by wand on 2016/12/17.
 */

public class JniUtils {

    static{
        System.loadLibrary("jnilibs");
    }
    public static native String hellojni();
//    public native int test();
}
