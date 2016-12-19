package com.example.administrator.droideye;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/16.
 */

public class Setting {
    private int whitelistsize = 0;
    private SharedPreferences.Editor processPreferencesEditor;
    private SharedPreferences processPreferences;
    private SharedPreferences whitelistProference;
    private SharedPreferences.Editor whitelistProferenceEditor;
    private static Setting setting;
    private static boolean isInited = false;

    private static long defduration = 1800000;

    public final static String TAG = Setting.class.getSimpleName();

    private Setting(Context context){
        processPreferencesEditor = context.getSharedPreferences(KEY.PROCESS, Context.MODE_PRIVATE).edit();
        processPreferences = context.getSharedPreferences(KEY.PROCESS,Context.MODE_PRIVATE);
        whitelistProferenceEditor = context.getSharedPreferences(KEY.WHITELIST, Context.MODE_PRIVATE).edit();
        whitelistProference = context.getSharedPreferences(KEY.WHITELIST,Context.MODE_PRIVATE);
    }

    public static void init(Context context){
        setting = new Setting(context);
        isInited = true;
    }

    public Setting getInstance(){
        if(!isInited)
            Log.d(TAG, "getInstance: without initing !!");
        return setting;
    }

    public void setDurationOfBackgroundProcess(long lastmillisecond){
        processPreferencesEditor.putLong(KEY.DURATION,lastmillisecond);
        processPreferencesEditor.commit();
    }

    public long getDDurationOfBackgroundProcess(){
        return processPreferences.getLong(KEY.DURATION,defduration);
    }

    public void extendWhiteList(List<String> whitelist){
        for (String item :
                whitelist) {
            whitelistProferenceEditor.putString(item,item);
        }
    }


    public void deleteFromWhiteList(List<String> whitelist){
        for (String item :
                whitelist) {
            whitelistProferenceEditor.remove(item);
        }
    }

    public boolean isInWhiteList(String item){
        Map map = whitelistProference.getAll();
        return map.containsKey(item);
    }
}
