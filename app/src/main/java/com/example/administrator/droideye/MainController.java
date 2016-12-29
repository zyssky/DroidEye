package com.example.administrator.droideye;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.administrator.droideye.HOOKS.*;

import com.example.administrator.droideye.Models.Traffic;
import com.example.administrator.droideye.Service.MonitorService;
import com.example.administrator.droideye.Settings.SettingsActivity;
import com.example.administrator.droideye.Views.ListFragment;
import com.example.administrator.droideye.Models.Configuration;
import com.example.administrator.droideye.TrafficMonitor.TrafficInsActivity;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import static com.example.administrator.droideye.Setting.TAG;

/**
 * Created by Administrator on 2016/12/3.
 */

public class MainController implements View.OnClickListener,AdapterView.OnItemClickListener {
    private MainView view;
    private InteractionListener listener;
    private SimpleAdapter simpleAdapter;
    private boolean includeSystem;

//    private List<HashMap<String,Object>> runninglist;

    public MainController(MainView view, InteractionListener listener){
        this.view = view;
        this.listener = listener;
        ProcessHandler.init(listener.getActivity());
        Setting.init(listener.getActivity());
        includeSystem = false;

//        if(RootHelper.isDeviceRooted()) {
//            String apkRoot = "chmod 777 " + listener.getActivity().getPackageCodePath();
//            RootHelper.RootCommand(apkRoot);
//        }

        initValuesOnView();
    }

    void initValuesOnView(){
        view.setTestBtnListener(this);
        view.setListViewAdapter(generateMyAdapter());
//        view.setListViewAdapter(generateAdapter(includeSystem));
        view.setListViewListener(this);
    }

    public AlarmsAdapter generateVariousAdapter(int adapterType) {
        switch (adapterType) {
            case 0:
                AlarmsAdapter adapter =new  AlarmsAdapter(listener.getActivity(), UnbounceStatsCollection.getInstance().toAlarmArrayList(listener.getActivity()),
                        R.layout.alarms_listitem, new int[]{R.id.AlarmIcon, R.id.AlarmName, R.id.AlarmAllow, R.id.AlarmBlock, R.id.waketimelable, R.id.waketime},AlarmsAdapter.ALARMS);
                return adapter;
            case 1:
                return new AlarmsAdapter(listener.getActivity(), UnbounceStatsCollection.getInstance().toServiceArrayList(listener.getActivity()),
                        R.layout.alarms_listitem, new int[]{R.id.AlarmIcon, R.id.AlarmName, R.id.AlarmAllow, R.id.AlarmBlock, R.id.waketimelable, R.id.waketime},AlarmsAdapter.SERVICES);
            case 2:
                return new AlarmsAdapter(listener.getActivity(),UnbounceStatsCollection.getInstance().toWakelockArrayList(listener.getActivity()),
                        R.layout.alarms_listitem,new int[]{R.id.AlarmIcon,R.id.AlarmName,R.id.AlarmAllow,R.id.AlarmBlock,R.id.waketimelable,R.id.waketime},AlarmsAdapter.WAKELOCKS);
            default:
                return null;
        }
    }

    private MyAdapter generateMyAdapter(){
        MyAdapter adapter = new MyAdapter(listener.getActivity(),R.layout.setting_permission,ProcessHandler.getInstance().getInstalledAppWithKillingPermission()
                ,new String[]{"icon","name","switch1","processname"},new int[]{R.id.icon,R.id.name,R.id.switch1});
        return adapter;
    }

    private SimpleAdapter generateAdapter(boolean includeSystem){
        SimpleAdapter simpleAdapter;
        simpleAdapter = new SimpleAdapter(listener.getActivity(),ProcessHandler.getInstance().getRunningApplications(includeSystem),R.layout.process_item
                ,new String[]{"icon","name","time","size"},new int[]{R.id.Icon,R.id.Name,R.id.Time,R.id.Size});

        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String arg2) {
                if (view instanceof ImageView && data instanceof Drawable){
                    ImageView iv = (ImageView)view;
                    iv.setImageDrawable((Drawable)data);
                    return true;
                }
                return false;
            }
        });
        this.simpleAdapter = simpleAdapter;
        return simpleAdapter;
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = ((AppCompatActivity)listener.getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_main, fragment);
        fragmentTransaction.commit();
    }

    private static int testposition = 0;

    @Override
    public void onClick(View v) {
//        listener.close();
//        listener.getActivity().startService(new Intent(listener.getActivity(), MonitorService.class));
        switch (v.getId()){
            case R.id.test:
//                view.setListViewAdapter(generateAdapter(!includeSystem));
//                includeSystem = !includeSystem;
                view.setListViewAdapter(generateVariousAdapter(testposition));
                testposition+=1;
                testposition%=3;
                break;
            case R.id.showtraffic:
                Intent intent = new Intent(listener.getActivity(), TrafficInsActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                listener.getActivity().startActivity(intent);
                break;
            case R.id.settings:
                //Enter Settings
                Intent setting_intent = new Intent(listener.getActivity(), SettingsActivity.class);
//                MyAdapter myAdapter = generateMyAdapter();
//                setting_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                listener.getActivity().startActivity(setting_intent);
                break;
            default:
                Log.d(Configuration.click_listener_error,"Switch-Default Error.");
        }

    }

    public int stopApp(String t){
        ProcessHandler.getInstance().forceStopProcess(t);
        view.setListViewAdapter(generateAdapter(includeSystem));
        return 0;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final HashMap<String,Object> map = (HashMap<String, Object>) simpleAdapter.getItem(position);

        Log.d(TAG, "onItemClick: "+map.get("packagename"));
        this.view.showDialog("是否关闭该应用", (String) map.get("name"), new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return stopApp((String) map.get("packagename"));
            }
        });
    }

    public MainController getme(){

        return this;
    }
}
