package com.example.administrator.droideye;

import android.app.ActivityManager;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/3.
 */

public class MainController implements View.OnClickListener {
    private MainView view;
    private InteractionListener listener;

    public MainController(MainView view, InteractionListener listener){
        this.view = view;
        this.listener = listener;
        initValuesOnView();
    }

    void initValuesOnView(){
        view.setTestBtnListener(this);

    }

    @Override
    public void onClick(View v) {

        Toast.makeText(listener.getContext(),"start your test ! ",Toast.LENGTH_SHORT).show();
//        ActivityManager activityManager = listener.getContext().getSystemService();

    }
}
