package com.example.administrator.droideye;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/12/3.
 */

public class MainView extends RelativeLayout {
    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Button testBtn;

    public void init(){
        testBtn = (Button) findViewById(R.id.test);

    }

    public void setTestBtnListener(OnClickListener listener){
        testBtn.setOnClickListener(listener);
    }
}
