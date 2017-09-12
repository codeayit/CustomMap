package com.ayit.cartoonmaplibrary.map.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.ayit.cartoonmaplibrary.map.entity.EventInfo;
import com.ayit.cartoonmaplibrary.map.entity.ViewSize;


/**
 * Created by lny on 2017/3/8.
 */

public class MapUtil {


    /**
     * 获取空间的宽高
     * @param view
     * @return
     */
    public static ViewSize getViewSize(View view){
        return  new ViewSize(view.getWidth(),view.getHeight());
    }


    public static   ViewSize getWindowSize(Context context){
        DisplayMetrics metric = new DisplayMetrics();
        ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        return new ViewSize(width,height);
    }

    /**
     * 获取对角线的长度
     * @param view
     * @return
     */
    public static  float getViewDiagonalLenght(View view){
        ViewSize size = getViewSize(view);
        return  (float) Math.pow((size.getHeight()*size.getHeight()+size.getWidth()*size.getWidth()),0.5);
    }

    /**
     * 获取两个事件间的距离差
     * @param lastEvent
     * @param currentEvent
     * @return
     */
    public static float getDistanceBetweenEvent(EventInfo lastEvent, EventInfo currentEvent) {
        float distence = 0;
        if (lastEvent != null) {
            for (int i = 0; i < lastEvent.getPointerCount(); i++) {
                if (currentEvent.getPointerCount()== i) {
                    currentEvent = lastEvent;
                }
                distence += (getDistenceBetweenPoint(currentEvent.getX(i),currentEvent.getY(i),currentEvent.getCnter().x,currentEvent.getCnter().y)-getDistenceBetweenPoint(lastEvent.getX(i),lastEvent.getY(i),lastEvent.getCnter().x,lastEvent.getCnter().y));
            }
        }
        return distence;
    }

    /**
     * 获取亮点间的距离
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static  float getDistenceBetweenPoint(float x1,float y1,float x2,float y2){
        return (float) Math.pow((Math.pow(x1 - x2, 2)) + (Math.pow(y1 - y2, 2)), 0.5);
    }


    public static String printAction(MotionEvent event) {
        String action = "";
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action = "action_down";
                break;
            case MotionEvent.ACTION_UP:
                action = "action_up";
                break;
            case MotionEvent.ACTION_MOVE:
                action = "action_move";
                break;
            case MotionEvent.ACTION_MASK:
                action = "action_mask";
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                action = "action_pointer_down";
                break;
            case MotionEvent.ACTION_POINTER_UP:
                action = "action_pointer_up";
                break;
            case MotionEvent.ACTION_CANCEL:
                action = "action_cancle";
                break;
            default:
                action = Integer.toOctalString(event.getAction()) + "";
                break;
        }
        LogUtil.log(action + " : " + event.getPointerCount());
        return action + " : " + event.getPointerCount();
    }


    public static int px2dp(Context context,int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static int dp2px(Context context,int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

}
