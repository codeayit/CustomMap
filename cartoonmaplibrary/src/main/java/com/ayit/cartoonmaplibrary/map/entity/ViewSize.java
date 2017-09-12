package com.ayit.cartoonmaplibrary.map.entity;

/**
 * Created by lny on 2017/3/7.
 */

public class ViewSize {
    private int width;
    private int height;

    public ViewSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "ViewSize{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
