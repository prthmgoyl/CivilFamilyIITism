package com.example.civilfamilyiitism.StudentSideOnly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.civilfamilyiitism.Ascefragment1;
import com.example.civilfamilyiitism.R;

public class AscepageStudentSide extends AppCompatActivity {

    ImageButton home , info , news , person;
    FrameLayout view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ascepage_student_side);

        home = (ImageButton) findViewById(R.id.imageButton3);
        info = (ImageButton) findViewById(R.id.imageButton5);
        news = (ImageButton) findViewById(R.id.imageButton6);
        person = (ImageButton) findViewById(R.id.imageButton4);
        view = (FrameLayout)findViewById(R.id.container);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.container,new Ascefragment1()).commit();
            }
        });

    }
}