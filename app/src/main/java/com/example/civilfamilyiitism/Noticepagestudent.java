package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Noticepagestudent extends AppCompatActivity {

   // String myuid;
    String check = "null";
    RecyclerView rcv;
    noticeadapterstudentside adapter;
    Button btn;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticepagestudent);

     //   myuid = FirebaseAuth.getInstance().getUid();
        rcv = (RecyclerView)findViewById(R.id.recyclerviewnoticestudent);
        btn = (Button) findViewById(R.id.button11);
        btn.setText("         ");
        reference = FirebaseDatabase.getInstance().getReference();

        check = getIntent().getStringExtra("check");
   //     Toast.makeText(this, "this "+check, Toast.LENGTH_SHORT).show();


        rcv.setLayoutManager(new LinearLayoutManager(Noticepagestudent.this));
        FirebaseRecyclerOptions<noticemodel> options =
                new FirebaseRecyclerOptions.Builder<noticemodel>()
                        .setQuery(reference.child("Notice").child(check), noticemodel.class)
                        .build();

        adapter=new noticeadapterstudentside(options);
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