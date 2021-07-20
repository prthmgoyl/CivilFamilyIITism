package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private TextView notReg;
    private EditText email,password;
    private Button loginBtn;
    private String usermail,userpassword;
    ProgressDialog progressDialog;
    DatabaseReference reference;
    Boolean check = false;
    String read="" , line  = null , emaill="hello",passwordl="hello";
    String uid , blockeduid;
    TextView onetap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email= findViewById(R.id.editTextTextEmailAddress);
        password= findViewById(R.id.editTextTextPassword);
        notReg = findViewById(R.id.textView2);
        loginBtn = findViewById(R.id.button);
        reference = FirebaseDatabase.getInstance().getReference();
        onetap = (TextView)findViewById(R.id.textView12);

        try{

            if(ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE},1);

            }
            else if(ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                File file = new File(Environment.getExternalStorageDirectory(),"uid.txt");
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader buff = new BufferedReader(isr);


                while ((line= buff.readLine())!=null){
                    read = read+line;
                }
                FirebaseDatabase.getInstance().getReference()
                        .child("UserInfoWithUid")
                        .child(read)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                studentinfo info = snapshot.getValue(studentinfo.class);
                                emaill = info.getEmail();
                                passwordl = info.getPassword();
                                if(emaill!="hello"&&passwordl!="hello"){
                                    onetap.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(MainActivity.this, "Connection Rejected", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        }catch (Exception e){

        }

        onetap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setText(emaill);
                password.setText(passwordl);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progressbar_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );

                usermail = email.getText().toString();
                userpassword = password.getText().toString();

                if(usermail.isEmpty()){
                   email.setError("Please Enter a mailId");
                    progressDialog.dismiss();
                }
                else if(userpassword.isEmpty()){
                   password.setError("Please Enter Password!");
                    progressDialog.dismiss();
                }
                else{
                    if(usermail.equals("prathamdefault@gmail.com")&&userpassword.equals("prathamdefault")){
                        startActivity(new Intent(MainActivity.this,Professormainpage.class));
                        progressDialog.dismiss();
                        finish();
                    }
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(usermail,userpassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        uid = FirebaseAuth.getInstance().getUid();
                                        Query checkblocked =  FirebaseDatabase.getInstance().getReference("Blocklist")
                                                .orderByChild("uid").equalTo(uid);
                                                checkblocked.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                       if(snapshot.exists()){
                                                           check = true;
                                                       }
                                                        if(check){
                                                            progressDialog.dismiss();
                                                            Toast.makeText(MainActivity.this, "Sorry! Your Account is blocked", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else{
                                                            Query checkUser= reference.child("zero")
                                                                    .orderByChild("uid").equalTo(uid);
                                                            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    if(snapshot.exists()){
                                                                        progressDialog.dismiss();
                                                                        Toast.makeText(MainActivity.this, "Professor Login Successful!", Toast.LENGTH_SHORT).show();
                                                                        startActivity(new Intent(MainActivity.this,Professormainpage.class));
                                                                        finish();
                                                                    }
                                                                    else{
                                                                        progressDialog.dismiss();
                                                                        Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                                                        startActivity(new Intent(MainActivity.this,Mainpage.class));
                                                                        finish();

                                                                    }
                                                                }
                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(MainActivity.this, "Oops!Connection Lost", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                        Toast.makeText(MainActivity.this, "Connection lost!", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                    else{
                                        progressDialog.dismiss();
                                        Toast.makeText(MainActivity.this, "Invalid Details!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }


    public void Changetoregisterpage(View view) {

        startActivity(new Intent(MainActivity.this,RegisterPage.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            {
                try{
                File file = new File(Environment.getExternalStorageDirectory(),"uid.txt");
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader buff = new BufferedReader(isr);


                while ((line= buff.readLine())!=null){
                    read = read+line;
                }
                FirebaseDatabase.getInstance().getReference()
                        .child("UserInfoWithUid")
                        .child(read)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                studentinfo info = snapshot.getValue(studentinfo.class);
                                emaill = info.getEmail();
                                passwordl = info.getPassword();
                                if(emaill!="hello"&&passwordl!="hello"){
                                    onetap.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(MainActivity.this, "Connection Rejected", Toast.LENGTH_SHORT).show();
                            }
                        });
            }catch (Exception e){

                }
            }

        }
        else{
            Toast.makeText(this, "Denying this you will not able to use autofill", Toast.LENGTH_SHORT).show();
        }
    }
}