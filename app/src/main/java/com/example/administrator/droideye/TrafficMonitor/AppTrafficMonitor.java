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
    }

    public List<HashMap<String,Object>> showApps(){

        //Haven't load into database yet.
        int i = 0;
        List<HashMap<String,Object>> res = new ArrayList<HashMap<String, Object>>();
        List<PackageInfo> usingNetPackages = requireNetPackages(packageManager);
        for (PackageInfo appinfo : usingNetPackages){
            if(appinfo.applicationInfo.loadLabel(packageManager).length()>25)
                continue;

            HashMap<String,Object> item = new HashMap<String,Object>();
            item.put("AppIcon",appinfo.applicationInfo.loadIcon(packageManager));
            item.put("AppName",appinfo.applicationInfo.loadLabel(packageManager));
            item.put("traffic",staticTraffic(appinfo));
            item.put("packageName", appinfo.applicationInfo.packageName);
            i+=1;
            res.add(item);
        }
        return res;
    }


    //Acquire App Names requiring
    public static List<PackageInfo> requireNetPackages(PackageManager packageManager){

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

    public static long getTrafficIn(int uId){

        return TrafficStats.getUidRxBytes(uId);
    }

    public static long getTrafficOut(int uId){

        return TrafficStats.getUidTxBytes(uId);
    }

    public static int getUidFromInfo(PackageInfo info){

        return info.applicationInfo.uid;
    }

    public String formatprintTraffic(long traffic){

        return FileUtils.formatFileSize(listener.getAppContext(),traffic);
    }

    public static String printTraffic(long traffic,Context context){

        return FileUtils.formatFileSize(context,traffic);
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

