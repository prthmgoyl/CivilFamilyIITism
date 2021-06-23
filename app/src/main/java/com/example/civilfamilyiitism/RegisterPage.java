package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity {

    private EditText username , email , phone , password;
    private Button registerBtn;
    private TextView alreadyregistered;
    private String username1,email1, phone1,password1,uid;
    public String year="notselected";
    private RadioButton rdbtn1,rdbtn2,rdbtn3,rdbtn4,rdbtn5;

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

        alreadyregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterPage.this,MainActivity.class));
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            uid = FirebaseAuth.getInstance().getUid();
                            studentinfo student = new studentinfo(username1,email1,phone1,password1,uid,year);
                            FirebaseDatabase.getInstance().getReference().child("UserInfo").child(phone1).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        FirebaseDatabase.getInstance().getReference().child(year).child(uid).setValue(student);
                                        if(year.equals("zero")){
                                            Toast.makeText(RegisterPage.this, "Registeration Successful!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterPage.this,Professormainpage.class));
                                        }
                                        else{
                                            FirebaseDatabase.getInstance().getReference().child("UserInfoWithUid").child(uid).setValue(student);
                                            Toast.makeText(RegisterPage.this, "Registeration Successful!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterPage.this,Mainpage.class));
                                        }

                                    }
                                }
                            });


                        }
                        else{
                            Toast.makeText(RegisterPage.this, "OOps! Try Again Later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}