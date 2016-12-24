package com.example.administrator.droideye;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/23.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    private List<Map<String,Object>> objects;
    private LayoutInflater inflater;
    private String[] keys;
    private int[] ids;

    public MyAdapter(Context context, int resource, List<Map<String,Object>> objects, String[] keys, int[] ids){
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.keys = keys;
        this.ids = ids;
    }



    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(null==convertView){
            view = inflater.inflate(resource,null);
        }
        ImageView imageView = (ImageView) view.findViewById(ids[0]);
        TextView textView = (TextView) view.findViewById(ids[1]);
        Switch switch1 = (Switch) view.findViewById(ids[2]);

        final Map<String,Object> map = objects.get(position);
        textView.setText((String)map.get(keys[1]));
        switch1.setChecked((boolean)map.get(keys[2]));
        imageView.setImageDrawable((Drawable)map.get(keys[0]));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch s = (Switch) v.findViewById(ids[2]);
                if((boolean)map.get(keys[2])){
                    Setting.getInstance().addToWhiteList((String)map.get(keys[3]));
                    map.put(keys[2],false);
                    objects.add(position,map);
                    s.setChecked(false);
                }
                else{
                    Setting.getInstance().deleteFromWhiteList((String)map.get(keys[3]));
                    map.put(keys[2],true);
                    objects.add(position,map);
                    s.setChecked(true);
                }
            }
        });
        return view;
    }
}
