package com.ayit.cartoonmaplibrary.map.entity;

/**
 * Created by lny on 2017/3/8.
 */

public class MapDrawableSelector {

    private int normalResId;
    private int pressedResId;

    public MapDrawableSelector() {
    }


    public MapDrawableSelector(int normalResId, int pressedResId) {
        this.normalResId = normalResId;
        this.pressedResId = pressedResId;
    }

    public int getPressedResId() {
        return pressedResId;
    }

    public void setPressedResId(int pressedResId) {
        this.pressedResId = pressedResId;
    }

    public int getNormalResId() {
        return normalResId;
    }

    public void setNormalResId(int normalResId) {
        this.normalResId = normalResId;
    }
}
