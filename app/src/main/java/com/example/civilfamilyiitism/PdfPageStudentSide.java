package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;


public class PdfPageStudentSide extends AppCompatActivity {

    RecyclerView rcv;
   PdfRecyclerViewAdapter adapter;
   BottomNavigationView navigation;
   String check = null;
   String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_page_student_side);

        uid = FirebaseAuth.getInstance().getUid();

        FirebaseDatabase.getInstance().getReference("UserInfoWithUid")
                .child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        studentinfo info = snapshot.getValue(studentinfo.class);
                        check = info.getYear();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(PdfPageStudentSide.this, "Connection Error!", Toast.LENGTH_SHORT).show();
            }
        });

        rcv = (RecyclerView)findViewById(R.id.recyclerViewPdfPage);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<putpdf> options =
                new FirebaseRecyclerOptions.Builder<putpdf>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("uploads").child(check), putpdf.class)
                        .build();

        adapter=new PdfRecyclerViewAdapter(options);
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