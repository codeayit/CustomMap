package com.ayit.cartoonmaplibrary.map;//package com.haitiand.moassioncartonrobot.map;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.MotionEvent;
//import android.view.WindowManager;
//
//import com.lny.demo.mapdemo.R;
//import com.lny.demo.mapdemo.map.impl.MapButtonTouchHandler;
//
///**
// * Created by lny on 2017/3/8.
// */
//
//public class Map extends AppCompatActivity implements MapButtonTouchHandler {
//
//    private MapController mapController;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_map);
//
//        mapController = new MapController(this);
//        mapController.bindMapView(findViewById(R.id.map),2,1,2);
//
//    }
//
//    @Override
//    public final boolean onTouchEvent(MotionEvent event) {
//        return mapController.bindEvent(event,false);
//    }
//
//    @Override
//    public boolean handlerEvent(MotionEvent event) {
//        return mapController.bindEvent(event,true);
//    }
//}
