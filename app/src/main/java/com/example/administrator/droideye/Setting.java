package com.example.administrator.droideye;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.example.administrator.droideye.HOOKS.Wakelocks;

import java.util.List;
import java.util.Map;

import de.robv.android.xposed.XSharedPreferences;

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

    private SharedPreferences m_prefs ;

    public final static String TAG = Setting.class.getSimpleName();

    private Setting(Context context){
        processPreferencesEditor = context.getSharedPreferences(KEY.PROCESS, Context.MODE_PRIVATE).edit();
        processPreferences = context.getSharedPreferences(KEY.PROCESS,Context.MODE_PRIVATE);
        whitelistProferenceEditor = context.getSharedPreferences(KEY.WHITELIST, Context.MODE_PRIVATE).edit();
        whitelistProference = context.getSharedPreferences(KEY.WHITELIST,Context.MODE_PRIVATE);
        m_prefs = context.getSharedPreferences(KEY.XPOSESETTING,Context.MODE_PRIVATE);
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

    public void changeServiceStatus(String serviceName,boolean status){
        m_prefs.edit().putBoolean("service_" + serviceName + "_enabled",status).commit();

        return;
    }

    public void changeWakelockStatus(String wakelockName,boolean status){
        m_prefs.edit().putBoolean("wakelock_" + wakelockName + "_enabled",status).commit();

        return;
    }

    public void changeAlarmStatus(String alarmName,boolean status){
        m_prefs.edit().putBoolean("alarm_" + alarmName + "_enabled",status).commit();

        return;
    }

    public void changeWakelockAllowTime(String wakelockName,long time){
        m_prefs.edit().putLong("wakelock_" + wakelockName + "_seconds",time).commit();

        return;
    }

    public void changeServiceAllowTime(String serviceName,long time){
        m_prefs.edit().putLong("service_" + serviceName + "_seconds",time).commit();

        return;
    }

    public void changeAlarmAllowTime(String alarmName,long time){
        m_prefs.edit().putLong("alarm_" + alarmName + "_seconds",time).commit();

        return;
    }

    public void setDurationOfBackgroundProcess(long lastmillisecond){
        processPreferencesEditor.putLong(KEY.DURATION,lastmillisecond);
        processPreferencesEditor.commit();
    }

    public boolean getAlarmStatus(String name){
        return m_prefs.getBoolean("alarm_" + name + "_enabled",false);
    }

    public boolean getServiceStatus(String name){
        return m_prefs.getBoolean("service_" + name + "_enabled",false);
    }

    public boolean getWakelocktatus(String name){
        return m_prefs.getBoolean("wakelock_" + name + "_enabled",false);
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
