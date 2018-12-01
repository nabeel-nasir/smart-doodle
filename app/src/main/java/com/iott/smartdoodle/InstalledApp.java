package com.iott.smartdoodle;

import android.content.Intent;

public class InstalledApp {
    String mAppName;
    Intent mIntent;
    boolean mSystemApp;

    public InstalledApp(String appName, Intent intent, boolean systemApp) {
        mAppName = appName;
        mIntent = intent;
        mSystemApp = systemApp;
    }
}