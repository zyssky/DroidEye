package com.example.administrator.droideye.Settings;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.example.administrator.droideye.R;

/**
 * Created by wand on 2016/12/25.
 */

public class SettingsView extends RelativeLayout {

    public ListView settingList;
    public SettingsView(Context context, AttributeSet attributeSet){

        super(context, attributeSet);
    }

    public void init(){

        settingList = (ListView) findViewById(R.id.settinglist);

    }

    public void setSettingsListViewAdapter(SettingsAdapter settingsadapter){

        settingList.setAdapter(settingsadapter);
    }

    public void setTrafficListListener(AdapterView.OnItemClickListener listener){

        settingList.setOnItemClickListener(listener);
    }
}
