package com.example.administrator.droideye;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
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

    private static int defduration = 1800000;

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
        setting.firstStart(context);
    }

    public static Setting getInstance(){
        if(!isInited)
            Log.d(TAG, "getInstance: without initing !!");
        return setting;
    }

    private void firstStart(Context context){
        SharedPreferences preferences = context.getSharedPreferences(KEY.ADDDEFAULTWHITELIST,Context.MODE_PRIVATE);
        if(!preferences.getBoolean(KEY.INITWHITELISTFLAG,false)){
            List<ApplicationInfo> list = ProcessHandler.getInstance().getInstalledApps();
            for (ApplicationInfo app :
                    list) {
                if (ProcessHandler.getInstance().isInSystem(app))
                    setting.addToWhiteList(app.processName);
            }
            preferences.edit().putBoolean(KEY.INITWHITELISTFLAG,true).commit();
        }
    }

    public void setDurationOfBackgroundProcess(long lastmillisecond){
        processPreferencesEditor.putLong(KEY.DURATION,lastmillisecond);
        processPreferencesEditor.commit();
    }

    public int getDDurationOfBackgroundProcess(){
        return processPreferences.getInt(KEY.DURATION,defduration);
    }

    public void addToWhiteList(String item){
        whitelistProferenceEditor.putString(item,item);
        whitelistProferenceEditor.commit();
    }

    public void deleteFromWhiteList(String item){
        whitelistProferenceEditor.remove(item);
        whitelistProferenceEditor.commit();
    }

    public boolean isInWhiteList(String item){
        Map map = whitelistProference.getAll();
        return map.containsKey(item);
    }
}
