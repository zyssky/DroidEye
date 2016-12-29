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
        appname.setText(appName);
        packagename.setText(packageName);
        appicon.setImageBitmap(bitmap);

        //Fetch Content From Database
        dbOpt dbopt = new dbOpt(this);
        List<Traffic> traffic = new ArrayList<Traffic>();
        String[] app = new String[]{appName};
        traffic = dbopt.userdef_query("traffic", "SELECT * FROM traffic WHERE appName = ?", app );
        Log.d("Show Me Your Content", traffic.get(0).appName);

    }
}
