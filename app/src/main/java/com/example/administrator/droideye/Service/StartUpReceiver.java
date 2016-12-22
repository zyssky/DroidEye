package com.example.administrator.droideye.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.TimeUtils;

import java.sql.Time;

public class StartUpReceiver extends BroadcastReceiver {
    public StartUpReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent intent1 = new Intent(context,MonitorService.class);
//        Thread.sleep();
        context.startService(intent1);
    }
}
