package com.example.civilfamilyiitism;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Searchview extends AppCompatActivity {

    SearchView srchview;
    RecyclerView rcv;
    RecyclerViewAdapterone adapter;
    RadioButton all, existing, passout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchview);

        rcv = (RecyclerView) findViewById(R.id.rcv);
        srchview = (SearchView) findViewById(R.id.searchView);
        all = findViewById(R.id.radioButton16);
        existing = findViewById(R.id.radioButton17);
        passout = findViewById(R.id.radioButton18);

        processsearch("");

        all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String search = srchview.getQuery().toString();
                processsearch(search);
            }
        });
        existing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String search = srchview.getQuery().toString();
                processsearch(search);
            }
        });
        passout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String search = srchview.getQuery().toString();
                processsearch(search);
            }
        });

        srchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processsearch(newText);
                return false;
            }
        });


    }

    private void processsearch(String query) {
        GridLayoutManager manager = new GridLayoutManager(Searchview.this, 2, RecyclerView.VERTICAL, false);

        rcv.setLayoutManager(manager);


        if (existing.isChecked()) {
            //  Toast.makeText(this, "Searching from existing users..", Toast.LENGTH_SHORT).show();
            FirebaseRecyclerOptions<studentinfo> options =
                    new FirebaseRecyclerOptions.Builder<studentinfo>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("UserInfoWithUid")
                                    .orderByChild("username").startAt(query).endAt(query + "\uf8ff"), studentinfo.class)
                            .build();

            adapter = new RecyclerViewAdapterone(options);
            rcv.setAdapter(adapter);
            adapter.startListening();
        } else if (passout.isChecked()) {
            // Toast.makeText(this, "Searching from Passouts...", Toast.LENGTH_SHORT).show();
            FirebaseRecyclerOptions<studentinfo> options =
                    new FirebaseRecyclerOptions.Builder<studentinfo>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("passout")
                                    .orderByChild("username").startAt(query).endAt(query + "\uf8ff"), studentinfo.class)
                            .build();
            adapter = new RecyclerViewAdapterone(options);
            rcv.setAdapter(adapter);
            adapter.startListening();

        } else {
            // Toast.makeText(this, "Searching from all", Toast.LENGTH_SHORT).show();
            FirebaseRecyclerOptions<studentinfo> options =
                    new FirebaseRecyclerOptions.Builder<studentinfo>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("UserInfo")
                                    .orderByChild("username").startAt(query).endAt(query + "\uf8ff"), studentinfo.class)
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