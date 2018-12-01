package com.iott.smartdoodle;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.WearableActivity;

import java.util.ArrayList;
import java.util.List;

public class AppPickerActivity extends WearableActivity {

    private WearableRecyclerView mWearableRecyclerView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_picker);

        List<InstalledApp> apps = new ArrayList<>();
        apps = getAllInstalledApps();

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
    }

    public List<InstalledApp> getAllInstalledApps() {
        List<InstalledApp> installedApps = new ArrayList<>();

        final PackageManager pm = getPackageManager();
        //get a list of installed apps.
        List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_META_DATA);

        for (PackageInfo packageInfo : packages) {
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;

            if (applicationInfo != null) {
                final String applicationName = (String) (pm.getApplicationLabel(applicationInfo));
                Intent launchIntentForPackage = pm.getLaunchIntentForPackage(applicationInfo.packageName);
//                Log.d(TAG, String.format("[%s] Name: %s, Package: %s, launchActivity: %s",
//                        isSystemPackage(packageInfo) ? "SYS" : "USER",
//                        applicationName,
//                        applicationInfo.packageName,
//                        launchIntentForPackage)
//                );
                InstalledApp installedApp = new InstalledApp(applicationName, launchIntentForPackage, isSystemPackage(packageInfo));
                installedApps.add(installedApp);
            }
        }
        return installedApps;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }
}
