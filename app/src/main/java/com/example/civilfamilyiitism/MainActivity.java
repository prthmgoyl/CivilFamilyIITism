package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView notReg;
    private EditText email,password;
    private Button loginBtn;
    private String usermail,userpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email= findViewById(R.id.editTextTextEmailAddress);
        password= findViewById(R.id.editTextTextPassword);
        notReg = findViewById(R.id.textView2);
        loginBtn = findViewById(R.id.button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usermail = email.getText().toString();
                userpassword = password.getText().toString();
 //               if(usermail.equals("prathamdefault@gmail.com")&&userpassword.equals("prathamdefault")){
//                    startActivity(new Intent(MainActivity.this,Mainpage.class));
//                }
                FirebaseAuth.getInstance().signInWithEmailAndPassword(usermail,userpassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Query checkUser= FirebaseDatabase.getInstance().getReference().child("zero")
                                    .orderByChild("uid").equalTo(FirebaseAuth.getInstance().getUid());
                            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        Toast.makeText(MainActivity.this, "Professor Login Successful!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this,Professormainpage.class));
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this,Mainpage.class));
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Invalid Details!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    public void Changetoregisterpage(View view) {
        startActivity(new Intent(MainActivity.this,RegisterPage.class));
    }
}