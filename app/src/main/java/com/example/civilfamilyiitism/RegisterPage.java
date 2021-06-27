package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterPage extends AppCompatActivity {

    private EditText username , email , phone , password;
    private Button registerBtn;
    private TextView alreadyregistered;
    String access;
    DatabaseReference reference;
    private String username1,email1, phone1,password1,uid;
    public String year="notselected";
    private RadioButton rdbtn1,rdbtn2,rdbtn3,rdbtn4,rdbtn5;
    ProgressDialog progressDialog;
    String designation = "student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        username = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.editTextTextEmailAddress2);
        phone = findViewById(R.id.editTextPhone);
        password = findViewById(R.id.editTextTextPassword2);
        registerBtn = findViewById(R.id.button2);
        alreadyregistered = findViewById(R.id.textView4);
        rdbtn1 = findViewById(R.id.radioButton);
        rdbtn2 = findViewById(R.id.radioButton4);
        rdbtn3 = findViewById(R.id.radioButton3);
        rdbtn4 = findViewById(R.id.radioButton2);
        rdbtn5 = findViewById(R.id.radioButton5);
        reference = FirebaseDatabase.getInstance().getReference();

        alreadyregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterPage.this,MainActivity.class));
                 finish();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressDialog = new ProgressDialog(RegisterPage.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progressbar_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(

                        android.R.color.transparent
                );


                reference.child("Access").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            access=snapshot.getValue(String.class);
                            if(access.equalsIgnoreCase("deny")){
                                progressDialog.dismiss();
                                Toast.makeText(RegisterPage.this, "Contact Professor!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                createUser();
                            }

                        }
                        else{
                            createUser();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterPage.this, "Connection Lost!", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }
    public void createUser(){

        username1 = username.getText().toString();
        email1 = email.getText().toString();
        phone1 = phone.getText().toString();
        password1 = password.getText().toString();

        if(rdbtn5.isChecked()){
            year = "zero" ;
        }
        else if(rdbtn1.isChecked()){
            year ="first";
        }
        else if(rdbtn2.isChecked()){
            year = "second" ;
        }
        else if(rdbtn3.isChecked()){
            year = "third";
        }
        else if(rdbtn4.isChecked()){
            year = "fourth";
        }


        if(year.equals("zero")){
            Intent intent = new Intent(this,ProffAccountConfirmation.class);
            intent.putExtra("username",username1);
            intent.putExtra("email",email1);
            intent.putExtra("phone",phone1);
            intent.putExtra("password",password1);
            startActivity(intent);
            finish();
        }
        else{
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        uid = FirebaseAuth.getInstance().getUid();
                        studentinfo student = new studentinfo(username1,email1,phone1,password1,uid,year,designation);
                        reference.child("UserInfo").child(phone1).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    reference.child(year).child(uid).setValue(student);
                                        reference.child("UserInfoWithUid").child(uid).setValue(student);
                                        Toast.makeText(RegisterPage.this, "Registeration Successful!", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        startActivity(new Intent(RegisterPage.this,Mainpage.class));
                                        finish();
                                }
                            }
                        });


                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(RegisterPage.this, "OOps! Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }





    }
}