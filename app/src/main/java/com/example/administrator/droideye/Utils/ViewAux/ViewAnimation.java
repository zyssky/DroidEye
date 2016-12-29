package com.example.administrator.droideye.Utils.ViewAux;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;

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

    public static Animation btn_shiftAnimation(long delay,float Xinit, float Xshift, float Yinit, float Yshift, float startalpha, float endalpha){

        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(

                Animation.RELATIVE_TO_SELF, Xinit,
                Animation.RELATIVE_TO_SELF, Xshift,
                Animation.RELATIVE_TO_SELF, Yinit,
                Animation.RELATIVE_TO_SELF, Yshift
        );
        translateAnimation.setDuration(delay);

        AlphaAnimation alphaAnimation = new AlphaAnimation(startalpha,endalpha);
        alphaAnimation.setDuration(delay);

        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);

        return animationSet;
    }


    public static void SlideIn(View view, long delay){


    }

    public static void SlideOut(View view, long delay){


    }

}
