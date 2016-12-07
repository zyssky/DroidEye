package com.example.administrator.droideye.Utils;

import android.content.Context;
import android.text.format.Formatter;
import android.util.Log;

import com.example.administrator.droideye.Models.Configuration;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by wand on 2016/12/3.
 */

public class FileUtils {

    private FileUtils(){
        //UnConstructable.
    }

    //Return with a proper representation of filesize in KB,MB,GB,etc.
    public static String formatFileSize(Context context, long num){

        return Formatter.formatFileSize(context,num);
    }

    //Default File Operations are stored and get from /data/data/packagename/files
    public static boolean createFile(String filename){

        File file = new File(Configuration.defaultFilePath + "/" + filename);
        if  (!file.exists()){
            try {
                file.createNewFile();
                return true;
            }catch (Exception e){
                Log.d(Configuration.file_opt_error, e.toString());
                return false;
            }
        }
        else{
            return true;
        }
    }

    //Exp: /data/data/com.android.good , tempfile
    public static boolean createFile(String path, String filename){

        File file = new File(path + filename);
        if  (!file.exists()){
            try {
                file.createNewFile();
                return true;
            }catch (Exception e){
                Log.d(Configuration.file_opt_error, e.toString());
                return false;
            }
        }
        else{
            return true;
        }
    }

    //Attention: If create failed , There isn't any attention to be drawed.
    public static boolean createFolder(String path , String foldername){

        File file = new File(path + foldername);
        if  (!file.exists() && !file.isDirectory()){
            file.mkdirs();
            return true;
        }else{
            return true;
        }
    }


    public static byte[] readFromFile(){

        byte[] res = null;
        return res;
    }

    public static boolean writetoFile(){

        return true;
    }

    public static boolean deleteFile(String filename){

        return true;
    }

}
