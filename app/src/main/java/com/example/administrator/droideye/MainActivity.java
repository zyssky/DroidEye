package com.example.administrator.droideye;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements InteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainView mainView = (MainView) findViewById(R.id.activity_main);
        mainView.init();
        ProcessHandler processHandler = new ProcessHandler(mainView,this);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
