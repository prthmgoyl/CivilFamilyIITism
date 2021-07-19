package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Professormainpage extends AppCompatActivity {

    private ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9,img10 , img11,img12;
    String check = "null";
    Uri filepath;
    ImageView userimage;
    Bitmap bitmap;
     TextView name;
    RecyclerView rcv;
    ProfessorRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professormainpage);

        img1 = findViewById(R.id.imageView13);
        img2 = findViewById(R.id.imageView12);
        img3 = findViewById(R.id.imageView15);
        img4 = findViewById(R.id.imageView14);
        img5 = findViewById(R.id.imageView9);
        img6 = findViewById(R.id.imageView);
        img7 = findViewById(R.id.imageView11);
        img8 = findViewById(R.id.imageView10);
        img9 = findViewById(R.id.imageView17);
        img10 = findViewById(R.id.imageView16);
        img11 = findViewById(R.id.imageView29);
        img12 = findViewById(R.id.imageView30);
       userimage = (ImageView)findViewById(R.id.imageView18);
       name = (TextView)findViewById(R.id.textView11);


        rcv = (RecyclerView)findViewById(R.id.recyclerViewproff);
        rcv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        FirebaseRecyclerOptions<studentinfo> options =
                new FirebaseRecyclerOptions.Builder<studentinfo>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("zero"), studentinfo.class)
                        .build();

        adapter=new ProfessorRecyclerViewAdapter(options);
        rcv.setAdapter(adapter);

        try{
            FirebaseDatabase.getInstance().getReference().child("UserInfoWithUid")
                    .child(FirebaseAuth.getInstance().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            studentinfo info = snapshot.getValue(studentinfo.class);
                            name.setText("Welcome! "+info.getUsername().toUpperCase());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {
            FirebaseStorage.getInstance().getReference().child("images")
                    .child(FirebaseAuth.getInstance().getUid())
                    .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                         if(uri!=null){
                             Glide
                                     .with(getApplicationContext())
                                     .load(uri.toString())
                                     .centerCrop()
                                     .into(userimage);
                         }
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        userimage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_PICK);
               intent.setType("image/");
               startActivityForResult(Intent.createChooser(intent,"SelectImage"),1);
           }
       });



        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "fourth";
               // Toast.makeText(Professormainpage.this, "img1"+check, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Professormainpage.this,Studentlistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);
       //         startActivity(new Intent(Professormainpage.this,Studentlistviewer.class));
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "third";
                //Toast.makeText(Professormainpage.this, "img2"+check, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Professormainpage.this,Studentlistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "second";
                //Toast.makeText(Professormainpage.this, "img3"+check, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Professormainpage.this,Studentlistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "first";
                //Toast.makeText(Professormainpage.this, "img4"+check, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Professormainpage.this,Studentlistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivity(new Intent(Professormainpage.this,UploadPdfpage.class));
            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Professormainpage.this, "img6", Toast.LENGTH_SHORT).show();
                      startActivity(new Intent(Professormainpage.this,NoticePageProff.class));
            }
        });
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Professormainpage.this, "ASCE PAGE!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Professormainpage.this,Ascepage.class));
            }
        });
        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(Professormainpage.this,ProfessorAddStudent.class));
            }
        });
        img9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Professormainpage.this,Blocklist.class));
                Toast.makeText(Professormainpage.this, "img9", Toast.LENGTH_SHORT).show();
            }
        });
        img10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(Professormainpage.this, "img10", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Professormainpage.this,Stopregisteration.class));
            }
        });
        img11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Professormainpage.this, "img11", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Professormainpage.this,Updateclass.class));
            }
        });
        img12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Professormainpage.this, "img12", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            filepath = data.getData();
            try {
                FirebaseStorage.getInstance().getReference().child("images").child(FirebaseAuth.getInstance().getUid())
                        .putFile(filepath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                FirebaseStorage.getInstance().getReference().child("images")
                                        .child(FirebaseAuth.getInstance().getUid())
                                        .getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                FirebaseDatabase.getInstance().getReference()
                                                        .child("Images")
                                                        .child(FirebaseAuth.getInstance().getUid())
                                                        .setValue(uri.toString());
                                            }
                                        });


                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    }
                });


            } catch (Exception e) {

            }
        }
    }
}