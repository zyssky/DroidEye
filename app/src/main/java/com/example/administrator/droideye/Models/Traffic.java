package com.example.administrator.droideye.Models;

import java.util.List;

/**
 * Created by wand on 2016/12/4.
 */

public class Traffic{

    //Characters in Traffic
    public String appName           = null;
    public String uid               = null;
    public String startTime         = null;
    public String totalTraffic      = null;
    public String tenMinTrafficin   = null;
    public String tenMinTrafficout  = null;
    public String fiveMinTrafficin  = null;
    public String fiveMinTrafficout = null;
    public String oneMinTrafficin   = null;
    public String oneMinTrafficout  = null;
    public String _30sTrafficin     = null;
    public String _30sTrafficout    = null;
    public String LimitCycle1       = null;
    public String LimitTrafficQuant1= null;
    public String LimitCycle2       = null;
    public String LimitTrafficQuant2= null;
    public String LimitCycle3       = null;
    public String LimitTrafficQuant3= null;
    public String KILLORWARN1       = null;
    public String KILLORWARN2       = null;
    public String KILLORWARN3       = null;


    public Traffic(){

    }

    //Simple Init Model
    public Traffic(String appName, String uid, String startTime){

        this.appName   = appName;
        this.uid       = uid;
        this.startTime = startTime;
    }

    public Traffic(String appName, String uid , String startTime, String totalTraffic){

        this.appName        = appName;
        this.uid            = uid;
        this.startTime      = startTime;
        this.totalTraffic   = totalTraffic;
    }

    public Traffic(String appName,
                   String startTime,
                   String totalTraffic,
                   String tenMinTrafficin,
                   String tenMinTrafficout,
                   String fiveMinTrafficin,
                   String fiveMinTrafficout,
                   String oneMinTrafficin,
                   String oneMinTrafficout,
                   String _30sTrafficin,
                   String _30sTrafficout,
                   String LimitCycle1,
                   String LimitTrafficQuant1,
                   String LimitCycle2,
                   String LimitTrafficQuant2,
                   String LimitCycle3,
                   String LimitTrafficQuant3,
                   String KILLORWARN1,
                   String KILLORWARN2,
                   String KILLORWARN3)
    {

        this.appName                = appName;
        this.startTime              = startTime;
        this.totalTraffic           = totalTraffic;
        this.tenMinTrafficin        = tenMinTrafficin;
        this.tenMinTrafficout       = tenMinTrafficout;
        this.fiveMinTrafficin       = fiveMinTrafficin;
        this.fiveMinTrafficout      = fiveMinTrafficout;
        this.oneMinTrafficin        = oneMinTrafficin;
        this.oneMinTrafficout       = oneMinTrafficout;
        this._30sTrafficin          = _30sTrafficin;
        this._30sTrafficout         = _30sTrafficout;
        this.LimitCycle1            = LimitCycle1;
        this.LimitTrafficQuant1     = LimitTrafficQuant1;
        this.LimitCycle2            = LimitCycle2;
        this.LimitTrafficQuant2     = LimitTrafficQuant2;
        this.LimitCycle3            = LimitCycle3;
        this.LimitTrafficQuant3     = LimitTrafficQuant3;
        this.KILLORWARN1            = KILLORWARN1;
        this.KILLORWARN2            = KILLORWARN2;
        this.KILLORWARN3            = KILLORWARN3;
    }
}
