package com.iott.smartdoodle;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends WearableActivity {

    private TextView mTextView;
    private EditText mEditText;
    private Button mInferButton;
    public static String TAG = "TouchEvent";
    private Interpreter tflite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            tflite = new Interpreter(loadModelFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        mEditText = findViewById(R.id.edit_text);
        mInferButton = findViewById(R.id.button);
        mTextView = (TextView) findViewById(R.id.text);

        mInferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputNumber = mEditText.getText().toString();
                float prediction = doInference(inputNumber);
                mTextView.setText(Float.toString(prediction));

            }
        });



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

    private float doInference(String inputString) {
        float[] inputVal = new float[1];
        //input shape is 1
        inputVal[0] = Float.valueOf(inputString);

        //output shape is 1x1
        float[][] outputVal = new float[1][1];

        //run inference
        tflite.run(inputVal, outputVal);

        return outputVal[0][0];
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
