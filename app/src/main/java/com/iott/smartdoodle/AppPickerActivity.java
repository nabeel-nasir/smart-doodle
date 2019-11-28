package com.iott.smartdoodle;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AppPickerActivity extends WearableActivity {

    private WearableRecyclerView mWearableRecyclerView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_picker);

        final List<InstalledApp> apps = Utils.getAllInstalledApps(this);

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
        mAdapter = new MyAdapter(this, apps, new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View v,
                                                int position) {
                Log.d("Click", position + "");
                Log.d("Click", apps.get(position).mAppName);
                Intent recordActivity = new Intent(AppPickerActivity.this, RecordActivity.class);
                recordActivity.putExtra("packageName", apps.get(position).mPackageName);
                startActivity(recordActivity);
                finish();
            }
        });
        mWearableRecyclerView.setAdapter(mAdapter);
    }
}
