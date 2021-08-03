package com.example.civilfamilyiitism.StudentSideOnly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.civilfamilyiitism.Ascefragment1;
import com.example.civilfamilyiitism.R;

public class AscepageStudentSide extends AppCompatActivity {

    ImageView home , info , news , person;
    FrameLayout view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ascepage_student_side);

        home = (ImageView) findViewById(R.id.imageButton3);
        info = (ImageView) findViewById(R.id.imageButton5);
        news = (ImageView) findViewById(R.id.imageButton6);
        person = (ImageView) findViewById(R.id.imageButton4);
        view = (FrameLayout)findViewById(R.id.container);

        getSupportFragmentManager().beginTransaction().add(R.id.container,new Ascefragment1()).commit();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.container,new Ascefragment1()).commit();
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.container,new AsceFacebookFragment()).commit();
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.container,new InstaPageFragment()).commit();

            }
        });
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.container,new LinkedinFragment()).commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}