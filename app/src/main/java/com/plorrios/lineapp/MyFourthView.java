package com.plorrios.lineapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class MyFourthView extends View {

    Paint paint = new Paint();
    float firstX = -1 , firstY, secondX, secondY, thirdX, thirdY;

    public MyFourthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLUE);
        if (firstX != -1) {
            canvas.drawLine(firstX, firstY, secondX, secondY, this.paint);
            canvas.drawLine(secondX, secondY, thirdX, thirdY, this.paint);
            canvas.drawLine(thirdX, thirdY, firstX, firstY, this.paint);
            canvas.drawCircle(firstX, firstY, 60f, this.paint);
            canvas.drawCircle(secondX, secondY, 60f, this.paint);
            canvas.drawCircle(thirdX, thirdY, 60f, this.paint);

            paint.setColor(Color.GREEN);
            float halfpointX = (secondX + thirdX) / 2;
            float halfpointY = (secondY + thirdY) / 2;
            canvas.drawLine( halfpointX, halfpointY, firstX, firstY, this.paint);

            canvas.save();
            canvas.rotate(180, firstX, firstY);
            canvas.drawLine(firstX, firstY, halfpointX, halfpointY, paint);
            canvas.restore();


            paint.setColor(Color.GRAY);
            canvas.drawLine( halfpointX, halfpointY, halfpointX +canvas.getWidth()/3, halfpointY, this.paint);
            canvas.drawLine( halfpointX, halfpointY, halfpointX, halfpointY + canvas.getWidth()/3, this.paint);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() == 3) {
            int firstFinger = event.findPointerIndex(0);
            int secondFinger = event.findPointerIndex(1);
            int thirdFinger = event.findPointerIndex(2);

            firstX = event.getX(firstFinger);
            firstY = event.getY(firstFinger);
            //Log.d("FIRST FINGER",String.valueOf(firstX) + "," + String.valueOf(firstY));
            secondX = event.getX(secondFinger);
            secondY = event.getY(secondFinger);
            //Log.d("SECOND FINGER",String.valueOf(secondX) + "," + String.valueOf(secondY));
            thirdX = event.getX(thirdFinger);
            thirdY = event.getY(thirdFinger);
            //Log.d("THIRD FINGER",String.valueOf(thirdX) + "," + String.valueOf(thirdY));

            //Calculo de distancias
            double distance12 = Math.sqrt((secondY - firstY) * (secondY - firstY) + (secondX - firstX) * (secondX - firstX));
            double distance23 = Math.sqrt((thirdY - secondY) * (thirdY - secondY) + (thirdX - secondX) * (thirdX - secondX));
            double distance31 = Math.sqrt((firstY - thirdY) * (firstY - thirdY) + (firstX - thirdX) * (firstX - thirdX));

            if (distance12 < distance23 && distance12 < distance31){
                firstFinger = event.findPointerIndex(2);
                secondFinger = event.findPointerIndex(0);
                thirdFinger = event.findPointerIndex(1);

            } else if (distance23 < distance12 && distance23 < distance31){

            } else if (distance31 < distance23 && distance31 < distance12){
                firstFinger = event.findPointerIndex(1);
                secondFinger = event.findPointerIndex(0);
                thirdFinger = event.findPointerIndex(2);

            }

            firstX = event.getX(firstFinger);
            firstY = event.getY(firstFinger);
            //Log.d("FIRST FINGER",String.valueOf(firstX) + "," + String.valueOf(firstY));
            secondX = event.getX(secondFinger);
            secondY = event.getY(secondFinger);
            //Log.d("SECOND FINGER",String.valueOf(secondX) + "," + String.valueOf(secondY));
            thirdX = event.getX(thirdFinger);
            thirdY = event.getY(thirdFinger);
            //Log.d("THIRD FINGER",String.valueOf(thirdX) + "," + String.valueOf(thirdY));

        }

        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP){

            firstX = -1;

        }

        invalidate();
        return true;
    }


}
