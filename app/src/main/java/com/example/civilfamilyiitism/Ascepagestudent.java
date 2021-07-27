package com.example.civilfamilyiitism;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.civilfamilyiitism.ui.main.AscepagestudentFragment;

public class Ascepagestudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, AscepagestudentFragment.newInstance())
                .commitNow();
        }
    }
}