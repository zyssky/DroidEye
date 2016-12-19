package com.example.administrator.droideye;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

/**
 * Created by Administrator on 2016/12/3.
 */

public class MainView extends RelativeLayout {
    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Button testBtn;
    private ListView listView;

    public void init(){
        testBtn = (Button) findViewById(R.id.test);
        listView = (ListView) findViewById(R.id.runningapplist);
    }

    public void setTestBtnListener(OnClickListener listener){
        testBtn.setOnClickListener(listener);
    }

    public void setListViewAdapter(SimpleAdapter adapter){
        listView.setAdapter(adapter);
    }
}
