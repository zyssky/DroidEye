package com.example.administrator.droideye;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.droideye.Service.MonitorService;

import java.io.File;

public class MainActivity extends AppCompatActivity implements InteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainView mainView = (MainView) findViewById(R.id.activity_main);
        mainView.init();
        MainController mainController = new MainController(mainView,this);
        startService(new Intent(this, MonitorService.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void close() {
        finish();
    }
}
