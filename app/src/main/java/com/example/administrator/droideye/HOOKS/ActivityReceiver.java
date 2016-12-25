package com.example.administrator.droideye.HOOKS;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;

public class ActivityReceiver extends BroadcastReceiver {

    public static final String STATS_REFRESHED_ACTION = "com.example.administrator.droideye.STATS_REFRESHED_ACTION";
    public static final String CREATE_FILES_ACTION = "com.example.administrator.droideye.CREATE_FILES_ACTION";
    public static final String RESET_FILES_ACTION = "com.example.administrator.droideye.RESET_FILES_ACTION";
    public static final String PUSH_NETWORK_STATS = "com.example.administrator.droideye.PUSH_NETWORK_STATS";

    public ActivityReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        String action = intent.getAction();
//        if (action.equals(CREATE_FILES_ACTION)) {
//            UnbounceStatsCollection.getInstance().createFiles(context);
//        } else if (action.equals(RESET_FILES_ACTION)) {
//            UnbounceStatsCollection.getInstance().recreateFiles(context);
//        } else if (action.equals(PUSH_NETWORK_STATS)) {
//            UnbounceStatsCollection.getInstance().pushStatsToNetworkInternal(context);
//        }

    }
}
