package com.example.administrator.droideye.TrafficMonitor;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.administrator.droideye.R;

/**
 * Created by wand on 2016/12/3.
 */

public class TrafficInsView extends RelativeLayout{

    private Button showtraffic;

    public TrafficInsView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public void init(){

        showtraffic = (Button)findViewById(R.id.showtraffic);
    }

    public void setShowtrafficBtnListener(OnClickListener listener){

        showtraffic.setOnClickListener(listener);
    }
}
