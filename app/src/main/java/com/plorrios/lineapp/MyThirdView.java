package com.plorrios.lineapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import java.util.ArrayList;

import java.util.Random;

public class MyThirdView extends View {

    Random random = new Random();
    Paint paint = new Paint();
    int color = Color.BLACK;
    ArrayList<Bitmap> lines;
    ArrayList<Path> paths;
    ArrayList<Integer> colors;
    private int mActivePointerId;
    int h,w;

    //drawing path
    static Path drawPath;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;

    public MyThirdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        drawPath = new Path();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        lines = new ArrayList<>();
        paths = new ArrayList<>();
        colors = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
        this.h = h;
        this.w = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < lines.size(); i++) {
            paint.setColor(colors.get(i));
            canvas.drawBitmap(lines.get(i), 0, 0, paint);
            canvas.drawPath(paths.get(i), paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        for (int i = 0; i < event.getPointerCount(); i++) {
            mActivePointerId = event.getPointerId(i);
            int pointerIndex = event.findPointerIndex(mActivePointerId);
            Log.d("POINTER", String.valueOf(pointerIndex));
            if (pointerIndex < lines.size()) {
                canvasBitmap = lines.get(pointerIndex);
            } else {
                canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            }

            if (pointerIndex < paths.size()) {
                drawPath = paths.get(pointerIndex);
            } else {
                drawPath = new Path();
            }
            float touchX = event.getX(pointerIndex);
            float touchY = event.getY(pointerIndex);

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    Log.d("ACTION", "DOWN");
                    drawPath.moveTo(touchX, touchY);
                    this.color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                    colors.add(pointerIndex, this.color);
                    paths.add(pointerIndex, drawPath);
                    lines.add(pointerIndex, canvasBitmap);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    Log.d("ACTION", "POINTER_DOWN");
                    drawPath.moveTo(touchX, touchY);
                    this.color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                    if (i < paths.size()) {
                        paths.set(pointerIndex, drawPath);
                        lines.set(pointerIndex, canvasBitmap);
                    } else {
                        colors.add(pointerIndex, this.color);
                        paths.add(pointerIndex, drawPath);
                        lines.add(pointerIndex, canvasBitmap);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d("ACTION", "MOVE");
                    drawCanvas.drawPath(drawPath, paint);
                    drawPath.lineTo(touchX, touchY);
                    paths.set(pointerIndex, drawPath);
                    lines.set(pointerIndex, canvasBitmap);
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d("ACTION", "UP");
                    drawPath.lineTo(touchX, touchY);
                    drawCanvas.drawPath(drawPath, paint);
                    paths.remove(pointerIndex);
                    lines.remove(pointerIndex);
                    colors.remove(pointerIndex);
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    Log.d("ACTION", "POINTER_UP");
                    drawPath.lineTo(touchX, touchY);
                    drawCanvas.drawPath(drawPath, paint);
                    if (event.getActionIndex() == pointerIndex) {
                        paths.remove(pointerIndex);
                        lines.remove(pointerIndex);
                        colors.remove(pointerIndex);
                    }
                    break;
                default:
                    return false;
            }
        }
        invalidate();
        return true;
    }

}
