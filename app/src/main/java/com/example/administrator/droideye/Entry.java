package com.example.administrator.droideye;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.droideye.Models.Configuration;
import com.example.administrator.droideye.Settings.SettingsActivity;
import com.example.administrator.droideye.TrafficMonitor.TrafficInsActivity;

/**
 * Created by wand on 2016/12/30.
 */

public class Entry extends AppCompatActivity implements View.OnClickListener{

    ImageButton Monitor;
    ImageButton Traffic;
    ImageButton WakeUp;
    ImageButton Setting;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        Monitor = (ImageButton) findViewById(R.id.Monitor);
        Traffic = (ImageButton) findViewById(R.id.Traffic);
        WakeUp  = (ImageButton) findViewById(R.id.WakeUp);
        Setting = (ImageButton) findViewById(R.id.Setting);

        Monitor.setOnClickListener(this);
        Traffic.setOnClickListener(this);
        WakeUp.setOnClickListener(this);
        Setting.setOnClickListener(this);


    }

    @Override
    public void onClick(View view){

        switch (view.getId()){

            case R.id.Monitor:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.Traffic:
                Intent intent2 = new Intent(this, TrafficInsActivity.class);
                startActivity(intent2);
                break;

            case R.id.WakeUp:
                Intent intent3 = new Intent(this, MainActivity.class);
                startActivity(intent3);
                break;

            case R.id.Setting:
                Intent intent4 = new Intent(this, SettingsActivity.class);
                startActivity(intent4);
                break;

            default:

                Log.d(Configuration.click_listener_error, "Critical Error");
        }
    }
}
