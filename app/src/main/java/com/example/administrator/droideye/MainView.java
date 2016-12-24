package com.example.administrator.droideye;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2016/12/3.
 */

public class MainView extends RelativeLayout {
    private Context context;
    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    private Button testBtn;
    private ListView listView;
    private Button showtraffic;

    public void init() {
        testBtn = (Button) findViewById(R.id.test);
        listView = (ListView) findViewById(R.id.runningapplist);
        showtraffic = (Button) findViewById(R.id.showtraffic);
//        testBtn.setVisibility(Button.INVISIBLE);
//        showtraffic.setVisibility(Button.INVISIBLE);
    }


    public void setTestBtnListener(OnClickListener listener){
        testBtn.setOnClickListener(listener);
        showtraffic.setOnClickListener(listener);
    }

    public void setListViewListener(AdapterView.OnItemClickListener listener){
        listView.setOnItemClickListener(listener);
    }

    public void showDialog(String title, String message, final Callable<Integer> func){
        new AlertDialog.Builder(context).setTitle(title).setMessage(message).setNegativeButton(R.string.cancle,null)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            func.call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
    }

    public void setListViewAdapter(BaseAdapter adapter){
        listView.setAdapter(adapter);
    }
}
