package com.example.administrator.droideye.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;

import com.example.administrator.droideye.KEY;


public class StartUpReceiver extends BroadcastReceiver {
    public StartUpReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d("receive", "onReceive: ");
//        Intent intent1 = new Intent(context,MonitorService.class);
//        Bundle bundle = intent.getExtras();
//        long time = bundle.getLong(KEY.SLEEPTIME,-1);
//        if(time>0)
//            try {
//                Log.d("sleep", "onReceive: sleeping"+time);
//                Thread.sleep(time);
//                Log.d("sleep", "onReceive: finish sleeping");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        context.startService(new Intent(context,MonitorService.class));
    }
}
