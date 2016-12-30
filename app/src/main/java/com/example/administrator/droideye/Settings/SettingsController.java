package com.example.administrator.droideye.Settings;

import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.example.administrator.droideye.Models.Configuration;
import com.example.administrator.droideye.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wand on 2016/12/25.
 */

public class SettingsController implements AdapterView.OnItemClickListener{

    SettingsView settingsView;
    SettingsListener settingsListener;
    List<Adapter> adapters = new ArrayList<Adapter>();

    public SettingsController(SettingsView settingsView, SettingsListener settingsListener){

        this.settingsView = settingsView;
        this.settingsListener = settingsListener;
    }

    public void initsValueOnView(){

        this.settingsView.setTrafficListListener(this);
        //adding adapters here.
        SettingsAdapter settingsAdapter = new SettingsAdapter(settingsListener.getAppContext());

        List<Map<String, ?>> security = new LinkedList<Map<String, ?>>();
        security.add(createItem("process_item",
                "查看当前运行进程，管理进程项"));
        security.add(createItem("setting_permission",
                "管理进程权限，是否可以杀死进程"));
        security.add(createItem("process_whitelist",
                "进程监控白名单设置"));
        security.add(createItem("daemon_process",
                "管理后台进程，进程唤醒"));

        settingsAdapter.addSection("进程监控", new SimpleAdapter(settingsListener.getAppContext(), security,
                R.layout.list_complex,
                new String[] { "title", "caption" }, new int[] {
                R.id.list_complex_title, R.id.list_complex_caption }));

        security.clear();
        security.add(createItem("traffic_overlook",
                "查看流量统计数据"));
        security.add(createItem("traffic_setting",
                "设置流量监控限制参数"));
        security.add(createItem("traffic_whitelist",
                "流量监控白名单设置"));

        settingsAdapter.addSection("流量管理", new SimpleAdapter(settingsListener.getAppContext(), security,
                R.layout.list_complex,
                new String[] { "title", "caption" }, new int[] {
                R.id.list_complex_title, R.id.list_complex_caption }));

        settingsAdapter.addSection("process_items", this.adapters.get(0));
        settingsAdapter.addSection("SystemApps", this.adapters.get(1));


        settingsView.setSettingsListViewAdapter(settingsAdapter);

//        settingsAdapter.addSection("switch_items", );
    }

    public Map<String, ?> createItem(String title, String caption) {
        Map<String, String> item = new HashMap<String, String>();
        item.put("title", title);
        item.put("caption", caption);
        return item;
    }

    public void addAdapter(Adapter adapter){

        this.adapters.add(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position , long id){

        switch(parent.getId()){

            case R.id.settinglist:


                break;

            default:

                Log.d(Configuration.click_listener_error, "Error At SettingList.");
        }
    }
}
