package com.example.administrator.droideye;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.droideye.Models.Configuration;
import com.example.administrator.droideye.Models.StatusLogger;
import com.example.administrator.droideye.Service.MonitorService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity implements InteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StartWorks stwork = new StartWorks();
        stwork.start();
        MainView mainView = (MainView) findViewById(R.id.activity_main);
        mainView.init();
        MainController mainController = new MainController(mainView,this);
        startService(new Intent(this, MonitorService.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
