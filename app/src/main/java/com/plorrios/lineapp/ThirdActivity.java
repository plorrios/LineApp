package com.plorrios.lineapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ThirdActivity extends AppCompatActivity {

    private MyThirdView mDrawLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        mDrawLayout = findViewById(R.id.third_view);

        mDrawLayout.setVisibility(View.VISIBLE);
        mDrawLayout.setDrawingCacheEnabled(true);
        mDrawLayout.setEnabled(true);
        mDrawLayout.invalidate();
    }

}
