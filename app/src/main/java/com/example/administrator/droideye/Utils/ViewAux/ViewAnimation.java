package com.example.administrator.droideye.Utils.ViewAux;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;

/**
 * Created by wand on 2016/12/7.
 * This class contains several animations
 * on android basic views.
 */

public class ViewAnimation {

    public static void Rotation_Animation(View view, long delay, float startdegree, float exitdegree){

        RotateAnimation animation = new RotateAnimation(startdegree,
                                        exitdegree,
                                        view.getWidth()/2,
                                        view.getHeight()/2);
        animation.setDuration(delay);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

}
