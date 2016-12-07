package com.example.administrator.droideye.TrafficMonitor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.administrator.droideye.Models.DataBase.dbOpt;
import com.example.administrator.droideye.R;
import com.example.administrator.droideye.Utils.ViewAux.ViewAnimation;

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
        TrafficInsView traview = (TrafficInsView)findViewById(R.id.showtrafficview);
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