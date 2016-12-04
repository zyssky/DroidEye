package com.example.administrator.droideye.Models.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import static com.example.administrator.droideye.Models.Configuration.create_appinfo;
import static com.example.administrator.droideye.Models.Configuration.create_traffic;


/**
 * Created by wand on 2016/11/20.
 * This class is for establishing
 * a sql-table for storing data locally
 * implementing CURD Operations.
 */

public class SQLite extends SQLiteOpenHelper{

    private Context mContext;

    //Invoke each time when need using the database.
    //Db Version must be an integer increasing.
    public SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){

        super(context, name, factory, version);
        mContext = context;

    }

    //Invoke when first--Create.
    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(create_appinfo);
        db.execSQL(create_traffic);
        //Show Debug Info
        Toast.makeText(mContext, "Create Tables Succeed.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db){

    }

}
