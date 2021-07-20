package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ascepage extends AppCompatActivity {

    private EditText edt1;
    Button btn;
    String message , read="";
    String line = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ascepage);

        edt1= findViewById(R.id.editTextTextMultiLine);
        btn = (Button)findViewById(R.id.button19);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File textfile = new File(Environment.getExternalStorageDirectory(),"uid.txt");
                Toast.makeText(Ascepage.this, Environment.getExternalStorageDirectory().getAbsolutePath(), Toast.LENGTH_SHORT).show();
                try {
                    FileOutputStream fos = new FileOutputStream(textfile);
                    fos.write(FirebaseAuth.getInstance().getUid().getBytes());
                    fos.close();


                    File file = new File(Environment.getExternalStorageDirectory(),"uid.txt");
                    FileInputStream fis = new FileInputStream(file);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader buff = new BufferedReader(isr);


                    while ((line= buff.readLine())!=null){
                        read = read+line;
                    }
                    Toast.makeText(Ascepage.this, read, Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference()
                            .child("UserInfoWithUid")
                            .child(read)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    studentinfo info = snapshot.getValue(studentinfo.class);
                                    String email = info.getUsername();
                                    String password = info.getPassword();
                                   edt1.setText(email + " " +password);
                                    //Toast.makeText(Ascepage.this, email + " " +password, Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(Ascepage.this, "Connection Rejected", Toast.LENGTH_SHORT).show();
                                }
                            });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}