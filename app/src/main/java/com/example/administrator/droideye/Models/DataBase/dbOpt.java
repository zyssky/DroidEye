package com.example.administrator.droideye.Models.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.droideye.Models.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wand on 2016/11/21.
 */

public class dbOpt {

    //Init Database At the beginning of the program.
    public SQLite dbOperator;
    public static Context mContext;
    private SQLiteDatabase db = null;
    public dbOpt(){

        dbOperator = new SQLite(mContext, Configuration.db_name,null ,1);
        //Really Create Database:
        db = dbOperator.getReadableDatabase();
    }

    //Insert Mods:
    //Waiting to be finish here.

    //Delete Mods:
    public void delete_func(String tablename, String column_name, String evalue){

        try{
            db.execSQL("DELETE FROM " + tablename + " WHERE " + column_name + " =?" , new Object[]{evalue});
        }catch(Exception e){
            Log.d(Configuration.db_error_log_head,e.toString());
            Toast.makeText(mContext,"Deleting " + column_name + " in " + tablename + " Failed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete_all(String tablename){
        //Invoke with Cautious!
        //Will empty whole table.
        try{
            db.execSQL("DELETE FROM " + tablename);
        }catch(Exception e){
            Log.d(Configuration.db_error_log_head,e.toString());
            Toast.makeText(mContext,"Deleteing Table " + tablename + " Failed.", Toast.LENGTH_SHORT).show();
        }
    }



    //Update Mods:
    public void update_table(String table_name, String column_name, String condition_column,String oldvalue, String newvalue){

        try{

            db.execSQL("UPDATE " + table_name + " SET " + column_name + " =? " + "WHERE " + condition_column + " =?",
                    new Object[]{newvalue,oldvalue});
        }catch(Exception e){
            Log.d(Configuration.db_error_log_head,e.toString());
            Toast.makeText(mContext,"Updating Table " + table_name + " Failed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void userdef_update(String update_SQL, String[] values){

        try{
            db.execSQL(update_SQL,values);

        }catch(Exception e){
            Log.d(Configuration.db_error_log_head, e.toString());
            Toast.makeText(mContext,"Updating Table " + " Failed.", Toast.LENGTH_SHORT).show();
        }

    }

    //Basic - Query Mods:
    public List<Object> query_info(String table_name, String query_column, String value){

        List<Object> ret = new ArrayList<Object>();
        try {
            if (query_column.length() == 0) {
                //Query-all
                Cursor cursor = null;
                switch (table_name) {

                    case "user":
                        cursor = db.rawQuery("SELECT * FROM user", null);
                        while (cursor.moveToNext()) {


                        }
                        cursor.close();
                        return ret;

                    default:

                        Log.d(Configuration.db_error_log_head, "Query With Wrong Table Name: " + table_name);
                        return null;
                }

            } else {
                Cursor cursor = null;
                switch (table_name) {

                    case "user":
                        cursor = db.rawQuery("SELECT * FROM user " + " WHERE " + query_column + " =?", new String[]{value});
                        while (cursor.moveToNext()) {


                        }
                        cursor.close();
                        return ret;

                    default:

                        Log.d(Configuration.db_error_log_head, "Query With Wrong Table Name: " + table_name);
                        return null;
                }
            }
        }
        catch(Exception e){
            Log.d(Configuration.db_error_log_head,e.toString());
            Toast.makeText(mContext,"Query-Info At User Table Failed.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    //Self-DEFINED Query Func---
    public List<Object> userdef_query(String table_name, String Query_SQL, String[] values){

        List<Object> ret = new ArrayList<Object>();
        //WARNING: Using This Func needs to be with caution!
        try {
            Cursor cursor = null;


                switch (table_name) {

                    case "user":
                        cursor = db.rawQuery(Query_SQL, values);
                        while(cursor.moveToNext()){


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

    public void close_db(){

        db.close();
    }
}
