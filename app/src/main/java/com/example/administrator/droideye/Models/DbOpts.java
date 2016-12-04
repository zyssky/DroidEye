package com.example.administrator.droideye.Models;

/**
 * Created by wand on 2016/12/4.
 * This Interface is for doing self defined
 * Database operations including basic CURD
 * and other useful settings
 */

public interface DbOpts {

    //Insert
    void insert_model();
    //Delete
    void delete_model();
    //Update
    void update_model();
    //Query
    void query_model();
}
