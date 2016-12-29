package com.example.administrator.droideye.TrafficMonitor;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;

//import com.example.administrator.droideye.JniUtils.jnitest;
import com.example.administrator.droideye.Models.Configuration;
import com.example.administrator.droideye.Models.DataBase.dbOpt;
import com.example.administrator.droideye.Models.Traffic;
import com.example.administrator.droideye.R;
import com.example.administrator.droideye.Utils.FileUtils;
import com.example.administrator.droideye.Utils.JniUtils;
import com.example.administrator.droideye.Utils.ViewAux.ViewAnimation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        dbOpt dbopt = new dbOpt(this);
        InitWorks initWorks = new InitWorks(this, dbopt);
        initWorks.start();
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
    List<PackageInfo> apps = new ArrayList<>();
    PackageManager packageManager = null;

    public InitWorks(Context context , dbOpt dbopt){

        this.dbopt = dbopt;
        this.context = context;
        this.packageManager = this.context.getPackageManager();

    }


    @Override
    public void run(){

        storeindb();
        while(true) {

            try{
                Thread.sleep(5000);
            }catch (Exception e){
                Log.d(Configuration.threadsleeperror, e.toString());
            }
            boolean isWatched = judgeNetworkType();
            if (!isWatched){
                break;
            }
        }
    }


    public void storeindb(){

            //Load into database now.
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            List<Traffic> traffics = new ArrayList<>();
            List<PackageInfo> usingNetPackages = AppTrafficMonitor.requireNetPackages(this.packageManager);
            for (PackageInfo appinfo : usingNetPackages){
                if(appinfo.applicationInfo.loadLabel(packageManager).length()>25)
                    continue;

                Traffic traffic = new Traffic((String)appinfo.applicationInfo.loadLabel(packageManager),
                        AppTrafficMonitor.getUidFromInfo(appinfo)+"",
                        sDateFormat.format(new java.util.Date()),
                        "0");
                traffics.add(traffic);
            }

            for ( Traffic traff : traffics){

                dbopt.add_traffic(traff);
            }
        }

    public boolean judgeNetworkType(){

        String network = getNetWorkType(this.context);
        File file = new File(Configuration.networktypepath);
        if ( !file.exists()){

            FileUtils.createFile("network");
            FileUtils.writetoFile(Configuration.networktypepath, "Watching|"+network);
            return true;
        }else{
            String content = FileUtils.readFromFile(Configuration.networktypepath);
            String A       = content.split("|")[0];
            String type    = content.split("|")[1];
            Log.d("WHICHMODEAMIIN", type);
            if (type.indexOf("WIFI") >=0){
                Configuration.inwifimode = true;
            }else{
                Configuration.inwifimode = false;
            }
            Log.d("AMIWATCHING?", A);
            return true;
        }
    }

    public String getNetWorkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                return "WIFI";
            } else if (type.equalsIgnoreCase("MOBILE")) {
                return "MOBILE";
//                mNetWorkType = TextUtils.isEmpty(proxyHost)
//                        ? (isFastMobileNetwork(context) ? NETWORKTYPE_3G : NETWORKTYPE_2G)
//                        : NETWORKTYPE_WAP;
            }
        } else {
            return "OFFLINE";
        }
        return "OFFLINE";
    }
}

