package com.example.administrator.droideye.TrafficMonitor;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.administrator.droideye.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wand on 2016/12/3.
 */

public class TrafficInsController implements View.OnClickListener,AdapterView.OnItemClickListener{

    private TrafficInsView view;
    private TrafficInsListener listener;
    private List<HashMap<String,Object>> trafficItems = new ArrayList<HashMap<String, Object>>();
    private SimpleAdapter trafficAdapter;

    public TrafficInsController(TrafficInsView view , TrafficInsListener listener){

        AppTrafficMonitor trafficMonitor = new AppTrafficMonitor(listener);
        trafficItems = trafficMonitor.showApps();
        this.view = view;
        this.listener = listener;
        initAdapter();
        initsValueOnView();
    }

    public void initAdapter(){

        this.trafficAdapter = new SimpleAdapter(listener.getAppContext(),trafficItems, R.layout.trafficlst_item,
                new String[] {"AppIcon","AppName","traffic"},
                new int[] {R.id.AppIcon,R.id.AppName,R.id.traffic});

        trafficAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
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
    }

    public void initsValueOnView(){

        this.view.setTrafficListViewAdapter(trafficAdapter);
        this.view.setTrafficListListener(this);
    }

    @Override
    public void onClick(View v){

        //Test Our Class here.
        //Toast.makeText(listener.getAppContext(),"start your test ! ",Toast.LENGTH_SHORT).show();
        //AppTrafficMonitor trafficMonitor = new AppTrafficMonitor(listener);
        //trafficMonitor.trafficmonitor();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position , long id){

        String pos = position + "";
        Toast.makeText(listener.getAppContext(), "Entering Number " + pos, Toast.LENGTH_SHORT).show();
    }

    public void test(){

        for (int i = 0 ; i < 10; i++){
            HashMap<String , Object> temp = new HashMap<>();
            temp.put("AppIcon",R.drawable.eye);
            temp.put("AppName","AppTraffic");
            temp.put("traffic","5KB");
            trafficItems.add(temp);
        }
    }
}
