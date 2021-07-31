package com.example.civilfamilyiitism;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.civilfamilyiitism.Adapters.RecyclerViewAdapterone;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Searchview extends AppCompatActivity {

    SearchView srchview;
    RecyclerView rcv;
    RecyclerViewAdapterone adapter;
    RadioButton all, existing, passout;
    Spinner spin ;
    String check = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchview);

        rcv = (RecyclerView) findViewById(R.id.rcv);
        srchview = (SearchView) findViewById(R.id.searchView);
        all = findViewById(R.id.radioButton16);
        existing = findViewById(R.id.radioButton17);
        passout = findViewById(R.id.radioButton18);
        spin = (Spinner)findViewById(R.id.spinner2);

        processsearch("",check);

        all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String search = srchview.getQuery().toString();
                processsearch(search , check);
            }
        });
        existing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String search = srchview.getQuery().toString();
                processsearch(search , check);
            }
        });
        passout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String search = srchview.getQuery().toString();
                processsearch(search , check);
            }
        });

        srchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processsearch(query , check);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processsearch(newText , check);
                return false;
            }
        });

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String temp = (String) spin.getItemAtPosition(position);
                if(temp.equals("By name")){
                    check = "username";
                }
                else if(temp.equals("By Admission no.")){
                    check = "designation";
                }
                else if(temp.equals("By Email")){
                    check = "email";
                }
                else if(temp.equals("By Phone")){
                    check = "phone";
                }
                processsearch(srchview.getQuery().toString(),check);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void processsearch(String query , String field) {
        GridLayoutManager manager = new GridLayoutManager(Searchview.this, 2, RecyclerView.VERTICAL, false);

        rcv.setLayoutManager(manager);


        if (existing.isChecked()) {
            //  Toast.makeText(this, "Searching from existing users..", Toast.LENGTH_SHORT).show();
            FirebaseRecyclerOptions<studentinfo> options =
                    new FirebaseRecyclerOptions.Builder<studentinfo>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("UserInfoWithUid")
                                    .orderByChild(field).startAt(query).endAt(query + "\uf8ff"), studentinfo.class)
                            .build();

            adapter = new RecyclerViewAdapterone(options);
            rcv.setAdapter(adapter);
            adapter.startListening();
        } else if (passout.isChecked()) {
            // Toast.makeText(this, "Searching from Passouts...", Toast.LENGTH_SHORT).show();
            FirebaseRecyclerOptions<studentinfo> options =
                    new FirebaseRecyclerOptions.Builder<studentinfo>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("passout")
                                    .orderByChild(field).startAt(query).endAt(query + "\uf8ff"), studentinfo.class)
                            .build();
            adapter = new RecyclerViewAdapterone(options);
            rcv.setAdapter(adapter);
            adapter.startListening();

        } else {
            // Toast.makeText(this, "Searching from all", Toast.LENGTH_SHORT).show();
            FirebaseRecyclerOptions<studentinfo> options =
                    new FirebaseRecyclerOptions.Builder<studentinfo>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("UserInfo")
                                    .orderByChild(field).startAt(query).endAt(query + "\uf8ff"), studentinfo.class)
                            .build();

            adapter = new RecyclerViewAdapterone(options);
            rcv.setAdapter(adapter);
            adapter.startListening();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
       // adapter.stopListening();

        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}