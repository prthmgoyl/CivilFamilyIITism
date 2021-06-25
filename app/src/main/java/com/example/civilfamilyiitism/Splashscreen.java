package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Splashscreen extends AppCompatActivity {

    FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user !=null){
                       uid= FirebaseAuth.getInstance().getUid();
                        Query checkuser = FirebaseDatabase.getInstance().getReference().child("zero")
                                .orderByChild("uid").equalTo(uid);
                        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    Toast.makeText(Splashscreen.this, "Professor Login Successful!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Splashscreen.this,Professormainpage.class));
                                }
                                else{
                                    Toast.makeText(Splashscreen.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Splashscreen.this,Mainpage.class));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Splashscreen.this, "OOps! Connection lost", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        startActivity(new Intent(Splashscreen.this,MainActivity.class));
                    }
                } catch (InterruptedException e) {
                    Toast.makeText(Splashscreen.this, "No Connection!", Toast.LENGTH_SHORT).show();
                }
            }
        };
        thread.start();
    }
}