package com.plorrios.lineapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class MySecondView extends View {

    Random random = new Random();
    Paint paint = new Paint();
    float prevX, prevY, newX, newY;
    int color = Color.BLACK;
    ArrayList<Float> lines;
    ArrayList<Integer> colors;

    public MySecondView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        lines = new ArrayList<>();
        colors = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(this.color);
        canvas.drawLine(this.prevX, this.prevY, this.newX, this.newY, this.paint);
        for (int i = 0; i<lines.size(); i = i+4) {
            paint.setColor(colors.get(i/4));
            canvas.drawLine(lines.get(0+i), lines.get(1+i), lines.get(2+i), lines.get(3+i), this.paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();
                this.color = Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255));
                break;
            case MotionEvent.ACTION_MOVE:
                newX = event.getX();
                newY = event.getY();
                this.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                lines.add(prevX);
                lines.add(prevY);
                lines.add(newX);
                lines.add(newY);
                colors.add(this.color);

                prevX = -1;
                prevY = -1;
                newX = -1;
                newY = -1;
                this.invalidate();
                break;
        }
        return true;
    }
}
