package com.ayit.cartoonmaplibrary.map;

import android.content.Context;
import android.graphics.Point;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


import com.ayit.cartoonmaplibrary.map.entity.EventInfo;
import com.ayit.cartoonmaplibrary.map.entity.ViewSize;
import com.ayit.cartoonmaplibrary.map.utils.LogUtil;
import com.ayit.cartoonmaplibrary.map.utils.MapUtil;
import com.ayit.cartoonmaplibrary.map.view.MapButton;
import com.nineoldandroids.view.ViewHelper;


/**
 * Created by lny on 2017/3/8.
 */

public class MapController {
    private Context context;
    private GestureDetector detector;
    private View mapView;
    private float scrollX;
    private float scrollY;
    private float mapViewDiagonal;
    private float currentMapViewDiagonal;
    private ViewSize mapViewSize;
    private EventInfo lastEvent;
    private float maxScale;
    private float minScale;
    private float iniScale;
    private Point lastPosition;
    private MapButton mapButton;

    public void bindMapButton(MapButton button) {
        this.mapButton = button;
    }

    public void unBindMapButton() {
        this.mapButton = null;
    }

    public MapButton getBindedMapButton() {
        return mapButton;
    }

    public Point getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(Point lastPosition) {
        this.lastPosition = lastPosition;
    }

    public float getIniScale() {
        return iniScale;
    }

    public void setIniScale(float iniScale) {
        this.iniScale = iniScale;
    }

    public float getMaxScale() {
        return maxScale;
    }

    public void setMaxScale(float maxScale) {
        this.maxScale = maxScale;
    }

    public float getMinScale() {
        return minScale;
    }

    public void setMinScale(float minScale) {
        this.minScale = minScale;
    }

    public float getCurrentMapViewDiagonal() {
        return currentMapViewDiagonal;
    }

    public void setCurrentMapViewDiagonal(float currentMapViewDiagonal) {
        this.currentMapViewDiagonal = currentMapViewDiagonal;
    }


    public EventInfo getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(EventInfo lastEvent) {
        this.lastEvent = lastEvent;
    }

    public MapController(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    /**
     * 绑定地图
     *
     * @param mapView
     */
    public void bindMapView(View mapView) {
        bindMapView(mapView, Integer.MAX_VALUE, 0, 1);
    }

    /**
     *
     * @param mapView  地图空间
     * @param maxScale  最大的放大倍数
     * @param minScale  最小的缩小倍数
     * @param iniScale  初始化倍数
     */
    public void bindMapView(final View mapView, final float maxScale, final float minScale, final float iniScale) {
        if (maxScale<1 ||maxScale<minScale|| minScale<0 || iniScale>maxScale || iniScale<minScale){
            throw new RuntimeException("bindMapView maxScale<1 ||maxScale<minScale|| minScale<0 || iniScale>maxScale || iniScale<minScale");
        }
        mapView.post(new Runnable() {
            @Override
            public void run() {
                //在消息队列中读取mapView 的 宽高尺寸
                setMapView(mapView);
                setMaxScale(maxScale);
                setMinScale(minScale);
                setIniScale(iniScale);
                init();
            }
        });
    }


    public View getMapView() {
        return mapView;
    }

    public void setMapView(View mapView) {
        this.mapView = mapView;
    }

    public float getScrollX() {
        return scrollX;
    }

    public void setScrollX(float scrollX) {
        this.scrollX = scrollX;
    }

    public float getScrollY() {
        return scrollY;
    }

    public void setScrollY(float scrollY) {
        this.scrollY = scrollY;
    }

    public float getMapViewDiagonal() {
        return mapViewDiagonal;
    }

    public void setMapViewDiagonal(float mapViewDiagonal) {
        this.mapViewDiagonal = mapViewDiagonal;
    }


    public ViewSize getMapViewSize() {
        return mapViewSize;
    }

    public void setMapViewSize(ViewSize mapViewSize) {
        this.mapViewSize = mapViewSize;
    }

    private ViewSize getMapScaleViewSize() {
        float scale = getCurrentMapViewDiagonal() / getMapViewDiagonal();
        return new ViewSize((int) (getMapViewSize().getWidth() * scale), (int) (getMapViewSize().getHeight() * scale));
    }

    /**
     * 初始化数据
     */
    private void init() {
        scrollX = 0;
        scrollY = 0;
        float tempMapViewDiagonal = MapUtil.getViewDiagonalLenght(getMapView());
        setCurrentMapViewDiagonal(tempMapViewDiagonal * getIniScale());
        setMapViewDiagonal(tempMapViewDiagonal);
        ViewSize tempMapViewSize = MapUtil.getViewSize(getMapView());
        setMapViewSize(tempMapViewSize);
        ViewHelper.setScaleY(getMapView(), iniScale);
        ViewHelper.setScaleX(getMapView(), iniScale);
        detector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                onMapScroll(distanceX, distanceY);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }

    /**
     * 绑定touch事件
     *
     * @param event
     * @return
     */
    public boolean bindEvent(MotionEvent event) {
        MapUtil.printAction(event);
        return bindEvent(event, getBindedMapButton());
    }

    private boolean bindEvent(MotionEvent event, MapButton btn) {
        this.detector.onTouchEvent(event);
        updateButtonDrawable(event, btn);
        doScaleMap(event);
        return true;
    }

    /**
     * 执行缩放
     *
     * @param event
     */
    private void doScaleMap(MotionEvent event) {
        EventInfo lastEvent = getLastEvent();
        EventInfo currentEvent = new EventInfo(event);
        if (event.getPointerCount() >= 2) {
            if (MotionEvent.ACTION_MOVE == event.getAction()) {
                float temDuration = getCurrentMapViewDiagonal() + MapUtil.getDistanceBetweenEvent(lastEvent, currentEvent) * 3;
                float scale = temDuration / getMapViewDiagonal();
                if (scale <= getMaxScale() && scale >= getMinScale()) {
                    ViewHelper.setScaleX(getMapView(), scale);
                    ViewHelper.setScaleY(getMapView(), scale);
                    setCurrentMapViewDiagonal(temDuration);
                } else {
                    if (scale < getMinScale()) {
                        scale = getMinScale();
                        setCurrentMapViewDiagonal(getMapViewDiagonal() * getMinScale());
                    } else if (scale > getMaxScale()) {
                        scale = getMaxScale();
                        setCurrentMapViewDiagonal(getMapViewDiagonal() * getMaxScale());
                    }
                    ViewHelper.setScaleX(getMapView(), scale);
                    ViewHelper.setScaleY(getMapView(), scale);
                }
                LogUtil.log("scale : " + scale);
            }
        }

        setLastEvent(currentEvent);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setLastEvent(null);
                break;
        }
    }


