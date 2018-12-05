package com.iott.smartdoodle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;

import java.util.List;

public class RecordActivity extends WearableActivity {

    public static String TAG = "TouchEvent";
    private DoodleDrawingView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        drawView = findViewById(R.id.drawing);
        drawView.initializeDrawHandler(new DrawEventHandler() {
            @Override
            public void onDoodleDrawComplete(List<int[]> touchPoints) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawView.clearDrawing();
                    }
                }, 100);
//                for (int i = 0; i < touchPoints.size(); i++) {
//                    int[] point = touchPoints.get(i);
//                    Log.d(TAG, point[0] + "," + point[1]);
//                }
                String arrString = "{\"points\":[";
                for (int i = 0; i < touchPoints.size(); i++) {
                    int[] point = touchPoints.get(i);
                    arrString = arrString + "["+ point[0] + "," + point[1] + "],";
                }
                arrString = arrString.substring(0, arrString.length()-1);
                arrString += "]}";
                Log.d(TAG, arrString);

                Intent intent = new Intent(RecordActivity.this, ConfirmationActivity.class);
                intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                        ConfirmationActivity.SUCCESS_ANIMATION);
                intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                        getString(R.string.doodle_recorded));
                startActivity(intent);
            }
        });

        // Enables Always-on display
        setAmbientEnabled();
    }
}

interface DrawEventHandler {
    void onDoodleDrawComplete(List<int[]> touchPoints);
}
