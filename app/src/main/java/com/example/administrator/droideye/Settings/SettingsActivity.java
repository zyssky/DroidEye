package com.example.administrator.droideye.Settings;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.example.administrator.droideye.ProcessMonitor.MyAdapter;
import com.example.administrator.droideye.ProcessMonitor.ProcessHandler;
import com.example.administrator.droideye.R;

/**
 * Created by wand on 2016/12/25.
 */

public class SettingsActivity extends AppCompatActivity implements SettingsListener{

    @Override
    public Context getAppContext(){

        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){

        setContentView(R.layout.activity_settings);
        super.onCreate(savedInstanceState);
        SettingsView settingsView = (SettingsView) findViewById(R.id.activity_set);
        settingsView.init();
        SettingsController settingsController = new SettingsController(settingsView, this);
        settingsController.addAdapter(generateMyAdapter());
        settingsController.addAdapter(generateAdapter(true));
        settingsController.initsValueOnView();

    }

    private MyAdapter generateMyAdapter(){
        MyAdapter adapter = new MyAdapter(this ,R.layout.setting_permission, ProcessHandler.getInstance().getInstalledAppWithKillingPermission()
                ,new String[]{"icon","name","switch1","processname"},new int[]{R.id.icon,R.id.name,R.id.switch1});
        return adapter;
    }

    private SimpleAdapter generateAdapter(boolean includeSystem){
        SimpleAdapter simpleAdapter;
        simpleAdapter = new SimpleAdapter(this,ProcessHandler.getInstance().getRunningApplications(includeSystem),R.layout.process_item
                ,new String[]{"icon","name","time","size"},new int[]{R.id.Icon,R.id.Name,R.id.Time,R.id.Size});

        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String arg2) {
                if (view instanceof ImageView && data instanceof Drawable){
                    ImageView iv = (ImageView)view;
                    iv.setImageDrawable((Drawable)data);
                    return true;
                }
                return false;
            }
        });
        return simpleAdapter;
    }
}
