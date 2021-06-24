package com.example.civilfamilyiitism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Professormainpage extends AppCompatActivity {

    private ImageView img1,img2,img3,img4,img5,img6,img7,img8;
    String check = "null";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professormainpage);

        img1 = findViewById(R.id.imageView13);
        img2 = findViewById(R.id.imageView12);
        img3 = findViewById(R.id.imageView15);
        img4 = findViewById(R.id.imageView14);
        img5 = findViewById(R.id.imageView9);
        img6 = findViewById(R.id.imageView);
        img7 = findViewById(R.id.imageView11);
        img8 = findViewById(R.id.imageView10);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "fourth";
                Toast.makeText(Professormainpage.this, "img1"+check, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Professormainpage.this,Studentlistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);
       //         startActivity(new Intent(Professormainpage.this,Studentlistviewer.class));
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "third";
                Toast.makeText(Professormainpage.this, "img2"+check, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Professormainpage.this,Studentlistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "second";
                Toast.makeText(Professormainpage.this, "img3"+check, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Professormainpage.this,Studentlistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "first";
                Toast.makeText(Professormainpage.this, "img4"+check, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Professormainpage.this,Studentlistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivity(new Intent(Professormainpage.this,UploadPdfpage.class));
            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Professormainpage.this, "ASCE PAGE!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Professormainpage.this,Ascepage.class));
            }
        });
        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(Professormainpage.this,Studentaddpage.class));
            }
        });

    }
}