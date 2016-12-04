package com.example.administrator.droideye.TrafficMonitor;

import android.view.View;

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
        AppTrafficMonitor trafficMonitor = new AppTrafficMonitor(listener);
        trafficMonitor.trafficmonitor();
    }
}
