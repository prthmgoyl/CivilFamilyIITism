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

    }
}