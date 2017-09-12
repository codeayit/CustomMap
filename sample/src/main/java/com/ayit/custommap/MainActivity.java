package com.ayit.custommap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ayit.cartoonmaplibrary.map.MapController;
import com.ayit.cartoonmaplibrary.map.entity.MapDrawableSelector;
import com.ayit.cartoonmaplibrary.map.impl.MapButtonTouchHandler;
import com.ayit.cartoonmaplibrary.map.view.MapButton;


public class MainActivity extends AppCompatActivity implements MapButtonTouchHandler {

    private MapController mapController;
    private View mapView;
    private MapButton mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.map_container);
        mapButton = (MapButton) findViewById(R.id.map_btn);
        mapController = new MapController(this);
        mapController.bindMapView(mapView, 2, 1, 1);

        mapButton.setDrawableSelector(new MapDrawableSelector(R.drawable.map_room_music_normal,R.drawable.map_room_music_press));
        mapButton.setOnMapButtonClickListener(new MapButton.OnMapButtonClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"点击了",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mapController.bindEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void handleMapButton(MapButton button) {
        mapController.bindMapButton(button);
    }
}
