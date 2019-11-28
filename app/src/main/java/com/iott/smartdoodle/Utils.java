package com.iott.smartdoodle;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    /**
     * Return a 40x40 array from the 400x400 array
     * @param touchPoints set of x,y coordinates
     * @return scaled down touch points with image move to top left
     */
    public static float[][] getSimplifiedTouchPoints(List<int[]> touchPoints) {
        //reposition the drawing to the top-left corner to have min values of 0
        int xMin = 400, yMin = 400;
        for(int[] point : touchPoints) {
            if(point[0] < xMin)
                xMin = point[0];
            if(point[1] < yMin)
                yMin = point[1];
        }
        float[][] remappedPoints = new float[40][40];
        for(int[] point : touchPoints) {
            int x = point[0];
            int y = point[1];

            x = (x - xMin) / 10;
            y = (y - yMin) / 10;

            //flip (x,y) since the android screen follows 3rd quadrant
            remappedPoints[y][x] = 1;
        }
        return remappedPoints;
    }

    public static List<InstalledApp> getAllInstalledApps(Context context) {
        List<InstalledApp> installedApps = new ArrayList<>();

        final PackageManager pm = context.getPackageManager();
        //get a list of installed apps.
        List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_META_DATA);

        for (PackageInfo packageInfo : packages) {
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;

            if (applicationInfo != null) {
                final String applicationName = (String) (pm.getApplicationLabel(applicationInfo));
                Intent launchIntentForPackage = pm.getLaunchIntentForPackage(applicationInfo.packageName);
                Log.d("App", String.format("[%s] Name: %s, Package: %s, launchActivity: %s",
                        isSystemPackage(packageInfo) ? "SYS" : "USER",
                        applicationName,
                        applicationInfo.packageName,
                        launchIntentForPackage)
                );
                if(launchIntentForPackage != null) {
                    InstalledApp installedApp = new InstalledApp(
                            applicationName, applicationInfo.packageName,launchIntentForPackage, isSystemPackage(packageInfo)
                    );
                    installedApps.add(installedApp);
                }
            }
        }
        return installedApps;
    }

    private static boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }
}
