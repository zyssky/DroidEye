package com.example.administrator.droideye;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.USAGE_STATS_SERVICE;

/**
 * Created by Administrator on 2016/12/3.
 */

public class ProcessHandler {
    private List<ActivityManager.RunningAppProcessInfo> listAppInfo;
    private PackageManager packageManager;
    private InteractionListener listener;
    private  ActivityManager activityManager;
    private UsageStatsManager usageStatsManager;

    public static String TAG = ProcessHandler.class.getSimpleName();

    private static ProcessHandler processHandler;
    private static boolean isInited = false;

    public static ProcessHandler getInstance(){
        if(isInited)
            return processHandler;
        else{
            Log.d(TAG, "getInstance: without initing the ProcessHandler");
            return null;
        }
    }

    public static void init(InteractionListener listener){
        processHandler = new ProcessHandler(listener);
        isInited = true;
    }

    public static String longToTimeStr(long milliseconds){
        long seconds = milliseconds/1000;
        long hour = seconds/3600;
        long minute = (seconds/60)%60;
        long second = seconds%60;
        StringBuffer sb = new StringBuffer();
        if(hour>0) {
            sb.append(hour);
            sb.append(":");
        }
        if(minute>0){
            if(minute>9)
                sb.append(minute);
            else {
                sb.append("0");
                sb.append(minute);
            }
            sb.append(":");
        }
        if(second>9)
            sb.append(second);
        else {
            sb.append("0");
            sb.append(second);
        }
        return sb.toString();
    }

    public ProcessHandler(InteractionListener listener){
        this.listener = listener;
        this.activityManager = (ActivityManager) listener.getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        this.packageManager = listener.getActivity().getPackageManager();
        this.usageStatsManager=(UsageStatsManager) listener.getActivity().getSystemService(USAGE_STATS_SERVICE);

    }

    public List<AndroidAppProcess> getRunningApps(){

        List<AndroidAppProcess> processInfoList = ProcessManager.getRunningAppProcesses();
        Toast.makeText(listener.getActivity(),"start your test ! with process: "+processInfoList.size(),Toast.LENGTH_SHORT).show();

        for (AndroidAppProcess appProcess : processInfoList) {
            int pid = appProcess.uid; // pid
            String packageName = appProcess.getPackageName(); // 进程名
            Log.d(TAG, "processName: " + packageName + "  uid : " + pid);
        }
        return processInfoList;
    }

    public void killProcess(String packagename){
        Log.d(TAG, "killProcess: "+packagename);
        ActivityManager activityManager = (ActivityManager) listener.getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.killBackgroundProcesses(packagename);
    }

