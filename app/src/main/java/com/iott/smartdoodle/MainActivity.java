package com.iott.smartdoodle;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends WearableActivity {

    public static String TAG = "TouchEvent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View v = findViewById(R.id.box_inset_layout);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v,
                                   MotionEvent event) {
                int X = (int) event.getX();
                int Y = (int) event.getY();
                int eventaction = event.getAction();

                switch (eventaction) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "ACTION_DOWN AT COORDS "+"X: "+X+" Y: "+Y);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG, "ACTION_MOVE AT COORDS "+"X: "+X+" Y: "+Y);
                        break;

                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "ACTION_UP AT COORDS "+"X: "+X+" Y: "+Y);
                        break;
                }
                return true;
            }
        });
        // Enables Always-on
        setAmbientEnabled();



    }
}
