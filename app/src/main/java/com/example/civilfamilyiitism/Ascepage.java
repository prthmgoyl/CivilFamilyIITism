package com.example.civilfamilyiitism;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class Ascepage extends AppCompatActivity {

    private EditText edt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ascepage);

        edt1= findViewById(R.id.editTextTextMultiLine);
        edt1.setText("abcdefghijklmnopqrstuvwxyz 1234567890 ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890 abcdefghijklmnopqrstuvwxyz 1234567890 1234567890");
    }
}