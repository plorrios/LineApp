package com.plorrios.lineapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Random;

public class MyFifthView extends View {

    Random random = new Random();
    Paint paint = new Paint();
    float clickX = -1, clickY;
    float lastFocusX, lastFocusY;
    int selectedSquare;
    int color = Color.BLACK;
    ArrayList<Float> clicks;
    ArrayList<Float> scales;
    ArrayList<Integer> colors;
    GestureDetector gestures;
    ScaleGestureDetector scaleGesture;
    boolean squareSelected = false;
    boolean isScaling = false;


    public MyFifthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.BLACK);
        paint.setStrokeJoin(Paint.Join.ROUND);
        clicks = new ArrayList<>();
        colors = new ArrayList<>();
        scales = new ArrayList<>();
        scaleGesture = new ScaleGestureDetector(getContext(),
                new ScaleListener());
        gestures = new GestureDetector(getContext(), new GestureListener());
    }

    public class GestureListener implements GestureDetector.OnGestureListener,
            GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            clickX = e.getX();
            clickY = e.getY();
            clicks.add(clickX);
            clicks.add(clickY);
            color = Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255));
            colors.add(color);
            scales.add(1.0f);
            Log.d("DOUBLE CLICK IN: ", String.valueOf(clickX) + "," + String.valueOf(clickY) );

            invalidate();

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent downEvent, MotionEvent currentEvent,
                                float distanceX, float distanceY) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }
    };

    public class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float focusX = detector.getFocusX();
            float focusY = detector.getFocusY();

            for (int i = 0; i < clicks.size(); i = i + 2) {

                int halfi = i/2;
                if (focusX > (clicks.get(i) - 150 * scales.get(halfi)) && focusX < (clicks.get(i) + 150 * scales.get(halfi))
                        && focusY > (clicks.get(i + 1) - 150 * scales.get(halfi)) && focusY < (clicks.get(i + 1) + 150 * scales.get(halfi))) {
                    selectedSquare = i;
                    squareSelected = true;
                    isScaling = true;
                }

            }

            if(squareSelected){
                int halfi = selectedSquare/2;
                Log.d("SCALE FACTOR",String.valueOf(detector.getScaleFactor()));
                Log.d("SCALE RESULT",String.valueOf(scales.get(halfi) * detector.getScaleFactor()));
                scales.set(halfi,scales.get(halfi) * detector.getScaleFactor());
                invalidate();

            }

            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            lastFocusX = detector.getFocusX();
            lastFocusY = detector.getFocusY();
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (clickX != -1) {
            for (int i = 0; i < clicks.size(); i = i + 2) {
                int halfi = i/2;
                paint.setColor(colors.get(halfi));
                canvas.drawRect((clicks.get(i) - 150 * scales.get(halfi)), (clicks.get(i + 1) - 150 * scales.get(halfi)),
                        (clicks.get(i) + 150 * scales.get(halfi)), (clicks.get(i + 1) + 150 * scales.get(halfi)), paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        scaleGesture.onTouchEvent(event);
        gestures.onTouchEvent(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                for (int i = 0; i < clicks.size(); i = i + 2) {

                    int halfi = i/2;
                    if (x > (clicks.get(i) - 150 * scales.get(halfi)) && x < (clicks.get(i) + 150 * scales.get(halfi))
                            && y > (clicks.get(i + 1) - 150 * scales.get(halfi)) && y < (clicks.get(i + 1) + 150 * scales.get(halfi))) {
                        selectedSquare = i;
                        squareSelected = true;
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
                event.getPointerCount();
                if (squareSelected && event.getPointerCount() == 1 && !isScaling) {
                    clicks.set(selectedSquare, event.getX());
                    clicks.set(selectedSquare + 1, event.getY());
                }

                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                squareSelected = false;
                isScaling = false;
                invalidate();
                break;
        }
        return true;
    }








}
