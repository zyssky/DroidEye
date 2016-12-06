package com.example.administrator.droideye.TrafficMonitor;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.util.Log;

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
    private FileUtils fileutil;

    public AppTrafficMonitor(TrafficInsListener listener){

        this.listener = listener;
        this.fileutil = new FileUtils();
        this.packageManager = listener.getAppContext().getPackageManager();
    }

    public List<HashMap<String,Object>> showApps(){

        List<HashMap<String,Object>> res = new ArrayList<HashMap<String, Object>>();
        List<PackageInfo> usingNetPackages = requireNetPackages(packageManager);
        for (PackageInfo app : usingNetPackages){
            if(app.applicationInfo.loadLabel(packageManager).length()>25)
                continue;
//            Log.d("[*]UsingInternet", app.applicationInfo.loadLabel(packageManager)+"");
            HashMap<String,Object> item = new HashMap<String,Object>();
            item.put("AppIcon",app.applicationInfo.loadIcon(packageManager));
            item.put("AppName",app.applicationInfo.loadLabel(packageManager));
            item.put("traffic","0KB");
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

    public String getAppNameFromInfo(PackageInfo info){

        return info.applicationInfo.loadLabel(packageManager)+"";
    }

    public String formatprintTraffic(long traffic){

        return fileutil.formatFileSize(listener.getAppContext(),traffic);
    }

    public void staticTraffic(List<Map> statics, List<PackageInfo> usingNetPackages){

        //List[Map(AppName,Traffic)]
        Map<String, Long> temp = new HashMap<String, Long>();
        for(int i = 0; i < usingNetPackages.size(); i++){

            PackageInfo appInfo = usingNetPackages.get(i);
            int uid = getUidFromInfo(appInfo);
            String name = getAppNameFromInfo(appInfo);

        }
    }

//    public void test(){
//        FileUtils util = new FileUtils();
//        Log.d("[TestFileFormat]", util.formatFileSize(listener.getAppContext(),1500));
//    }
}
