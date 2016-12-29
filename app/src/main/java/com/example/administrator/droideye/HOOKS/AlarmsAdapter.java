package com.example.administrator.droideye.HOOKS;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;


import com.example.administrator.droideye.ProcessHandler;
import com.example.administrator.droideye.R;
import com.example.administrator.droideye.Setting;

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

    public static int currentTYPE = 0;

    public static String[] Status = {"Unsafe to limit !","Unknow ","Safe to limit "};

    public AlarmsAdapter(Context context, ArrayList<BaseStats> alarmStatList,int resource,int[] ids,int TYPE) {
        this.context = context;
        this.alarmStatList = alarmStatList;
        this.resource = resource;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.ids = ids;
        currentTYPE = TYPE;
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

        imageView.setImageDrawable(ProcessHandler.getInstance().getAppIcon(alarm.getName()));
        name.setText(alarm.getDerivedPackageName(context));
        allowcount.setText(String.valueOf(alarm.getAllowedCount()));
        blockcount.setText(String.valueOf(alarm.getBlockCount()));

        if(alarm instanceof WakelockStats){
            TextView wake = (TextView) view.findViewById(ids[4]);
            TextView block = (TextView) view.findViewById(ids[5]);
            String time = (((WakelockStats)alarm).getDurationAllowedFormatted());
            if(!time.equals("00:00")) {
                wake.setVisibility(TextView.VISIBLE);
                block.setVisibility(TextView.VISIBLE);
//            wake.setText((((WakelockStats)alarm).getDurationAllowedFormatted()));
                block.setText(time);
            }
        }

        final String title = alarm.getDerivedPackageName();
        final String keyname = alarm.getName();
//        final boolean status2 = alarm.getBlockingEnabled();
        final boolean status = Setting.getInstance().getAlarmStatus(keyname);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogview = inflater.inflate(R.layout.setting_dialog,null);
                final TextView safeforlimit = (TextView) dialogview.findViewById(R.id.safeforlimit);
                switch (EventLookup.isSafe(keyname)){
                    case EventLookup.UNSAFE:
                        safeforlimit.setText(Status[0]);
                        break;
                    case EventLookup.UNKNOWN:
                        safeforlimit.setText(Status[1]);
                        break;
                    case EventLookup.SAFE:
                        safeforlimit.setText(Status[2]);
                        break;
                }

                final Switch swtich1 = (Switch) dialogview.findViewById(R.id.blockswitch);
                swtich1.setChecked(status);
                new AlertDialog.Builder(context).setTitle(title).setView(dialogview)
                        .setNegativeButton("取消",null).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText text = (EditText) dialogview.findViewById(R.id.frequence);

                        switch (currentTYPE){
                            case 0:
                                Setting.getInstance().changeAlarmStatus(keyname, swtich1.isChecked());
                                if (text.getText().toString().matches("\\d+"))
                                    Setting.getInstance().changeAlarmAllowTime(keyname, Long.parseLong(text.getText().toString()));
                                break;
                            case 1:
                                Setting.getInstance().changeServiceStatus(keyname, swtich1.isChecked());
                                if (text.getText().toString().matches("\\d+"))
                                    Setting.getInstance().changeServiceAllowTime(keyname, Long.parseLong(text.getText().toString()));
                                break;
                            case 2:
                                Setting.getInstance().changeWakelockStatus(keyname, swtich1.isChecked());
                                if (text.getText().toString().matches("\\d+"))
                                    Setting.getInstance().changeWakelockAllowTime(keyname, Long.parseLong(text.getText().toString()));
                        }

                    }
                }).show();
            }
        });

        return view;
    }

}
