package com.example.administrator.droideye.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wand on 2016/12/8.
 */

public class StatusLogger implements Serializable{

    public HashMap<String , String> Status;

    public StatusLogger(){
        Status = new HashMap<>();
    }

    public void addAttrs(String key ,String value){

        Status.put(key,value);
    }

    public boolean deleteAttrs(String key){

        boolean find = false;
        Iterator it = Status.keySet().iterator();
        while(it.hasNext()){

            String temp = (String)it.next();
            if ( temp.equals(key)){
                //Find Target
                Status.remove(key);
                find = true;
                break;
            }
        }
        return find;
    }
}

