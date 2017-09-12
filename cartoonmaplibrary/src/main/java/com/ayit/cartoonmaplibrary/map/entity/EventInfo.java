package com.ayit.cartoonmaplibrary.map.entity;

import android.graphics.Point;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lny on 2017/3/6.
 */

public class EventInfo {
    private int pointerCount;
    private List<Point> pointList;
    private Point center;


    public EventInfo(MotionEvent event) {
        copyEventInfo(event);
    }

    public int getX(){
        return  (int)(pointList.get(0).x);
    }
    public int getY(){
        return  (int)(pointList.get(0).y);
    }
    public int getX(int i){
        return  (int)(pointList.get(i).x);
    }
    public int getY(int i){
        return  (int)(pointList.get(i).y);
    }

    public void copyEventInfo(MotionEvent event){
        pointList = new ArrayList<>();
        pointerCount = event.getPointerCount();
        for (int i=0;i<event.getPointerCount();i++){
            pointList.add(new Point((int) event.getX(i),(int)event.getY(i)));
        }
        center = getCenter(event);
    }

    public int getPointerCount(){
        return  pointerCount;
    }

    public Point getCnter(){
        return center;
    }


    private Point getCenter(MotionEvent event) {
        Point point = new Point();
        float x = 0;
        float y = 0;
        for (int i = 0; i < event.getPointerCount(); i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        point.set((int) (x / event.getPointerCount()), (int) (y / event.getPointerCount()));
        return point;
    }

}
