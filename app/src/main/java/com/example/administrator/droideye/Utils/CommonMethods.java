package com.example.administrator.droideye.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/12/8.
 */

public final class CommonMethods {
    public static void changeFragment(AppCompatActivity activity, int dest_id, Fragment fragment){
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(dest_id,fragment).commit();
    }
}
