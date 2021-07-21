package com.example.civilfamilyiitism;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Searchview extends AppCompatActivity {

    SearchView srchview;
    RecyclerView rcv;
    RecyclerViewAdapterone adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchview);

        rcv = (RecyclerView)findViewById(R.id.rcv);
        srchview = (SearchView)findViewById(R.id.searchView);

        processsearch("");

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
        GridLayoutManager manager = new GridLayoutManager(Searchview.this,2,RecyclerView.VERTICAL,false);

        rcv.setLayoutManager(manager);


        FirebaseRecyclerOptions<studentinfo> options =
                new FirebaseRecyclerOptions.Builder<studentinfo>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("UserInfoWithUid")
                                .orderByChild("username").startAt(query).endAt(query+"\uf8ff"), studentinfo.class)
                        .build();

        adapter=new RecyclerViewAdapterone(options);
        rcv.setAdapter(adapter);
        adapter.startListening();
    }

}