package com.example.administrator.droideye.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.example.administrator.droideye.KEY;
import com.example.administrator.droideye.Models.DataBase.dbOpt;
import com.example.administrator.droideye.Models.Traffic;
import com.example.administrator.droideye.ProcessHandler;
import com.example.administrator.droideye.Setting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.administrator.droideye.ProcessHandler.TAG;

public class MonitorService extends Service {
    long sleeptime;
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
        sendBroadcast();
        dbOpt dbopt = new dbOpt(this);
        List<Traffic> traffics = dbopt.userdef_query("traffic", "SELECT * FROM traffic", null);
        trafficMonit trafficMonitor = new trafficMonit(dbopt, traffics);
        trafficMonitor.start();

        Log.d(TAG, "onStartCommand: "+sleeptime);
        return super.onStartCommand(intent, flags, startId);
    }


    private void sendBroadcast(){
        Intent intent = new Intent(this,StartUpReceiver.class);
//        intent.putExtra(KEY.SLEEPTIME,10000);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+sleeptime,pendingIntent);
    }



    private void mission1(){
        ProcessHandler.init(this);
        Setting.init(this);
        List<String> tobekill = new ArrayList<String>();
//        int deadline = Setting.getInstance().getDDurationOfBackgroundProcess();
        sleeptime = Setting.getInstance().getDDurationOfBackgroundProcess();
        HashMap<String,ProcessHandler.UsedRecord> recordHashMap = ProcessHandler.getInstance().getAppUsedRecords();
        for (Map.Entry<String, ProcessHandler.UsedRecord> entry : recordHashMap.entrySet()){
            if(entry.getValue().seconds>sleeptime)
                tobekill.add(entry.getKey());
            else{
                long tolivetime = sleeptime-entry.getValue().seconds;
                sleeptime = sleeptime>tolivetime?tolivetime:sleeptime;
            }
        }
        for (String dead:
             tobekill) {
            ProcessHandler.getInstance().forceStopProcess(dead);
        }

    }


}
