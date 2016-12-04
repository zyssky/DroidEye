package com.example.administrator.droideye.Models;

/**
 * Created by wand on 2016/12/4.
 */

public class Configuration {

    //SQLiteDataBase Relevant Settings
    public static final String db_name          = "DroidEye.db";
    public static final String create_header    = "CREATE TABLE IF NOT EXISTS ";
    public static final String db_error_log_head= "[*]DbError:";
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
            "30sTrafficin       TEXT , " +
            "30sTrafficout      TEXT , " +
            "LimitCycle1        TEXT , " +
            "LimitTrafficQuant1 TEXT , " +
            "LimitCycle2        TEXT , " +
            "LimitTrafficQuant2 TEXT , " +
            "LimitCycle3        TEXT , " +
            "LimitTrafficQuant3 TEXT , " +
            "KILLORWARN1      INTEGER, " +
            "KILLORWARN2      INTEGER, " +
            "KILLORWARN3      INTEGER  " +
            ");";
    //
}
