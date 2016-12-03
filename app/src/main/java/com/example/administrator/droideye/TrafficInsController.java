package com.example.administrator.droideye;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by wand on 2016/12/3.
 */

public class TrafficInsController implements View.OnClickListener{

    private TrafficInsView view;
    private TrafficInsListener listener;
    public TrafficInsController(TrafficInsView view , TrafficInsListener listener){

        this.view = view;
        this.listener = listener;
        initsValueOnView();
    }

    public void initsValueOnView(){

        this.view.setShowtrafficBtnListener(this);
    }

    @Override
    public void onClick(View v){

        //Test Our Class here.
        //Toast.makeText(listener.getAppContext(),"start your test ! ",Toast.LENGTH_SHORT).show();
        getAppTraffics trafficMonit = new getAppTraffics(listener);
        trafficMonit.trafficmonitor();
    }
}
