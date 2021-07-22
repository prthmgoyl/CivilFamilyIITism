package com.example.civilfamilyiitism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class searchsplashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchsplashscreen);

        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                    startActivity(new Intent(searchsplashscreen.this,Searchview.class));
                    finish();
                } catch (InterruptedException e) {

                }
            }
        };
        thread.start();
    }
}