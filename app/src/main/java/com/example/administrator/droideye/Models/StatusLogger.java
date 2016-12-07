package com.example.administrator.droideye.Models;

import java.io.Serializable;

/**
 * Created by wand on 2016/12/8.
 */

public class StatusLogger implements Serializable{

    private boolean isFirstRun;

    public StatusLogger(){


    }

    public boolean ifFirstRun(){

        return isFirstRun;
    }

    public void setNotFirstRun(){

        isFirstRun = false;
    }
}

