package com.example.administrator.droideye.Service;

import android.util.Log;

import com.example.administrator.droideye.Models.Configuration;
import com.example.administrator.droideye.Models.DataBase.dbOpt;
import com.example.administrator.droideye.Models.Traffic;
import com.example.administrator.droideye.TrafficMonitor.AppTrafficMonitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wand on 2016/12/29.
 */

public class trafficMonit extends Thread{

    dbOpt dbopt = null;
    List<Traffic> traffics = new ArrayList<>();

    public trafficMonit(dbOpt dbopt, List<Traffic> traffics){

        this.dbopt = dbopt;
        this.traffics = traffics;
    }

    @Override
    public void run() {

        //Three thread for running threads

        oneMinMonitor one = new oneMinMonitor( traffics ,this.dbopt);
        one.start();
        fiveMinMonitor five = new fiveMinMonitor(traffics ,this.dbopt);
        five.start();
        tenMinMonitor ten = new tenMinMonitor(traffics ,this.dbopt);
        ten.start();

        while (true) {

            try {
                //Sleep And Write Data Into Database.
                Thread.sleep(10000);
            } catch (Exception e) {
                Log.d(Configuration.threadsleeperror, e.toString());
            }

            //Check Wifi Or Mobile Network
        }
    }

//
//    public void checkWifi(){
//
//
//    }
}


class oneMinMonitor extends Thread{

        List<Traffic> traffics = new ArrayList<>();
        dbOpt dbopt      = null;

        public oneMinMonitor(List<Traffic> traffics, dbOpt dbopt){

            this.traffics = traffics;
            this.dbopt    = dbopt;
        }

        @Override
        public void run(){

            //Init Monitor
            while(true){

                try {
                    //Sleep And Write Data Into Database.
                    Thread.sleep(60000);
                }catch(Exception e){
                    Log.d(Configuration.threadsleeperror, e.toString());
                }

                //Static Traffics
                for(Traffic traffic : traffics){

                    String appName = traffic.appName;
                    String uid     = traffic.uid;
                    String totalTraffic = traffic.totalTraffic;
                    String oneMinTraffic= null;
                    if(Configuration.inwifimode)
                    oneMinTraffic= traffic.oneMinTrafficin;
                    else
                        oneMinTraffic = traffic.oneMinTrafficout;

                    oneMinTraffic += AppTrafficMonitor.getTrafficIn(Integer.parseInt(uid)) +
                            AppTrafficMonitor.getTrafficOut(Integer.parseInt(uid));

                    if(Configuration.inwifimode){
                        traffic.oneMinTrafficin = oneMinTraffic;
                    }else{
                        traffic.oneMinTrafficout = oneMinTraffic;
                    }
//                    dbopt.selfdef_update_table("UPDATE traffic SET oneMinTrafficIn = ? WHERE appName = ?", {oneMinTraffic});
                }
            }
        }

}



class fiveMinMonitor extends Thread{

    List<Traffic> traffics = new ArrayList<>();
    dbOpt dbopt      = null;

    public fiveMinMonitor(List<Traffic> traffics, dbOpt dbopt){

        this.traffics = traffics;
        this.dbopt    = dbopt;
    }

    @Override
    public void run(){

        //Init Monitor
        while(true){

            try {
                //Sleep And Write Data Into Database.
                Thread.sleep(300000);
            }catch(Exception e){
                Log.d(Configuration.threadsleeperror, e.toString());
            }

            //Static Traffics
            for(Traffic traffic : traffics){

                String appName = traffic.appName;
                String uid     = traffic.uid;
                String totalTraffic = traffic.totalTraffic;
                String fiveMinTraffic= null;
                if(Configuration.inwifimode)
                    fiveMinTraffic= traffic.fiveMinTrafficin;
                else
                    fiveMinTraffic = traffic.fiveMinTrafficout;

                fiveMinTraffic += AppTrafficMonitor.getTrafficIn(Integer.parseInt(uid)) +
                        AppTrafficMonitor.getTrafficOut(Integer.parseInt(uid));

                if(Configuration.inwifimode){
                    traffic.fiveMinTrafficin = fiveMinTraffic;
                }else{
                    traffic.fiveMinTrafficout = fiveMinTraffic;
                }
                dbopt.add_traffic(traffic);
            }
        }
    }

}


class tenMinMonitor extends Thread{

    List<Traffic> traffics = new ArrayList<>();
    dbOpt dbopt      = null;

    public tenMinMonitor(List<Traffic> traffics, dbOpt dbopt){

        this.traffics = traffics;
        this.dbopt    = dbopt;
    }

    @Override
    public void run(){

        //Init Monitor
        while(true){

            try {
                //Sleep And Write Data Into Database.
                Thread.sleep(600000);
            }catch(Exception e){
                Log.d(Configuration.threadsleeperror, e.toString());
            }

            //Static Traffics
            for(Traffic traffic : traffics){

                String appName = traffic.appName;
                String uid     = traffic.uid;
                String totalTraffic = traffic.totalTraffic;
                String tenMinTraffic= null;
                if(Configuration.inwifimode)
                    tenMinTraffic= traffic.tenMinTrafficin;
                else
                    tenMinTraffic = traffic.tenMinTrafficout;

                tenMinTraffic += AppTrafficMonitor.getTrafficIn(Integer.parseInt(uid)) +
                        AppTrafficMonitor.getTrafficOut(Integer.parseInt(uid));

                if(Configuration.inwifimode){
                    traffic.tenMinTrafficin = tenMinTraffic;
                }else{
                    traffic.tenMinTrafficout = tenMinTraffic;
                }
                dbopt.add_traffic(traffic);
            }
        }
    }

}
