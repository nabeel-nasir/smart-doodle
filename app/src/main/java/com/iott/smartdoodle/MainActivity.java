package com.iott.smartdoodle;

import android.os.Bundle;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends WearableActivity {

    public static String TAG = "TouchEvent";
    private WearableRecyclerView mWearableRecyclerView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> apps = new ArrayList<>();
        apps.add("1");
        apps.add("2");
        apps.add("3");
        apps.add("4");
        apps.add("5");
        apps.add("6");
        apps.add("7");
        apps.add("8");
        apps.add("9");
        apps.add("10");
        apps.add("11");
        apps.add("12");
        apps.add("13");
        apps.add("14");
        apps.add("15");

        mWearableRecyclerView = findViewById(R.id.recycler_launcher_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mWearableRecyclerView.setHasFixedSize(true);

        // To align the edge children (first and last) with the center of the screen
        mWearableRecyclerView.setEdgeItemsCenteringEnabled(true);

//        mWearableRecyclerView.setCircularScrollingGestureEnabled(true);

        mWearableRecyclerView.setLayoutManager(
                new WearableLinearLayoutManager(this));

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(apps);
        mWearableRecyclerView.setAdapter(mAdapter);

//        View v = findViewById(R.id.box_inset_layout);
//        v.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v,
//                                   MotionEvent event) {
//                int X = (int) event.getX();
//                int Y = (int) event.getY();
//                int eventaction = event.getAction();
//
//                switch (eventaction) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.d(TAG, "ACTION_DOWN AT COORDS "+"X: "+X+" Y: "+Y);
//                        break;
//
//                    case MotionEvent.ACTION_MOVE:
//                        Log.d(TAG, "ACTION_MOVE AT COORDS "+"X: "+X+" Y: "+Y);
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        Log.d(TAG, "ACTION_UP AT COORDS "+"X: "+X+" Y: "+Y);
//                        break;
//                }
//                return true;
//            }
//        });
//        // Enables Always-on
//        setAmbientEnabled();



    }
}
