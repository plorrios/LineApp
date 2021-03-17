package com.plorrios.lineapp;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class MyListener extends
        GestureDetector.SimpleOnGestureListener {
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }
    @Override
    public boolean onDoubleTap(MotionEvent e) {

        return true;
    }




}