    private void updateButtonDrawable(MotionEvent event, MapButton btn) {
        if (btn == null || btn.getDrawableSelector() == null || btn.getOnMapButtonClickListener() == null) {
            return;
        }
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            btn.setImageResource(btn.getDrawableSelector().getPressedResId());
            setLastPosition(new Point((int) event.getX(), (int) event.getY()));
        } else if (MotionEvent.ACTION_CANCEL == event.getAction() ||
                MotionEvent.ACTION_UP == event.getAction()) {
            btn.setImageResource(btn.getDrawableSelector().getNormalResId());
            Point point = getLastPosition();
            if (point != null) {
                float dis = MapUtil.getDistenceBetweenPoint(getLastPosition().x, getLastPosition().y, event.getX(), event.getY());
                if (dis <= 10) {
                    btn.getOnMapButtonClickListener().onClick(btn);
                }
            }
            unBindMapButton();
        }
    }


    /**
     * 当地图滑动
     *
     * @param distanceX
     * @param distanceY
     */
    private void onMapScroll(float distanceX, float distanceY) {
        float temX = getScrollX() - distanceX;
        float temY = getScrollY() - distanceY;
        ViewSize pSize = getMapScaleViewSize();
        ViewSize nSize = getMapViewSize();

        int sideMX = (pSize.getWidth() - nSize.getWidth()) / 2;
        int sideMY = (pSize.getHeight() - nSize.getHeight()) / 2;

        int sideLX = -(pSize.getWidth() - nSize.getWidth()) / 2;
        int sideLY = -(pSize.getHeight() - nSize.getHeight()) / 2;
        if (temX > sideMX) {
            temX = sideMX;
        } else if (temX < sideLX) {
            temX = sideLX;
        }
        if (temY > sideMY) {
            temY = sideMY;
        } else if (temY < sideLY) {
            temY = sideLY;
        }
        LogUtil.log(temX + " : " + temY + " - ");

        getMapView().setPivotX(getMapViewSize().getWidth() / 2);
        getMapView().setPivotY(getMapViewSize().getHeight() / 2);
        ViewHelper.setTranslationX(getMapView(), (int) (temX));
        ViewHelper.setTranslationY(getMapView(), (int) (temY));
        setScrollX(temX);
        setScrollY(temY);
    }
}
