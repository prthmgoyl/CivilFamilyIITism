package com.example.civilfamilyiitism.ProfessorSideOnly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.civilfamilyiitism.Ascefragment1;
import com.example.civilfamilyiitism.R;
import com.example.civilfamilyiitism.StudentSideOnly.AsceFacebookFragment;
import com.example.civilfamilyiitism.StudentSideOnly.InstaPageFragment;
import com.example.civilfamilyiitism.StudentSideOnly.LinkedinFragment;
import com.example.civilfamilyiitism.studentinfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ascepage extends AppCompatActivity {

    ImageView home , info , news , person;
    FrameLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ascepage);

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