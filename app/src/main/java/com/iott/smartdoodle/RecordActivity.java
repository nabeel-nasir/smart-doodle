package com.iott.smartdoodle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.activity.WearableActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecordActivity extends WearableActivity {

    public static String TAG = "TouchEvent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width = displayMetrics.widthPixels;
        Log.d(TAG, String.format("h = %d, w = %d", height, width));

        final List<int[]> touchPoints = new ArrayList<>();

        View v = findViewById(R.id.box_inset_layout);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v,
                                   MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                int eventaction = event.getAction();

                switch (eventaction) {
                    case MotionEvent.ACTION_DOWN:
//                        Log.d(TAG, "ACTION_DOWN AT COORDS "+"X: "+x+" Y: "+y);
                        touchPoints.clear();
                        touchPoints.add(new int[]{x,y});
                        break;

                    case MotionEvent.ACTION_MOVE:
//                        Log.d(TAG, "ACTION_MOVE AT COORDS "+"X: "+x+" Y: "+y);
                        touchPoints.add(new int[]{x,y});
                        break;

                    case MotionEvent.ACTION_UP:
//                        Log.d(TAG, "ACTION_UP AT COORDS "+"X: "+x+" Y: "+y);
                        touchPoints.add(new int[]{x,y});

                        String out = "[";
                        for(int[] point : touchPoints) {
                            out += "[" + point[0] + "," + point[1] + "],";
                        }
                        out = out.substring(0, out.length() - 1);
                        out += "]";
                        Log.d(TAG, out);
//                        int[][] simplifiedTouchPoints = getSimplifiedTouchPoints(touchPoints);
//                        String arrString = "simplified touch points =\n";
//                        for(int i=0;i<40;i++) {
//                            String row = "";
//                            for(int j=0;j<40;j++) {
//                                row = row + simplifiedTouchPoints[i][j] + " ";
//                            }
//                            arrString = arrString + row + "\n";
//                        }
//                        Log.d(TAG, arrString);
                        Intent intent = new Intent(RecordActivity.this, ConfirmationActivity.class);
                        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                                ConfirmationActivity.SUCCESS_ANIMATION);
                        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                                getString(R.string.doodle_recorded));
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        // Enables Always-on
        setAmbientEnabled();
    }

    //Return a 40x40 array from the 400x400 array
    private int[][] getSimplifiedTouchPoints(List<int[]> touchPoints) {
        //reposition the drawing to the top-left corner to have min values of 0
        int xMin = 400, yMin = 400;
        for(int[] point : touchPoints) {
            if(point[0] < xMin)
                xMin = point[0];
            if(point[1] < yMin)
                yMin = point[1];
        }
        int[][] remappedPoints = new int[40][40];
        for(int[] point : touchPoints) {
            int x = point[0];
            int y = point[1];

            //TODO change later
            x = (x - xMin) / 10;
            y = (y - yMin) / 10;

            //flip (x,y) since the android screen follows 3rd quadrant
            remappedPoints[y][x] = 1;
        }
        return remappedPoints;
    }
}
