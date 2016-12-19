package com.example.administrator.droideye.Models;

/**
 * Created by wand on 2016/12/4.
 */

public class Configuration {


    //SQLiteDataBase Relevant Settings
    public static final String db_name          = "DroidEye.db";
    public static final String create_header    = "CREATE TABLE IF NOT EXISTS ";
    public static final String create_traffic   = create_header + "traffic(" +
            "appName TEXT PRIMARY KEY ," +
            "startTime          TEXT , " +
            "totalTraffic       TEXT , " + //Using TEXT REPRESENT Bytes Afraid of Overflow.
            "tenMinTrafficin    TEXT , " +
            "tenMinTrafficout   TEXT , " +
            "fiveMinTrafficin   TEXT , " +
            "fiveMinTrafficout  TEXT , " +
            "oneMinTrafficin    TEXT , " +
            "oneMinTrafficout   TEXT , " +
            "_30sTrafficin      TEXT , " +
            "_30sTrafficout     TEXT , " +
            "LimitCycle1        TEXT , " +
            "LimitTrafficQuant1 TEXT , " +
            "LimitCycle2        TEXT , " +
            "LimitTrafficQuant2 TEXT , " +
            "LimitCycle3        TEXT , " +
            "LimitTrafficQuant3 TEXT , " +
            "KILLORWARN1      INTEGER, " +
            "KILLORWARN2      INTEGER, " +
            "KILLORWARN3      INTEGER, " +
            "FOREIGN KEY(appName) REFERENCES appinfo(appName)" +
            ");";
    //

    public static final String create_appinfo = create_header + "appinfo(" +
            "appName TEXT PRIMARY KEY , "     +
            "packageName TEXT ,         "     +
            "appIcon     TEXT ,         "     +
            "InstallTime TEXT ,         "     +
            "privileges  TEXT ,         "     +
            "visitedurls TEXT ,         "     +
            "secinfo     TEXT ,         "     + //Hasn't thinking enough yet.
            "SD_Place    TEXT           "     +
            ");";


    //Logs Path , all should be moved to SDCard Positions.
    public static final String basiclogpath     = "DriodEyelog";
    public static final String actionlogpath    = basiclogpath + "/" + "action_logs";
    public static final String netflowlogpath   = basiclogpath + "/" + "newflow_logs";
    public static final String storagelogpath   = basiclogpath + "/" + "store_logs";
    public static final String defaultFilePath  = "/data/data/com.example.administrator.droideye/files";


    //Errors:
    public static final String db_error_log_head= "[*]DbError:";
    public static final String file_opt_error= "[*]FileOptError:";
    public static final String click_listener_error= "[*]ListenerError:";

}
