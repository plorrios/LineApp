package com.plorrios.lineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void click(View v){
        Intent intent;
        switch(v.getId()){
            case R.id.Ejercicio1Button:
                intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
                break;
            case R.id.Ejercicio2Button:
                intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                break;
            case R.id.Ejercicio3Button:
                intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent);
                break;
            case R.id.Ejercicio4Button:
                intent = new Intent(MainActivity.this, FourthActivity.class);
                startActivity(intent);
                break;
            case R.id.Ejercicio5Button:
                intent = new Intent(MainActivity.this, FifthActivity.class);
                startActivity(intent);
                break;
        }
    }
}
