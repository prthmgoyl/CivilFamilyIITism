package com.example.civilfamilyiitism;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Studentlistviewer extends AppCompatActivity {

    RecyclerView rcv;
    RecyclerViewAdapter adapter;
    String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlistviewer);

        rcv = (RecyclerView)findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        rcv.setLayoutManager(manager);

        check = getIntent().getStringExtra("keycheck");

        FirebaseRecyclerOptions<studentinfo> options =
                new FirebaseRecyclerOptions.Builder<studentinfo>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(check).orderByChild("username"), studentinfo.class)
                        .build();

        adapter=new RecyclerViewAdapter(options);
        rcv.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}