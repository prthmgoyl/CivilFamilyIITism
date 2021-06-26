package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Splashscreen extends AppCompatActivity {

    FirebaseUser user;
    String uid,dateTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        dateTime = simpleDateFormat.format(calendar.getTime());


        Thread thread = new Thread(){
            public void run(){
                try {
                   // sleep(3000);

                    user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user !=null){
                       uid= FirebaseAuth.getInstance().getUid();
                        FirebaseDatabase.getInstance().getReference().child("UsersActivity")
                                .child(dateTime +" "+String.valueOf(System.currentTimeMillis())).setValue(uid);

                        Query checkuser = FirebaseDatabase.getInstance().getReference().child("zero")
                                .orderByChild("uid").equalTo(uid);
                        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    Toast.makeText(Splashscreen.this, "Professor Login Successful!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Splashscreen.this,Professormainpage.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(Splashscreen.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Splashscreen.this,Mainpage.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Splashscreen.this, "OOps! Connection lost", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        sleep(2000);
                        startActivity(new Intent(Splashscreen.this,MainActivity.class));
                        finish();
                    }
                } catch (Exception e) {
                    Toast.makeText(Splashscreen.this, "No Connection!", Toast.LENGTH_SHORT).show();
                }
            }
        };
        thread.start();
    }
}