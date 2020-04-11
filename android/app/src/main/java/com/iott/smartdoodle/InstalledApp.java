package com.iott.smartdoodle;

import android.content.Intent;

public class InstalledApp {
    String mAppName;
    String mPackageName;
    Intent mIntent;
    boolean mSystemApp;

    public InstalledApp(String appName, String packageName, Intent intent, boolean systemApp) {
        mAppName = appName;
        mPackageName = packageName;
        mIntent = intent;
        mSystemApp = systemApp;
    }
}