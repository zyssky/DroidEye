package com.example.administrator.droideye.HOOKS;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.administrator.droideye.R;

import java.io.File;

public class WakeUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button alarm;
//    private Button service;
//    private Button wakelock;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);
        alarm = (Button) findViewById(R.id.alarm);
//        service = (Button) findViewById(R.id.service);
//        wakelock = (Button) findViewById(R.id.wakeup);
        listView = (ListView) findViewById(R.id.wakeuplist);
        listView.setAdapter(generateVariousAdapter(AlarmsAdapter.ALARMS));
        alarm.setOnClickListener(this);
//        service.setOnClickListener(this);
//        wakelock.setOnClickListener(this);
    }

    public AlarmsAdapter generateVariousAdapter(int adapterType) {
        switch (adapterType) {
            case 0:
                AlarmsAdapter adapter =new  AlarmsAdapter(this, UnbounceStatsCollection.getInstance().toAlarmArrayList(this),
                        R.layout.alarms_listitem, new int[]{R.id.AlarmIcon, R.id.AlarmName, R.id.AlarmAllow, R.id.AlarmBlock, R.id.waketimelable, R.id.waketime},AlarmsAdapter.ALARMS);
                return adapter;
            case 1:
                return new AlarmsAdapter(this, UnbounceStatsCollection.getInstance().toServiceArrayList(this),
                        R.layout.alarms_listitem, new int[]{R.id.AlarmIcon, R.id.AlarmName, R.id.AlarmAllow, R.id.AlarmBlock, R.id.waketimelable, R.id.waketime},AlarmsAdapter.SERVICES);
            case 2:
                return new AlarmsAdapter(this,UnbounceStatsCollection.getInstance().toWakelockArrayList(this),
                        R.layout.alarms_listitem,new int[]{R.id.AlarmIcon,R.id.AlarmName,R.id.AlarmAllow,R.id.AlarmBlock,R.id.waketimelable,R.id.waketime},AlarmsAdapter.WAKELOCKS);
            default:
                return null;
        }
    }

    public static int position = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.alarm:
                position+=1;
                position%=3;
                listView.setAdapter(generateVariousAdapter(position));
                break;
//            case R.id.service:
//                listView.setAdapter(generateVariousAdapter(AlarmsAdapter.SERVICES));
//                break;
//            case R.id.wakeup:
//                listView.setAdapter(generateVariousAdapter(AlarmsAdapter.WAKELOCKS));
//                break;
        }
    }
    public boolean isUnbounceServiceRunning() {
        //The Unbounce hook changes this to true.
        return false;
    }

    public String getAmplifyKernelVersion() {
        //The Unbounce hook changes this to true.
        return "0";
    }



    public boolean isXposedRunning() {
//        return true;
        return new File("/data/data/de.robv.android.xposed.installer/bin/XposedBridge.jar").exists();
    }

}
