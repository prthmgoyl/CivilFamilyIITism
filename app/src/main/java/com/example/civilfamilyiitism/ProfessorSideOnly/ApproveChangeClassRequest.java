package com.example.civilfamilyiitism.ProfessorSideOnly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.civilfamilyiitism.Adapters.RecyclerViewAdapterone;
import com.example.civilfamilyiitism.Adapters.changeclassadapter;
import com.example.civilfamilyiitism.R;
import com.example.civilfamilyiitism.StudentSideOnly.Friendslistviewer;
import com.example.civilfamilyiitism.studentinfo;
import com.example.civilfamilyiitism.studentinfoincludechangeto;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ApproveChangeClassRequest extends AppCompatActivity {

    RecyclerView rcv;
    changeclassadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_change_class_request);

        rcv=(RecyclerView)findViewById(R.id.rcv);

        rcv.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<studentinfoincludechangeto> options =
                new FirebaseRecyclerOptions.Builder<studentinfoincludechangeto>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("ChangeClassRequests").orderByChild("username"), studentinfoincludechangeto.class)
                        .build();

        adapter = new changeclassadapter(options);
        rcv.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}