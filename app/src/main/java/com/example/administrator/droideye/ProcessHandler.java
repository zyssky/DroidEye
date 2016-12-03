package com.example.administrator.droideye;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;
import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2016/12/3.
 */

public class ProcessHandler {
    private List<ActivityManager.RunningAppProcessInfo> listAppInfo;
    private PackageManager packageManager;
    private InteractionListener listener;
    private  ActivityManager activityManager;
    private ProcessManager processManager;

    public ProcessHandler(InteractionListener listener){
        this.listener = listener;
        this.activityManager = (ActivityManager) listener.getContext().getSystemService(Context.ACTIVITY_SERVICE);

    }

    public List<AndroidAppProcess> getRunningApps(){

        List<AndroidAppProcess> processInfoList = ProcessManager.getRunningAppProcesses();
        Toast.makeText(listener.getContext(),"start your test ! with process: "+processInfoList.size(),Toast.LENGTH_SHORT).show();

        for (AndroidAppProcess appProcess : processInfoList) {
            int pid = appProcess.pid; // pid
            String processName = appProcess.getPackageName(); // 进程名
            Log.d(TAG, "processName: " + processName + "  pid: " + pid);
        }
        return processInfoList;
    }

    public void killProcess(String packagename){
        ActivityManager activityManager = (ActivityManager) listener.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.killBackgroundProcesses(packagename);
    }

    public List<ActivityManager.RunningServiceInfo> getRunningServices(){
        List<ActivityManager.RunningServiceInfo> serviceInfoList = activityManager.getRunningServices(1000);

        for (ActivityManager.RunningServiceInfo appProcess : serviceInfoList) {
            int pid = appProcess.pid; // pid
            String processName = appProcess.process; // 服务名
            Log.d(TAG, "processName: " + processName + "  pid: " + pid);
        }

        Toast.makeText(listener.getContext(),"start your test ! with service:"+serviceInfoList.size(),Toast.LENGTH_SHORT).show();
        return serviceInfoList;
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
        PackageManager pm = listener.getContext().getPackageManager();
        List<ApplicationInfo> list = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Toast.makeText(listener.getContext(),"start your test ! with installed app :"+list.size(),Toast.LENGTH_SHORT).show();
        return list;

    }

}


