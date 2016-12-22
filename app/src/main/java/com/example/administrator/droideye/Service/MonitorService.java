package com.example.administrator.droideye.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.administrator.droideye.ProcessHandler;
import com.example.administrator.droideye.Setting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.administrator.droideye.ProcessHandler.TAG;

public class MonitorService extends Service {
    int sleeptime;
    public MonitorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mission1();
        return super.onStartCommand(intent, flags, startId);
    }



    private void mission1(){
        ProcessHandler.init(this);
        Setting.init(this);
        List<String> tobekill = new ArrayList<String>();
        int deadline = Setting.getInstance().getDDurationOfBackgroundProcess();
        sleeptime = Setting.getInstance().getDDurationOfBackgroundProcess();
        HashMap<String,ProcessHandler.UsedRecord> recordHashMap = ProcessHandler.getInstance().getAppUsedRecords();
        for (Map.Entry<String, ProcessHandler.UsedRecord> entry : recordHashMap.entrySet()){
            if(entry.getValue().seconds>deadline)
                tobekill.add(entry.getKey());
            else{
                int tolivetime = sleeptime-entry.getValue().seconds;
                sleeptime = sleeptime>tolivetime?tolivetime:sleeptime;
            }
        }
        for (String dead:
             tobekill) {
            ProcessHandler.getInstance().forceStopProcess(dead);
        }

    }
}