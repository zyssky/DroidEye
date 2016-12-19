package com.example.administrator.droideye.Models.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.droideye.Models.AppInfo;
import com.example.administrator.droideye.Models.Configuration;
import com.example.administrator.droideye.Models.Traffic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wand on 2016/11/21.
 */

public class dbOpt {

    //Init Database At the beginning of the program.
    public SQLite dbOperator;
    private SQLiteDatabase db = null;
    private Context mContext;
    public dbOpt(Context mContext){

        this.mContext = mContext;
        dbOperator = new SQLite(mContext, Configuration.db_name,null ,1);
        //Really Create Database:
        db = dbOperator.getReadableDatabase();
    }

    //User-def Mod:
    public boolean EXEC_SQL(String _SQL, String[] values){

        try{
            db.execSQL(_SQL,values);
            return true;
        }catch (Exception e){
            Log.d(Configuration.db_error_log_head, e.toString());
            Toast.makeText(mContext,"EXEC " + _SQL + " failed.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //Default Insert Mod:
    public boolean add_appinfo(AppInfo appInfo){

        try{
            db.execSQL("INSERT INTO appinfo(appName," +
                    "uid,"+
                    "packageName," +
                    "appIcon," +
                    "InstallTime," +
                    "privileges," +
                    "visitedurls," +
                    "secinfo," +
                    "SD_Place)" +
                    " values(?,?,?,?,?,?,?,?)", new Object[]{appInfo.appName,
                    appInfo.uid,
                    appInfo.packageName,
                    appInfo.appIcon,
                    appInfo.installTime,
                    appInfo.privileges,
                    appInfo.visitedurls,
                    appInfo.secinfo,
                    appInfo.SD_Place});
            return true;
        }catch (Exception e){
            Log.d(Configuration.db_error_log_head, e.toString());
            return false;
        }
    }

    public boolean add_traffic(Traffic traffic){
        try{
            db.execSQL("INSERT INTO traffic(appName," +
                    "startTime," +
                    "totalTraffic," +
                    "tenMinTrafficin," +
                    "tenMinTrafficout," +
                    "fiveMinTrafficin," +
                    "fiveMinTrafficout," +
                    "oneMinTrafficin," +
                    "oneMinTrafficout," +
                    "_30sTrafficin," +
                    "_30sTrafficout," +
                    "LimitCycle1," +
                    "LimitTrafficQuant1," +
                    "LimitCycle2," +
                    "LimitTrafficQuant2," +
                    "LimitCycle3," +
                    "LimitTrafficQuant3," +
                    "KILLORWARN1," +
                    "KILLORWARN2," +
                    "KILLORWARN3)" +
                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{

                            traffic.appName,
                            traffic.startTime,
                            traffic.totalTraffic,
                            traffic.tenMinTrafficin,
                            traffic.tenMinTrafficout,
                            traffic.fiveMinTrafficin,
                            traffic.fiveMinTrafficout,
                            traffic.oneMinTrafficin,
                            traffic.oneMinTrafficout,
                            traffic._30sTrafficin,
                            traffic._30sTrafficout,
                            traffic.LimitCycle1,
                            traffic.LimitTrafficQuant1,
                            traffic.LimitCycle2,
                            traffic.LimitTrafficQuant2,
                            traffic.LimitCycle3,
                            traffic.LimitTrafficQuant3,
                            traffic.KILLORWARN1,
                            traffic.KILLORWARN2,
                            traffic.KILLORWARN3
                    }
            );
            return true;
        }catch (Exception e){
            Log.d(Configuration.db_error_log_head, e.toString());
            return false;
        }
    }

    //Delete Mods:
    public boolean delete_func(String tablename, String column_name, String evalue){

        try{
            db.execSQL("DELETE FROM " + tablename + " WHERE " + column_name + " =?" , new Object[]{evalue});
            return true;
        }catch(Exception e){
            Log.d(Configuration.db_error_log_head,e.toString());
            Toast.makeText(mContext,"Deleting " + column_name + " in " + tablename + " Failed.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean delete_all(String tablename){
        //Invoke with Cautious!
        //Will empty whole table.
        try{
            db.execSQL("DELETE FROM " + tablename);
            return true;
        }catch(Exception e){
            Log.d(Configuration.db_error_log_head,e.toString());
            Toast.makeText(mContext,"Deleteing Table " + tablename + " Failed.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    //Update Mods:
    public boolean update_table(String table_name, String column_name, String condition_column,String oldvalue, String newvalue){

        try{

            db.execSQL("UPDATE " + table_name + " SET " + column_name + " =? " + "WHERE " + condition_column + " =?",
                    new Object[]{newvalue,oldvalue});
            return true;
        }catch(Exception e){
            Log.d(Configuration.db_error_log_head,e.toString());
            Toast.makeText(mContext,"Updating Table " + table_name + " Failed.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    //Self-DEFINED Query Func---
    public List<Object> userdef_query(String table_name, String Query_SQL, String[] values){

        List<Object> ret = new ArrayList<Object>();
        //WARNING: Using This Func needs to be with caution!
        try {
            Cursor cursor = null;

                switch (table_name) {

                    case "appinfo":
                        cursor = db.rawQuery(Query_SQL, values);
                        while(cursor.moveToNext()){

                            AppInfo appinfo = null;
                            ret.add(appinfo);
                        }
                        cursor.close();
                        return ret;
                    case "traffic":
                        cursor = db.rawQuery(Query_SQL, values);
                        while (cursor.moveToNext()){

                            Traffic traffic = null;
                            ret.add(traffic);
                        }
                        cursor.close();
                        return ret;


                    default:

                        Log.d(Configuration.db_error_log_head, "Error with SelfDef Query");
                        return null;
                }

        }catch(Exception e){
            Log.d(Configuration.db_error_log_head,"Error When SelfDef Query At Table : " + table_name);
            return null;
        }
    }

    public boolean close_db(){
        try {
            db.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
