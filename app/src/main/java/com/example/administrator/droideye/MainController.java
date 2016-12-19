package com.example.administrator.droideye;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.administrator.droideye.Service.LongRunningService;
import com.example.administrator.droideye.Views.ListFragment;
import com.example.administrator.droideye.Models.Configuration;
import com.example.administrator.droideye.TrafficMonitor.TrafficInsActivity;

/**
 * Created by Administrator on 2016/12/3.
 */

public class MainController implements View.OnClickListener {
    private MainView view;
    private InteractionListener listener;
    private SimpleAdapter simpleAdapter;

    public MainController(MainView view, InteractionListener listener){
        this.view = view;
        this.listener = listener;
        ProcessHandler.init(listener);
        Setting.init(listener.getActivity());
        initValuesOnView();
    }

    void initValuesOnView(){
        view.setTestBtnListener(this);
        simpleAdapter = new SimpleAdapter(listener.getActivity(),ProcessHandler.getInstance().testgetList(),R.layout.trafficlst_item
                ,new String[]{"icon","name","time"},new int[]{R.id.AppIcon,R.id.AppName,R.id.traffic});
//        setFragment(ListFragment.newInstance(simpleAdapter,null,null));
        view.setListViewAdapter(simpleAdapter);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = ((AppCompatActivity)listener.getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_main, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {

//        Toast.makeText(listener.getActivity(),"start your test ! ",Toast.LENGTH_SHORT).show();
//        ActivityManager activityManager = listener.getActivity().getSystemService();
//        ProcessHandler.getInstance().test();
        listener.getActivity().startService(new Intent(listener.getActivity(), LongRunningService.class));
        switch (v.getId()){
            case R.id.test:
                new ProcessHandler(listener).getInstalledAppInfoList();
                break;
            case R.id.showtraffic:
                Intent intent = new Intent(listener.getActivity(), TrafficInsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                listener.getActivity().startActivity(intent);
            default:
                Log.d(Configuration.click_listener_error,"Switch-Default Error.");
        }
//        Toast.makeText(listener.getContext(),"start your test ! ",Toast.LENGTH_SHORT).show();
//        ActivityManager activityManager = listener.getContext().getSystemService();

    }
}
