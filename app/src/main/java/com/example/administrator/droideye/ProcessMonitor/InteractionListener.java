package com.example.administrator.droideye.ProcessMonitor;

import android.content.Context;

/**
 * Created by Administrator on 2016/12/3.
 */

public interface InteractionListener {
    Context getActivity();

    void close();
}
