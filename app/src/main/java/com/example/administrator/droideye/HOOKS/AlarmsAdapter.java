package com.example.administrator.droideye.HOOKS;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.administrator.droideye.ProcessHandler;
import com.example.administrator.droideye.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by rsteckler on 9/7/14.
 */
public class AlarmsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BaseStats> alarmStatList;
    private int resource;
    private LayoutInflater inflater;
    private int[] ids;

    public static int ALARMS = 0;
    public static int SERVICES = 1;
    public static int WAKELOCKS = 2;

    public AlarmsAdapter(Context context, ArrayList<BaseStats> alarmStatList,int resource,int[] ids) {
        this.context = context;
        this.alarmStatList = alarmStatList;
        this.resource = resource;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.ids = ids;
    }

    @Override
    public int getCount() {
        return alarmStatList.size();
    }

    @Override
    public Object getItem(int position) {
        return alarmStatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(null==convertView){
            view = inflater.inflate(resource,null);
        }
        BaseStats alarm =(BaseStats) getItem(position);

        ImageView imageView = (ImageView) view.findViewById(ids[0]);
        TextView name = (TextView) view.findViewById(ids[1]);
        TextView allowcount = (TextView) view.findViewById(ids[2]);
        TextView blockcount = (TextView) view.findViewById(ids[3]);

        imageView.setImageDrawable(ProcessHandler.getInstance().getAppIcon(alarm.getDerivedPackageName(context)));
        name.setText(alarm.getName());
        allowcount.setText(String.valueOf(alarm.getAllowedCount()));
        blockcount.setText(String.valueOf(alarm.getAllowedCount()));

        if(alarm instanceof WakelockStats){
            TextView wake = (TextView) view.findViewById(ids[4]);
            TextView block = (TextView) view.findViewById(ids[5]);
            wake.setVisibility(TextView.VISIBLE);
            block.setVisibility(TextView.VISIBLE);
            wake.setText((((WakelockStats)alarm).getDurationAllowedFormatted()));
            block.setText((((WakelockStats)alarm).getDurationBlockedFormatted()));
        }

        return view;
    }

}
