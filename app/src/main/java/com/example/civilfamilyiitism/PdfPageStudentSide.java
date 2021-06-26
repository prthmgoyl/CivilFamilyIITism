package com.example.civilfamilyiitism;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class PdfPageStudentSide extends AppCompatActivity {

    RecyclerView rcv;
   PdfRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_page_student_side);

        rcv = (RecyclerView)findViewById(R.id.recyclerViewPdfPage);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<putpdf> options =
                new FirebaseRecyclerOptions.Builder<putpdf>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("uploads"), putpdf.class)
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