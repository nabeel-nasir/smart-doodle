package com.iott.smartdoodle;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;

import static com.iott.smartdoodle.Utils.getSimplifiedTouchPoints;

public class InferActivity extends WearableActivity {

    public static String TAG = "TouchEvent";
    private Interpreter tflite;
    private DoodleDrawingView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        final List<InstalledApp> apps = Utils.getAllInstalledApps(this);

        try {
            tflite = new Interpreter(loadModelFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

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

                float[][] simplifiedTouchPoints = getSimplifiedTouchPoints(touchPoints);
                float[] confidenceVals = doInference(simplifiedTouchPoints);
                Log.d(TAG, Arrays.toString(confidenceVals));
                float max = confidenceVals[0];
                int maxIndex = 0;
                for(int i=1;i<confidenceVals.length;i++){
                    if(confidenceVals[i] > max) {
                        max = confidenceVals[i];
                        maxIndex = i;
                    }
                }

                String shape = "";
                switch (maxIndex) {
                    case 0:
                        shape = "Circle";
                        break;
                    case 1:
                        shape = "V";
                        break;
                    case 2:
                        shape = "~";
                        break;
                    case 3:
                        shape = "Triangle";
                        new LifxToggleAPI().execute();
                        break;
                    case 4:
                        shape = "Line";
                        break;
                }

                if(maxIndex == 0) {
                    final Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(apps.get(2).mIntent);
                        }
                    }, 1500);
                } else {
                    Intent intent = new Intent(InferActivity.this, ConfirmationActivity.class);
                    intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                            ConfirmationActivity.SUCCESS_ANIMATION);
                    intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                            shape);
                    startActivity(intent);
                }
            }
        });

        // Enables Always-on display
        setAmbientEnabled();
    }

    private float[] doInference(float[][] inputVal) {
        //output shape is 1x5
        float[][] outputConfidenceVals = new float[1][5];

        //run inference
        tflite.run(inputVal, outputConfidenceVals);

        return outputConfidenceVals[0];
    }

    /* Memory map the model file in Assets. */
    private MappedByteBuffer loadModelFile() throws IOException {
        //Open the model using an input stream, and memory map it to load
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("linear.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}
