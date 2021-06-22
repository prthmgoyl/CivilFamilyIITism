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

public class RegisterPage extends AppCompatActivity {

    private EditText username , email , phone , password;
    private Button registerBtn;
    private TextView alreadyregistered;
    private String username1,email1, phone1,password1;

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

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterPage.this, "Registeration Successful!", Toast.LENGTH_SHORT).show();
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