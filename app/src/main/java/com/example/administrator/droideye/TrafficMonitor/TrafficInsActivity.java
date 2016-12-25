package com.example.administrator.droideye.TrafficMonitor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

//import com.example.administrator.droideye.JniUtils.jnitest;
import com.example.administrator.droideye.Models.Configuration;
import com.example.administrator.droideye.Models.DataBase.dbOpt;
import com.example.administrator.droideye.R;
import com.example.administrator.droideye.Utils.JniUtils;
import com.example.administrator.droideye.Utils.ViewAux.ViewAnimation;

import java.io.File;

/**
 * Created by wand on 2016/12/3.
 */

public class TrafficInsActivity extends AppCompatActivity implements TrafficInsListener {

    @Override
    public Context getAppContext(){

        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trafficins);
        //Initialize View Class
        Log.d("JNI says", JniUtils.hellojni());
        TrafficInsView traview = (TrafficInsView)findViewById(R.id.showtrafficview);
//        JniUtils jnit = new JniUtils();
//        NDK tests here.
        traview.init();
        //Initialize a controller
        TrafficInsController traController = new TrafficInsController(traview,this);
    }
}




class InitWorks extends Thread{

    dbOpt   dbopt   = null;
    Context context = null;

    public InitWorks(dbOpt dbopt){

        this.dbopt = dbopt;
    }



    @Override
    public void run(){




    }
}

