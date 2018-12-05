package com.iott.smartdoodle;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;

public class MainActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleRecordButtonPress(View view) {
//        Intent recordActivityIntent = new Intent(this, RecordActivity.class);
        Intent recordActivityIntent = new Intent(this, RecordActivity.class);
        startActivity(recordActivityIntent);
    }

    public void handleInferButtonPress(View view) {
        Intent inferActivityIntent = new Intent(this, InferActivity.class);
        startActivity(inferActivityIntent);
    }
}
