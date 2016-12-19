package com.example.administrator.droideye.TrafficMonitor;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.util.Log;

import com.example.administrator.droideye.Models.Configuration;
import com.example.administrator.droideye.Models.DataBase.dbOpt;
import com.example.administrator.droideye.Utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by wand on 2016/12/3.
 */

public class AppTrafficMonitor {

    //Who implements the interface must be a activity context.
    private TrafficInsListener listener;
    private PackageManager packageManager;

    public AppTrafficMonitor(TrafficInsListener listener){

        this.listener = listener;
        this.packageManager = listener.getAppContext().getPackageManager();
        //Here Test for TrafficStatus Class:
        CheckTraffic cktraffic = new CheckTraffic(listener);
        cktraffic.start();
    }

    public List<HashMap<String,Object>> showApps(){

        //Haven't load into database yet.
        List<HashMap<String,Object>> res = new ArrayList<HashMap<String, Object>>();
        List<PackageInfo> usingNetPackages = requireNetPackages(packageManager);
        for (PackageInfo appinfo : usingNetPackages){
            if(appinfo.applicationInfo.loadLabel(packageManager).length()>25)
                continue;

            HashMap<String,Object> item = new HashMap<String,Object>();
            item.put("AppIcon",appinfo.applicationInfo.loadIcon(packageManager));
            item.put("AppName",appinfo.applicationInfo.loadLabel(packageManager));
            item.put("traffic",staticTraffic(appinfo));
            res.add(item);
        }
        return res;
    }


    //Acquire App Names requiring
    public List<PackageInfo> requireNetPackages(PackageManager packageManager){

        List<PackageInfo> res         = new ArrayList<PackageInfo>();
        List<PackageInfo> packinfos   = packageManager.getInstalledPackages(
                                        PackageManager.GET_UNINSTALLED_PACKAGES |
                                        PackageManager.GET_PERMISSIONS);

        //Expect Optimization--
        for(PackageInfo info : packinfos){

            String[] permissions = info.requestedPermissions;
            if(permissions != null) {
                boolean use_internet = false;
                for (String permission : permissions) {

                    if ("android.permission.INTERNET".equals(permission)) {
                        //Found Android Internet Permission for some app.
                        use_internet = true;
                        if(info!=null){
                        res.add(info);
                        }
//                        Log.d("[*]App using internet: ", info.toString());
                        break;
                    }
                }
                if(!use_internet){
                    Log.d("AppNotusinginternet: ", info.toString());
                }
            }
            else{
                Log.d("[*]Appwithoutpermission", info.toString());
            }
        }
        return res;
    }

    public long getTrafficIn(int uId){

        return TrafficStats.getUidRxBytes(uId);
    }

    public long getTrafficOut(int uId){

        return TrafficStats.getUidTxBytes(uId);
    }

    public int getUidFromInfo(PackageInfo info){

        return info.applicationInfo.uid;
    }

    public String formatprintTraffic(long traffic){

        return FileUtils.formatFileSize(listener.getAppContext(),traffic);
    }

    public String staticTraffic(PackageInfo appinfo){

        //New type of static analyzing states here.
        int uid = getUidFromInfo(appinfo);
        long trafficin = getTrafficIn(uid);
        long trafficout= getTrafficOut(uid);
        long trafficall= trafficin + trafficout;
        //Request for all
        return formatprintTraffic(trafficall);
    }

//    public void test(){
//        FileUtils util = new FileUtils();
//        Log.d("[TestFileFormat]", util.formatFileSize(listener.getAppContext(),1500));
//    }
}
//
class CheckTraffic extends Thread{

    TrafficInsListener listener;
    public CheckTraffic(TrafficInsListener listener){
        this.listener = listener;
    }

    @Override
    public void run(){

        while(true){
            try{
                Thread.sleep(2000);
                long temp = TrafficStats.getTotalRxBytes()+TrafficStats.getTotalTxBytes();
                Log.d("Traffic Status:" , FileUtils.formatFileSize(listener.getAppContext(),temp));
            }catch(Exception e){
                Log.d(Configuration.file_opt_error,e.toString());
            }
        }
    }
}
