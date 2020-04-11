package com.iott.smartdoodle;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enables Always-on display
        setAmbientEnabled();
    }

    public void handleRecordButtonPress(View view) {
//        Intent recordActivityIntent = new Intent(this, RecordActivity.class);
        Intent recordActivityIntent = new Intent(this, AppPickerActivity.class);
        startActivity(recordActivityIntent);


//        try
//        {
//            ImageView imageView = findViewById(R.id.icon);
//            Drawable icon = getPackageManager().getApplicationIcon("com.shazam.android");
//            imageView.setImageDrawable(icon);
//        }
//        catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }


    }

    public void handleInferButtonPress(View view) {
        Intent inferActivityIntent = new Intent(this, InferActivity.class);
        startActivity(inferActivityIntent);
    }
}
