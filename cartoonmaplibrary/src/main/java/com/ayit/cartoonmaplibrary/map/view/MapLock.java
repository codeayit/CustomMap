package com.ayit.cartoonmaplibrary.map.view;//package com.ayit.custommap.map.view;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.RelativeLayout;
//
//import com.ayit.custommap.R;
//
//
///**
// * Created by lny on 2017/5/25.
// */
//
//public class MapLock extends RelativeLayout {
//
//    private View lock;
//
//    public MapLock(Context context) {
//        super(context);
//        inidata();
//    }
//    public MapLock(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        inidata();
//    }
//
//    public MapLock(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        inidata();
//    }
//    private void inidata() {
//        setClickable(false);
//        setFocusable(false);
//        View content = LayoutInflater.from(getContext()).inflate(R.layout.layout_map_lock_content,null);
//        addView(content);
//        lock = content.findViewById(R.id.iv_layout_map_lock_lock);
//    }
//
//    public void show(){
//        setVisibility(VISIBLE);
//    }
//
//    public void hide(){
//        setVisibility(GONE);
//    }
//
//    public void shake(){
//
//    }
//
//}
