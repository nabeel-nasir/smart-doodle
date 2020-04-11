package com.iott.smartdoodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DoodleDrawingView extends View {
    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFFC0C0C0;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;

    private List<int[]> mTouchPoints;
    private DrawEventHandler mDrawEventHandler;

    public DoodleDrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
        mTouchPoints = new ArrayList<>();
    }

    public void initializeDrawHandler(DrawEventHandler drawEventHandler) {
        mDrawEventHandler = drawEventHandler;
    }

    private void setupDrawing(){
    //get drawing area setup for interaction
        drawPath = new Path();
        drawPaint = new Paint();

        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    //view given size
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    //draw view
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    //detect user touch
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchPoints.add(new int[]{touchX,touchY});
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                mTouchPoints.add(new int[]{touchX,touchY});
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                mTouchPoints.add(new int[]{touchX,touchY});
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                mDrawEventHandler.onDoodleDrawComplete(mTouchPoints);
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void clearDrawing() {
        mTouchPoints.clear();
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }
}
