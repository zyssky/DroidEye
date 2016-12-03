package com.example.administrator.droideye.Utils;

import android.content.Context;
import android.text.format.Formatter;

/**
 * Created by wand on 2016/12/3.
 */

public class FileUtils {

    public String formatFileSize(Context context, long num){

        return Formatter.formatFileSize(context,num);
    }
}
