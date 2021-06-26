package com.example.civilfamilyiitism;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Mainpage extends AppCompatActivity {

    private ImageView img1,img2,img3,img4,img5,img6,img7,img8;
    String check = "null";

    RecyclerView rcv;
    ProfessorRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        img1=findViewById(R.id.imageView1);
        img2=findViewById(R.id.imageView2);
        img3=findViewById(R.id.imageView3);
        img4=findViewById(R.id.imageView4);
        img5=findViewById(R.id.imageView5);
        img6=findViewById(R.id.imageView6);
        img7=findViewById(R.id.imageView7);
        img8=findViewById(R.id.imageView8);




        rcv = (RecyclerView)findViewById(R.id.recyclerViewproffstudentside);
        rcv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        FirebaseRecyclerOptions<studentinfo> options =
                new FirebaseRecyclerOptions.Builder<studentinfo>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("zero"), studentinfo.class)
                        .build();

        adapter=new ProfessorRecyclerViewAdapter(options);
        rcv.setAdapter(adapter);






                img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                        check = "fourth";
                        Toast.makeText(Mainpage.this, "img1"+check, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Mainpage.this,Friendslistviewer.class);
                        intent.putExtra("keycheck",check);
                        startActivity(intent);
                    }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "third";
                Toast.makeText(Mainpage.this, "img2"+check, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Mainpage.this,Friendslistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);

            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "second";
                Toast.makeText(Mainpage.this, "img3"+check, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Mainpage.this,Friendslistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);

            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "first";
                Toast.makeText(Mainpage.this, "img4"+check, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Mainpage.this,Friendslistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);

            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Mainpage.this, "img5", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Mainpage.this,PdfPageStudentSide.class));

            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Mainpage.this, "img6", Toast.LENGTH_SHORT).show();

            }
        });
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Mainpage.this, "img7", Toast.LENGTH_SHORT).show();

            }
        });
        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Mainpage.this, "img8", Toast.LENGTH_SHORT).show();

            }
        });
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