    public void forceStopProcess(String packagename){
        try {
            Method forceStopPackage = activityManager.getClass().getDeclaredMethod("forceStopPackage", String.class);
            forceStopPackage.setAccessible(true);

            forceStopPackage.invoke(activityManager, packagename);
        }
        catch (InvocationTargetException e) {
            e.getCause().printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<ActivityManager.RunningServiceInfo> getRunningServices(){
        List<ActivityManager.RunningServiceInfo> serviceInfoList = activityManager.getRunningServices(1000);

        for (ActivityManager.RunningServiceInfo appProcess : serviceInfoList) {
            int pid = appProcess.pid; // pid
            String processName = appProcess.process; // 服务名
            Log.d(TAG, "ServiceName: " + processName + "  used time: "+getServiceUsedTime(appProcess));
        }

//        Toast.makeText(listener.getActivity(),"start your test ! with service:"+serviceInfoList.size(),Toast.LENGTH_SHORT).show();
        return serviceInfoList;
    }

    public long getServiceUsedTime(ActivityManager.RunningServiceInfo service){
        return SystemClock.elapsedRealtime()-service.activeSince;
    }

    public List<HashMap<String,Object>> testgetList(){
        List<PackageInfo> list = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        List<HashMap<String,Object>> result = new ArrayList<HashMap<String,Object>>();
        for (PackageInfo app :
                list) {
            HashMap<String,Object> item = new HashMap<String,Object>();
            item.put("icon",app.applicationInfo.loadIcon(packageManager));
            item.put("name",app.applicationInfo.loadLabel(packageManager));
            item.put("time","0KB");
            result.add(item);
        }
        return result;
    }

    public List<HashMap<String,Object>> getRunningApplications(boolean includeSystem){
        List<HashMap<String,Object>> result = new ArrayList<HashMap<String,Object>>();
        if(includeSystem){
            for (ActivityManager.RunningServiceInfo service :
                    getRunningServices()) {

                ApplicationInfo app = getSpecifyAppInfo(service.process);
                if(null!=app){
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("icon",app.loadIcon(packageManager));
                    map.put("name",app.loadLabel(packageManager));
                    String time = ProcessHandler.longToTimeStr(getServiceUsedTime(service));
                    map.put("time",time);
                    result.add(map);
                }
            }
        }
        else{
            for (ActivityManager.RunningServiceInfo service :
                    getRunningServices()) {

                ApplicationInfo app = getSpecifyAppInfo(service.process);
                if(!isInSystem(service)){

                    HashMap<String, Object> map = new HashMap<String, Object>();
                    try {
                        map.put("icon",packageManager.getApplicationIcon(service.process));
                        map.put("name",app.loadLabel(packageManager));
                        String time = ProcessHandler.longToTimeStr(getServiceUsedTime(service));
                        map.put("time",time);
                        result.add(map);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        return result;
    }


    public void exec(String cmd){
        String result = "";
        String con = "";
        try {
            Process p =  Runtime.getRuntime().exec(cmd);
            BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
            while((result=br.readLine())!=null)
            {
                Log.d(TAG, "exec: "+result);
                con+=result;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
//        Log.d(TAG, "exec: "+con);
    }

    public List<ApplicationInfo> getInstalledAppInfoList(){

        List<ApplicationInfo> list = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
//        Toast.makeText(listener.getActivity(),"start your test ! with installed app :"+list.size(),Toast.LENGTH_SHORT).show();
//        for (ApplicationInfo app:
//             list) {
//            if(!isInSystem(app))
//                Log.d(TAG, "getInstalledAppInfoList: "+app.processName);
//        }
        return list;
    }

    public ApplicationInfo getSpecifyAppInfo(String packageName) {
        try {
            return packageManager.getApplicationInfo(packageName,PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isInSystem(ApplicationInfo app){
        int temp = app.flags & ApplicationInfo.FLAG_SYSTEM;
        if (0 == temp)
            return false;
        else
            return true;
    }

    public boolean isInSystem(ActivityManager.RunningServiceInfo service){
        ApplicationInfo app = getSpecifyAppInfo(service.process);
        if(null==app)
            return true;
        return isInSystem(app);
    }

    public Long getAppUsedTime(ApplicationInfo app){
        ComponentName appName = new ComponentName(app.packageName,app.getClass().getName());
        Long usedTime;
        try {
            //获得ServiceManager类
            Class<?> ServiceManager = Class.forName("android.os.ServiceManager");
            //获得ServiceManager的getService方法
            Method getService = ServiceManager.getMethod("getService", java.lang.String.class);
            //调用getService获取RemoteService
            Object oRemoteService = getService.invoke(null, USAGE_STATS_SERVICE);
            //获得IUsageStats.Stub类
            Class<?> cStub = Class.forName("com.android.internal.app.IUsageStats$Stub");
            //获得asInterface方法
            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
            //调用asInterface方法获取IUsageStats对象
            Object oIUsageStats = asInterface.invoke(null, oRemoteService);
            //获得getPkgUsageStats(ComponentName)方法
            Method getPkgUsageStats = oIUsageStats.getClass().getMethod("getPkgUsageStats", ComponentName.class);
            //调用getPkgUsageStats 获取PkgUsageStats对象
            Object aStats = getPkgUsageStats.invoke(oIUsageStats, appName);

            //获得PkgUsageStats类
            Class<?> PkgUsageStats = Class.forName("com.android.internal.os.PkgUsageStats");

            usedTime = PkgUsageStats.getDeclaredField("usageTime").getLong(aStats);
            return usedTime;
//            PkgUsageStats.getDeclaredField("launchCount").getInt(aStats);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long getAppInForegroundTime(String packageName){
        getUsageStatsPermission();
        long usedtime = -1;
        long time =System.currentTimeMillis()-SystemClock.elapsedRealtime();
        List<UsageStats> list=usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, time, System.currentTimeMillis());
        for (UsageStats u :
                list) {
            if (packageName.equals(u.getPackageName()))
                usedtime = u.getTotalTimeInForeground();
        }
        return usedtime;
    }

    public Long getAppBackgroundTime(String packageName){
        getUsageStatsPermission();
        long usedtime = -1;
        long time =System.currentTimeMillis()-SystemClock.elapsedRealtime();
        List<UsageStats> list=usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, time, System.currentTimeMillis());
        for (UsageStats u :
                list) {
            if (packageName.equals(u.getPackageName()))
                usedtime = System.currentTimeMillis()-u.getLastTimeUsed();
        }
        return usedtime;
    }

    public void test(){
        List<AndroidAppProcess> list = getRunningApps();
        List<ActivityManager.RunningServiceInfo> listService = getRunningServices();
//        for (AndroidAppProcess app : list) {
//            Toast.makeText(listener.getActivity(),app.getPackageName(),Toast.LENGTH_SHORT).show();
//            boolean temp = isInSystem(getSpecifyAppInfo(app.getPackageName()));
//            if (!temp) {
////                killProcess(app.getPackageName());
//
//                if(app.getPackageName().contains("schedule"))
//                    forceStopProcess(app.getPackageName());
//            }
//        }
        for (ActivityManager.RunningServiceInfo service :
                listService) {
//            Toast.makeText(listener.getActivity(),service.process,Toast.LENGTH_SHORT).show();
            if (!isInSystem(service)) {
                Log.d(TAG, "service: " + service.process);
//                Toast.makeText(listener.getActivity(),service.process,Toast.LENGTH_SHORT).show();
                if (service.process.contains("mobile")) {
//                    forceStopProcess(service.process);
                    killProcess(service.process);
                }
            }
        }
    }

    public void getUsageStatsPermission(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            if(hasUsageStatsOption())
                if(!hasOpenUsageStats())
                    listener.getActivity().startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }
    }

    private boolean hasUsageStatsOption() {
        PackageManager packageManager = listener.getActivity().getApplicationContext()
                .getPackageManager();
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private boolean hasOpenUsageStats(){
        long ts = System.currentTimeMillis();
        UsageStatsManager usageStatsManager = (UsageStatsManager) listener.getActivity()
                .getApplicationContext().getSystemService(USAGE_STATS_SERVICE);
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_BEST, 0, ts);
        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            return false;
        }
        return true;
    }
}


