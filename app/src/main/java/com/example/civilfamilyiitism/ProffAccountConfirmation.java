package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProffAccountConfirmation extends AppCompatActivity {

    EditText edt1,edt2;
    Button btn;
    private String designation,securitycode,check;
    private String username , email,phone ,password,year,uid;
    DatabaseReference reference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proff_account_confirmation);

        edt1 = (EditText)findViewById(R.id.editTextTextPersonName6);
        edt2 = (EditText)findViewById(R.id.editTextTextPersonName7);
        btn = (Button)findViewById(R.id.button8);

        reference = FirebaseDatabase.getInstance().getReference();


        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phone");
        password = getIntent().getStringExtra("password");
        year = "zero";


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               progressDialog = new ProgressDialog(ProffAccountConfirmation.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progressbar_dialog);
               progressDialog.getWindow().setBackgroundDrawableResource(
                       android.R.color.transparent
              );

                designation = edt1.getText().toString();
                securitycode = edt2.getText().toString();

                if(designation.isEmpty()){
                    edt1.setError("Please Enter Your Designation");
                    progressDialog.dismiss();
                }
                else if(securitycode.isEmpty()){
                    edt2.setError("Please enter SecurityCode");
                    progressDialog.dismiss();
                }
                else{
                    reference.child("Access").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                check = snapshot.getValue(String.class);
                                if (securitycode.equals(check)) {
                                    createUser();
                                    progressDialog.dismiss();
                                    //    startActivity(new Intent(ProffAccountConfirmation.this, Professormainpage.class));
                                    //   finish();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(ProffAccountConfirmation.this, "Sorry!Wrong Security Code", Toast.LENGTH_SHORT).show();

                                }
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(ProffAccountConfirmation.this, "Please Set a security code!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressDialog.dismiss();
                            Toast.makeText(ProffAccountConfirmation.this, "Try Again Later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    public void createUser(){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        uid = FirebaseAuth.getInstance().getUid();
                        studentinfo student = new studentinfo(username,email,phone,password,uid,year,designation);
                        reference.child("UserInfo").child(phone).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    reference.child("zero").child(uid).setValue(student);
                                        reference.child("UserInfoWithUid").child(uid).setValue(student);
                                        Toast.makeText(ProffAccountConfirmation.this, "Registeration Successful!", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        startActivity(new Intent(ProffAccountConfirmation.this,Professormainpage.class));
                                        finish();
                                }
                                else{
                                    progressDialog.dismiss();
                                    Toast.makeText(ProffAccountConfirmation.this, "OOps!Connection lost", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(ProffAccountConfirmation.this, "OOps! Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
}