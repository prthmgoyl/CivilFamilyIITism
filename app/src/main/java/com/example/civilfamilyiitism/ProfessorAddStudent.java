package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class ProfessorAddStudent extends AppCompatActivity {

    private String username1,email1, phone1,password1,uid;
    private EditText username , email , phone , password,desig;
    private Button registerBtn;
    public String year="notselected";
    String designation = "student";
    private RadioButton rdbtn1,rdbtn2,rdbtn3,rdbtn4,rdbtn5;
    String profuid,e,email12,password12;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_add_student);

        username = findViewById(R.id.editTextTextPersonName5);
        email = findViewById(R.id.editTextTextEmailAddress4);
        phone = findViewById(R.id.editTextPhone3);
        password = findViewById(R.id.editTextTextPassword3);
        desig = (EditText)findViewById(R.id.editTextTextPersonName8);
        registerBtn = findViewById(R.id.button7);
        rdbtn1 = findViewById(R.id.radioButton6);
        rdbtn2 = findViewById(R.id.radioButton7);
        rdbtn3 = findViewById(R.id.radioButton8);
        rdbtn4 = findViewById(R.id.radioButton9);
        rdbtn5 = findViewById(R.id.radioButton10);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profuid = FirebaseAuth.getInstance().getUid();
                FirebaseAuth.getInstance().signOut();
                createUser();
                FirebaseAuth.getInstance().signOut();
                studentinfo studentinfo = new studentinfo();
                FirebaseDatabase.getInstance().getReference().child("UserInfoWithUid").child(profuid)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                email12 = snapshot.child("email").getValue(String.class);
                                password12 = snapshot.child("password").getValue(String.class);

                                FirebaseAuth.getInstance().signInWithEmailAndPassword(email12,password12);
                                Toast.makeText(ProfessorAddStudent.this, "Added Successfully!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(ProfessorAddStudent.this, "Error!", Toast.LENGTH_SHORT).show();
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

        if(rdbtn1.isChecked()){
            year = "zero" ;
            designation=  desig.getText().toString();
        }
        else if(rdbtn2.isChecked()){
            year ="first";
        }
        else if(rdbtn3.isChecked()){
            year = "second" ;
        }
        else if(rdbtn4.isChecked()){
            year = "third";
        }
        else if(rdbtn5.isChecked()){
            year = "fourth";
        }


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
                                    Toast.makeText(ProfessorAddStudent.this, "Registeration Successful!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                    else{
                        Toast.makeText(ProfessorAddStudent.this, "OOps! Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
}