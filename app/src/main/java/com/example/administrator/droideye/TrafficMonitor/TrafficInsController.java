package com.example.administrator.droideye.TrafficMonitor;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.administrator.droideye.Models.Configuration;
import com.example.administrator.droideye.R;
import com.example.administrator.droideye.Utils.FileUtils;
import com.example.administrator.droideye.Utils.ViewAux.ViewAnimation;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wand on 2016/12/3.
 */

public class TrafficInsController implements View.OnClickListener,AdapterView.OnItemClickListener{

    private TrafficInsView view;
    private TrafficInsListener listener;
    private List<HashMap<String,Object>> trafficItems = new ArrayList<HashMap<String, Object>>();
    private SimpleAdapter trafficAdapter;
    int counter = 0;

    public TrafficInsController(TrafficInsView view , TrafficInsListener listener){

        AppTrafficMonitor trafficMonitor = new AppTrafficMonitor(listener);
        trafficItems = trafficMonitor.showApps();
        this.view = view;
        this.listener = listener;
        initAdapter();
        initsValueOnView();
    }

    public void initAdapter(){

        this.trafficAdapter = new SimpleAdapter(listener.getAppContext(),trafficItems, R.layout.trafficlst_item,
                new String[] {"AppIcon","AppName","traffic"},
                new int[] {R.id.AppIcon,R.id.AppName,R.id.traffic});

        trafficAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String arg2) {
                if (view instanceof ImageView && data instanceof Drawable){
                    ImageView iv = (ImageView)view;
                    iv.setImageDrawable((Drawable)data);
                    return true;
                }
                return false;
            }
        });
    }

    public void initsValueOnView(){

        this.view.setImageBtnClickListener(this);
        this.view.setTrafficListViewAdapter(trafficAdapter);
        this.view.setTrafficListListener(this);
    }

    @Override
    public void onClick(View v){

        //Test Our Class here.
        //Toast.makeText(listener.getAppContext(),"start your test ! ",Toast.LENGTH_SHORT).show();
        //AppTrafficMonitor trafficMonitor = new AppTrafficMonitor(listener);
        //trafficMonitor.trafficmonitor();
        switch (v.getId()){

            case R.id.rudder:
                if (counter%2 == 0)
                {
                    ViewAnimation.Rotation_Animation(view.menuButton,700,0,-90);
                    counter ++;
                    //Adding effects of eliminating button
//                    view.setBtnTransparent(R.id.five);
//                    Animation animation = AnimationUtils.loadAnimation(listener.getAppContext(), R.anim.btn_anim);
                    Animation animation = ViewAnimation.btn_shiftAnimation(1000,0,-6,0,0,1,0);
                    view.initAnim(R.id.one, animation);
                    view.setInVisible(R.id.one);
                    view.initAnim(R.id.five, animation);
                    view.setInVisible(R.id.five);
                    view.initAnim(R.id.ten, animation);
                    view.setInVisible(R.id.ten);
                }
                else{
                    ViewAnimation.Rotation_Animation(view.menuButton,700,-90,0);
                    counter ++;
                    Animation animation = ViewAnimation.btn_shiftAnimation(1000,-6,0,0,0,0,1);

                    view.initAnim(R.id.one, animation);
                    view.setVisible(R.id.one);
                    view.initAnim(R.id.five,animation);
                    view.setVisible(R.id.five);
                    view.initAnim(R.id.ten, animation);
                    view.setVisible(R.id.ten);

                }
                FileUtils.createFile("tester");
                FileUtils.createFolder(Configuration.defaultFilePath,"/ABCDEFG");
                FileUtils.createFolder("/system","/ABCDEFG");
                Toast.makeText(listener.getAppContext(),"This is a simple test.",Toast.LENGTH_SHORT).show();
                break;

            case R.id.ten:

                break;

            case R.id.five:

                break;

            case R.id.one:

                break;

            default:
                Toast.makeText(listener.getAppContext(),"Listener Doesn't find target item.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position , long id){

        switch(parent.getId()){

            case R.id.trafficList:

                ShowTrafficDetail(position);
        }

    }

    public void ShowTrafficDetail(int position){

        Intent intent = new Intent(listener.getAppContext(), TrafficDetailActivity.class);
        HashMap map   = trafficItems.get(position);
        Bitmap bmp    = ((BitmapDrawable)(map.get("AppIcon"))).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100 , baos);
        byte[] bitmapBytes = baos.toByteArray();
        intent.putExtra("AppIcon", bitmapBytes);
        intent.putExtra("AppName", (String)map.get("AppName"));
        intent.putExtra("packageName", (String)map.get("packageName"));
        listener.getAppContext().startActivity(intent);

    }

}
