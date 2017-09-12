package com.ayit.cartoonmaplibrary.map.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.ayit.cartoonmaplibrary.map.entity.MapDrawableSelector;
import com.ayit.cartoonmaplibrary.map.impl.MapButtonTouchHandler;


/**
 * Created by lny on 2017/3/5.
 */

public class MapButton extends ImageView {
    public MapButton(Context context) {
        super(context);
    }

    public MapButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MapButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private MapDrawableSelector drawableSelector;

    public void setDrawableSelector(MapDrawableSelector drawableSelector) {
        if (drawableSelector!=null)
            this.setImageResource(drawableSelector.getNormalResId());
        this.drawableSelector = drawableSelector;
    }

    public MapDrawableSelector getDrawableSelector() {
        return drawableSelector;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (onMapButtonClickListener!=null && !isAlpha(event))
            ((MapButtonTouchHandler) getContext()).handleMapButton(this);

        return  false;
    }
    private boolean isAlpha(MotionEvent event) {
        Bitmap bitmap = ((BitmapDrawable) this.getDrawable()).getBitmap();
        if (bitmap == null) {
        } else {
            if (bitmap.getWidth() > event.getX() && bitmap.getHeight() > event.getY() && event.getY() >= 0 && event.getX() >= 0) {
//                LogUtil.log("alpha : "+bitmap.getPixel((int) (event.getX()), ((int) event.getY())) +" :" +event.getX()+" - "+event.getY() +" : "+bitmap.getWidth()+" - "+bitmap.getHeight());
//                Bitmap btm = BitmapFactory.decodeResource(getResources(), R.drawable.map_room_dongman_normal);
//                LogUtil.log("alpha_1 : "+btm.getPixel((int) (event.getX()), ((int) event.getY())) +" :" +event.getX()+" - "+event.getY());
                float scaleX = (float) bitmap.getWidth()/getWidth();
                float scaleY = (float) bitmap.getHeight()/getHeight();
//                LogUtil.log("alpha : "+getWidth()+" / "+bitmap.getWidth()+" = "+scaleX);
//                LogUtil.log("alpha : "+event.getX()*scaleX+" - "+event.getY()*scaleY  +" ; "+event.getX()+" - "+event.getY());
                if (bitmap.getPixel((int) (event.getX()*scaleX), ((int) (event.getY()*scaleY))) == 0) {
                    return true;//透明区域返回true
                }
            }
        }
        return false;
    }


    private OnMapButtonClickListener onMapButtonClickListener;

    public OnMapButtonClickListener getOnMapButtonClickListener() {
        return onMapButtonClickListener;
    }

    public void setOnMapButtonClickListener(OnMapButtonClickListener onMapButtonClickListener) {
        this.onMapButtonClickListener = onMapButtonClickListener;
    }

    public interface OnMapButtonClickListener {
        void onClick(View view);
    }


}
