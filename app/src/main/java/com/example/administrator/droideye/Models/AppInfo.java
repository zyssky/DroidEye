package com.example.administrator.droideye.Models;

import java.util.List;

/**
 * Created by wand on 2016/12/5.
 */

public class AppInfo{

    //Characters
    public String appName     = null;
    public String packageName = null;
    public String appIcon     = null;
    public String installTime = null;
    public String privileges  = null;
    public String visitedurls = null;
    public String secinfo     = null;
    public String SD_Place    = null;

    //Constructors

    public AppInfo(String appName, String packageName ,String appIcon){

        this.appName        = appName;
        this.packageName    = packageName;
        this.appIcon        = appIcon;
    }

    public AppInfo(String appName,
                   String packageName,
                   String appIcon,
                   String installTime,
                   String privileges,
                   String visitedurls,
                   String secinfo,
                   String SD_Place)
    {
        this.appName            = appName;
        this.packageName        = packageName;
        this.appIcon            = appIcon;
        this.installTime        = installTime;
        this.privileges         = privileges;
        this.visitedurls        = visitedurls;
        this.secinfo            = secinfo;
        this.SD_Place           = SD_Place;
    }


}
