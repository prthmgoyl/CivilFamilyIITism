package com.example.civilfamilyiitism;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Professormainpage extends AppCompatActivity {

    private ImageView img1,img2,img3,img4;
    String check = "null";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professormainpage);

        img1 = findViewById(R.id.imageView13);
        img2 = findViewById(R.id.imageView12);
        img3 = findViewById(R.id.imageView15);
        img4 = findViewById(R.id.imageView14);

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

    }
}