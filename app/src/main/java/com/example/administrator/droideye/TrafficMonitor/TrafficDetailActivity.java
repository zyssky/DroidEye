package com.example.administrator.droideye.TrafficMonitor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.droideye.Models.DataBase.dbOpt;
import com.example.administrator.droideye.Models.Traffic;
import com.example.administrator.droideye.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wand on 2016/12/25.
 */

public class TrafficDetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.trafficdetail);
        Intent intent  = getIntent();
        String appName = intent.getStringExtra("AppName");
        String packageName = intent.getStringExtra("packageName");
        byte[] bis     = intent.getByteArrayExtra("AppIcon");
        Bitmap bitmap  = BitmapFactory.decodeByteArray(bis, 0 , bis.length);
        ImageButton appicon = (ImageButton)findViewById(R.id.appicon);
        TextView appname    = (TextView) findViewById(R.id.appname);
        TextView packagename= (TextView) findViewById(R.id.packagename);
        TextView One  = (TextView) findViewById(R.id.oneInfor);
        TextView Five  = (TextView) findViewById(R.id.fiveInfor);
        TextView Ten  = (TextView) findViewById(R.id.tenInfor);

        appname.setText(appName);
        packagename.setText(packageName);
        appicon.setImageBitmap(bitmap);

        //Fetch Content From Database
        dbOpt dbopt = new dbOpt(this);
        List<Traffic> traffic = new ArrayList<Traffic>();
        String[] app = new String[]{appName};
        traffic = dbopt.userdef_query("traffic", "SELECT * FROM traffic WHERE appName = ?", app );
        Traffic t = traffic.get(0);
        if(t.oneMinTrafficin == null || t.oneMinTrafficin.indexOf("null")>=0){
            t.oneMinTrafficin = "0";
        }
        if(t.oneMinTrafficout == null || t.oneMinTrafficout.indexOf("null")>=0){
            t.oneMinTrafficout = "0";
        }
        if(t.fiveMinTrafficin == null || t.fiveMinTrafficin.indexOf("null")>=0){
            t.fiveMinTrafficin = "0";
        }
        if(t.fiveMinTrafficout == null || t.fiveMinTrafficout.indexOf("null")>=0){
            t.fiveMinTrafficout = "0";
        }
        if(t.tenMinTrafficin == null || t.tenMinTrafficin.indexOf("null")>=0){
            t.tenMinTrafficin = "0";
        }
        if(t.tenMinTrafficout == null || t.tenMinTrafficout.indexOf("null")>=0){
            t.tenMinTrafficout = "0";
        }
        String oneMinWifi = AppTrafficMonitor.printTraffic(Integer.parseInt(t.oneMinTrafficin),this);
        String oneMinMobile = AppTrafficMonitor.printTraffic(Integer.parseInt(t.oneMinTrafficout),this);
        String fiveMinWifi = AppTrafficMonitor.printTraffic(Integer.parseInt(t.fiveMinTrafficin),this);
        String fiveMinMobile = AppTrafficMonitor.printTraffic(Integer.parseInt(t.fiveMinTrafficin),this);
        String tenMinWifi   = AppTrafficMonitor.printTraffic(Integer.parseInt(t.tenMinTrafficin),this);
        String tenMinMobile = AppTrafficMonitor.printTraffic(Integer.parseInt(t.tenMinTrafficin),this);

        One.setText("数据网络流量: " + oneMinMobile + " ; WiFi网络流量: " + oneMinWifi);
        Five.setText("数据网络流量: " + fiveMinMobile + " ; WiFi网络流量: " + fiveMinWifi);
        Ten.setText("数据网络流量: " + tenMinMobile + " ; WiFi网络流量: " + tenMinWifi);
    }
}
