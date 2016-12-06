package com.example.administrator.droideye.TrafficMonitor;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.example.administrator.droideye.R;

import java.util.List;

/**
 * Created by wand on 2016/12/3.
 */

public class TrafficInsView extends RelativeLayout{

    public ImageButton menuButton;
    private ListView trafficList;

    public TrafficInsView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public void init(){

        menuButton  = (ImageButton)findViewById(R.id.rudder);
        trafficList = (ListView)findViewById(R.id.trafficList);
    }

    public void setImageBtnClickListener(OnClickListener listener){

        menuButton.setOnClickListener(listener);
    }

    public void setTrafficListViewAdapter(SimpleAdapter simpleadapter){

        trafficList.setAdapter(simpleadapter);
    }

    public void setTrafficListListener(AdapterView.OnItemClickListener listener){

        trafficList.setOnItemClickListener(listener);
    }
}
