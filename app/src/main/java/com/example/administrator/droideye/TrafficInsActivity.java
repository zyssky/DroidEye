package com.example.administrator.droideye;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
