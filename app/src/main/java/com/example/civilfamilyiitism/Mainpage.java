package com.example.civilfamilyiitism;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Mainpage extends AppCompatActivity {

    private ImageView img1,img2,img3,img4,img5,img6,img7,img8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        img1=findViewById(R.id.imageView1);
        img2=findViewById(R.id.imageView2);
        img3=findViewById(R.id.imageView3);
        img4=findViewById(R.id.imageView4);
        img5=findViewById(R.id.imageView5);
        img6=findViewById(R.id.imageView6);
        img7=findViewById(R.id.imageView7);
        img8=findViewById(R.id.imageView8);


                img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Mainpage.this, "img1", Toast.LENGTH_SHORT).show();
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Mainpage.this, "img2", Toast.LENGTH_SHORT).show();

            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Mainpage.this, "img3", Toast.LENGTH_SHORT).show();

            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Mainpage.this, "img4", Toast.LENGTH_SHORT).show();

            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Mainpage.this, "img5", Toast.LENGTH_SHORT).show();

            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Mainpage.this, "img6", Toast.LENGTH_SHORT).show();

            }
        });
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Mainpage.this, "img7", Toast.LENGTH_SHORT).show();

            }
        });
        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Mainpage.this, "img8", Toast.LENGTH_SHORT).show();

            }
        });
    }
}