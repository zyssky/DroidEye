package com.example.administrator.droideye.ProcessMonitor;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.droideye.Settings.Setting;
import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
    private Context context;
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

    public static void init(Context context){
        processHandler = new ProcessHandler(context);
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
        if(minute>=0){
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

    public static String intToSizeStr(int size){
        if(size<1024)
            return ""+size+"KB";
        double mb = size/1024.0;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(mb)+"MB";
    }

    public static int MsToSecond(long ms){
        return new BigDecimal(ms/1000).intValueExact();
    }

    public ProcessHandler(Context context){
        this.context = context;
        this.activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        this.packageManager = context.getPackageManager();
        if(Build.VERSION.SDK_INT>=21)
            this.usageStatsManager=(UsageStatsManager) context.getSystemService(USAGE_STATS_SERVICE);
    }

    public List<AndroidAppProcess> getRunningApps(){

        List<AndroidAppProcess> processInfoList = ProcessManager.getRunningAppProcesses();
        Toast.makeText(context,"start your test ! with process: "+processInfoList.size(),Toast.LENGTH_SHORT).show();

        for (AndroidAppProcess appProcess : processInfoList) {
            int pid = appProcess.uid; // pid
            String packageName = appProcess.getPackageName(); // 进程名
            Log.d(TAG, "processName: " + packageName + "  uid : " + pid);
        }
        return processInfoList;
    }

    public Drawable getAppIcon(String packagename){
        packagename = packagename.split("[:/]")[0];
        Drawable drawable;
        try {
            drawable = packageManager.getApplicationIcon(getSpecifyAppInfo(packagename));
        }catch (Exception e){
            drawable = context.getResources().getDrawable(android.R.drawable.ic_lock_lock);
            Log.d(TAG, "getAppIcon: can not find package icon of "+packagename);
        }
        return drawable;
    }

    public List<Map<String,Object>> getInstalledAppWithKillingPermission(){
        List<ApplicationInfo> apps = getInstalledApps();
        List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
        for (ApplicationInfo app :
                apps) {
            if (Setting.getInstance().isInWhiteList(app.processName))
                result.add(generateMapItem(app.loadIcon(packageManager),(String)app.loadLabel(packageManager),false,app.processName));
            else
                result.add(generateMapItem(app.loadIcon(packageManager),(String)app.loadLabel(packageManager),true,app.processName));
        }
        return result;
    }

    private Map<String,Object> generateMapItem(Drawable drawable,String name,boolean switch1,String processname){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("icon",drawable);
        map.put("name",name);
        map.put("switch1",switch1);
        map.put("processname",processname);
        return map;
    }

    public List<ApplicationInfo> getInstalledApps(){
        return packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
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

    public void test(){
        forceStopProcess("com.example.administrator.droideye");
    }

    public List<ActivityManager.RunningServiceInfo> getRunningServices(){
        List<ActivityManager.RunningServiceInfo> serviceInfoList = activityManager.getRunningServices(1000);
        return serviceInfoList;
    }

    public long getServiceUsedTime(ActivityManager.RunningServiceInfo service){
        return SystemClock.elapsedRealtime()-service.activeSince;
    }

    public List<HashMap<String,Object>> getRunningApplications(boolean includeSystem){
        List<HashMap<String,Object>> result = new ArrayList<HashMap<String,Object>>();
        List<ActivityManager.RunningServiceInfo> list = getRunningServices();
        HashMap<String,SimpleProcess> list1 = getRunningSimpleProcess();
        HashMap<String,Object> counted = new HashMap<String, Object>();
        if(includeSystem){
            for (ActivityManager.RunningServiceInfo service  :
                    list) {
                ApplicationInfo app = getSpecifyAppInfo(service.process.split(":")[0]);
                if(null!=app&&!counted.containsKey(app.processName)){
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("icon",app.loadIcon(packageManager));
                    map.put("name",app.loadLabel(packageManager));
                    map.put("packagename",app.processName);
                    if(list1.containsKey(app.processName))
                        map.put("size",list1.get(app.processName).size);
//                    list1.remove(service.process);
                    String time = ProcessHandler.longToTimeStr(getServiceUsedTime(service));
                    map.put("time",time);
                    counted.put(app.processName,0);
                    result.add(map);
                }
            }
            for (Map.Entry<String,SimpleProcess> process :
                    list1.entrySet()) {
                if (!counted.containsKey(process.getValue().processname)){
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    try {
                        map.put("icon",packageManager.getApplicationIcon(process.getValue().processname));
                        map.put("name",packageManager.getApplicationLabel(getSpecifyAppInfo(process.getValue().processname)));
//                        String time = ProcessHandler.longToTimeStr(getAppInForegroundTime(process.getValue().processname));
                        map.put("time","");
                        map.put("size",process.getValue().size);
                        map.put("packagename",process.getValue().processname);
                        counted.put(process.getValue().processname,0);
//                        list1.remove(process.getValue().processname);
                        result.add(map);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else{
            for (ActivityManager.RunningServiceInfo service :
                    getRunningServices()) {

                ApplicationInfo app = getSpecifyAppInfo(service.process.split(":")[0]);
                if(null!=app&&!isInSystem(app)&&!counted.containsKey(app.processName)){
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("icon",app.loadIcon(packageManager));
                    map.put("name",app.loadLabel(packageManager));
                    map.put("packagename",app.processName);
                    String time = ProcessHandler.longToTimeStr(getServiceUsedTime(service));
                    map.put("time",time);
                    if(list1.containsKey(app.processName))
                        map.put("size",list1.get(app.processName).size);
                    counted.put(app.processName,0);
                    result.add(map);
                }
            }
            for (Map.Entry<String,SimpleProcess> process :
                    list1.entrySet()) {
                if (!process.getValue().isInSystem&&!counted.containsKey(process.getValue().processname)){
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    try {
                        map.put("icon",packageManager.getApplicationIcon(process.getValue().processname));
                        map.put("name",packageManager.getApplicationLabel(getSpecifyAppInfo(process.getValue().processname)));
//                        String time = ProcessHandler.longToTimeStr(getAppInForegroundTime(process.getValue().processname));
                        map.put("time","");
                        map.put("size",process.getValue().size);
                        map.put("packagename",process.getValue().processname);
                        counted.put(process.getValue().processname,0);
                        result.add(map);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return result;
    }

    public HashMap<String,UsedRecord> getAppUsedRecords(){
        HashMap<String,UsedRecord> result = new HashMap<String,UsedRecord>();
        List<ActivityManager.RunningServiceInfo> serviceInfoList = getRunningServices();
        HashMap<String,SimpleProcess> processHashMap = getRunningSimpleProcess();
        for (ActivityManager.RunningServiceInfo service :
                serviceInfoList) {
            String process = service.process.split(":")[0];
            if (!Setting.getInstance().isInWhiteList(process)) {
                ApplicationInfo app = getSpecifyAppInfo(process);
                if (null != app)
                    result.put(process, new UsedRecord((String) packageManager.getApplicationLabel(app)
                            , getServiceUsedTime(service)));
            }
            if(processHashMap.containsKey(process))
                processHashMap.remove(process);
        }
        for (Map.Entry<String,SimpleProcess> entry :
                processHashMap.entrySet()) {
            if (!Setting.getInstance().isInWhiteList(entry.getKey())){
                ApplicationInfo app = getSpecifyAppInfo(entry.getValue().processname);
                if(null!=app)
                    result.put(entry.getKey(),new UsedRecord((String)packageManager.getApplicationLabel(app)
                            ,getAppBackgroundTime(entry.getKey())));
            }

        }
        return result;
    }

    public HashMap<String,SimpleProcess> getRunningSimpleProcess(){
        String cmd = "ps";
        String result = "";
        HashMap<String,SimpleProcess> list = new HashMap<String,SimpleProcess>();
        try {

            Process p =  Runtime.getRuntime().exec(cmd);
            BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
            while((result=br.readLine())!=null)
            {
                String[] temp = result.split(" +");
                if(temp[temp.length-1].contains(".")) {
                    SimpleProcess pp = new SimpleProcess(temp);
                    if (pp.user.startsWith("u0") || pp.user.equals("system"))
                        list.put(pp.processname, pp);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }


    public ApplicationInfo getSpecifyAppInfo(String packageName) {
        try {
            return packageManager.getApplicationInfo(packageName,PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, "getSpecifyAppInfo: packageName not found "+packageName);
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
        ApplicationInfo app = getSpecifyAppInfo(service.process.split(":")[0]);
        if(null==app)
            return true;
        return isInSystem(app);
    }


    public Long getAppBackgroundTime(String packageName){
        if(Build.VERSION.SDK_INT>=21) {
            getUsageStatsPermission();
            long usedtime = -1;
            long time = System.currentTimeMillis() - SystemClock.elapsedRealtime();
            List<UsageStats> list = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, time, System.currentTimeMillis());
            for (UsageStats u :
                    list) {
                if (packageName.equals(u.getPackageName()))
                    usedtime = System.currentTimeMillis() - u.getLastTimeUsed();
            }
            return usedtime;
        }
        else{
            long time = 0;
            return time;
        }
    }


    public void getUsageStatsPermission(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            if(hasUsageStatsOption())
                if(!hasOpenUsageStats())
                    context.startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private boolean hasUsageStatsOption() {
        PackageManager packageManager = context.getApplicationContext()
                .getPackageManager();
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private boolean hasOpenUsageStats(){
        long ts = System.currentTimeMillis();
        UsageStatsManager usageStatsManager = (UsageStatsManager) context
                .getApplicationContext().getSystemService(USAGE_STATS_SERVICE);
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_BEST, 0, ts);
        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            return false;
        }
        return true;
    }

    public class UsedRecord implements Comparable<UsedRecord>{
        public String lable;
        public long seconds;
        public UsedRecord(String lable,long seconds){
            this.lable  = lable;
            this.seconds = seconds;
        }

        @Override
        public int compareTo(UsedRecord another) {
            if(this.seconds>another.seconds)
                return 1;
            if(this.seconds == another.seconds)
                return 0;
            return -1;
        }
    }

    class SimpleProcess{
        public String user;
        public String processname;
        public int pid;
        public String size;
        boolean isInSystem;
        public SimpleProcess(String[] result){
            try{
                user = result[0];
                processname = result[result.length-1].split(":")[0];
                pid = Integer.parseInt(result[1]);
                isInSystem = isInSystem(getSpecifyAppInfo(processname));
                size = intToSizeStr(Integer.parseInt(result[4]));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}


