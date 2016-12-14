package com.example.administrator.droideye;

import android.app.ActivityManager;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.droideye.Models.Configuration;
import com.example.administrator.droideye.TrafficMonitor.TrafficInsActivity;

/**
 * Created by Administrator on 2016/12/3.
 */

public class MainController implements View.OnClickListener {
    private MainView view;
    private InteractionListener listener;

    public MainController(MainView view, InteractionListener listener){
        this.view = view;
        this.listener = listener;
        initValuesOnView();
    }

    void initValuesOnView(){
        view.setTestBtnListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.test:
                new ProcessHandler(listener).getInstalledAppInfoList();
                break;
            case R.id.showtraffic:
                Intent intent = new Intent(listener.getContext(), TrafficInsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                listener.getContext().startActivity(intent);
            default:
                Log.d(Configuration.click_listener_error,"Switch-Default Error.");
        }
//        Toast.makeText(listener.getContext(),"start your test ! ",Toast.LENGTH_SHORT).show();
//        ActivityManager activityManager = listener.getContext().getSystemService();

    }
}
