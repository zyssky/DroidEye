package com.example.administrator.droideye.ProcessMonitor;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.droideye.Models.Configuration;
import com.example.administrator.droideye.Models.StatusLogger;
import com.example.administrator.droideye.R;
import com.example.administrator.droideye.Service.MonitorService;

import java.io.File;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class ProcessActivity extends AppCompatActivity implements InteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StartWorks stwork = new StartWorks();
        stwork.start();
        ProcessView processView = (ProcessView) findViewById(R.id.activity_main);
        processView.init();
        ProcessController processController = new ProcessController(processView,this);
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



class StartWorks extends Thread{

    public void SerializeStatus(){

        File file = new File(Configuration.stautsloggerpath);
        if (file.exists()){

            //Already there , not first Run.
            Configuration.isFirstRun = false;
            return;
        }
        else{


            //Serialize statuslogger first
            try{
                StatusLogger stlogger = new StatusLogger();
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(stlogger);
                oos.flush();
                oos.close();
                fos.close();
            }catch(Exception e){

                Log.d(Configuration.file_opt_error, e.toString());
            }

        }
    }


    @Override
    public void run(){

        SerializeStatus();
    }

}
