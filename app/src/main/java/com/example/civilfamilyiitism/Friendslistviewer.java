package com.example.civilfamilyiitism;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Friendslistviewer extends AppCompatActivity {

    RecyclerView rcv;
    RecyclerViewAdapterone adapter;
    String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendslistviewer);

try {

    GridLayoutManager manager = new GridLayoutManager(Friendslistviewer.this, 2, RecyclerView.VERTICAL, false);
    rcv = (RecyclerView) findViewById(R.id.recyclerViewstudent);
    rcv.setLayoutManager(manager);

    check = getIntent().getStringExtra("keycheck");

    FirebaseRecyclerOptions<studentinfo> options =
            new FirebaseRecyclerOptions.Builder<studentinfo>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child(check).orderByChild("username"), studentinfo.class)
                    .build();

    adapter = new RecyclerViewAdapterone(options);
    rcv.setAdapter(adapter);
    adapter.startListening();
}
catch (Exception e){
    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
}
    }

}