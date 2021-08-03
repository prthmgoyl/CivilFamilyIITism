package com.example.civilfamilyiitism.ProfessorSideOnly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.civilfamilyiitism.R;
import com.example.civilfamilyiitism.studentinfo;
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
    String designation = "null";
    private RadioButton rdbtn1,rdbtn2,rdbtn3,rdbtn4,rdbtn5;
    String profuid,email12,password12;
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

        reference = FirebaseDatabase.getInstance().getReference();

        try {
            profuid = FirebaseAuth.getInstance().getUid();
            reference.child("UserInfoWithUid").child(profuid)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            email12 = snapshot.child("email").getValue(String.class);
                            password12 = snapshot.child("password").getValue(String.class);

                            // Toast.makeText(ProfessorAddStudent.this, "Added Successfully!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(ProfessorAddStudent.this, "Error!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ProfessorAddStudent.this, Professormainpage.class));
                            finish();
                        }
                    });
        }
        catch (Exception e){
            Toast.makeText(ProfessorAddStudent.this, "Error!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfessorAddStudent.this, Professormainpage.class));
            finish();
        }


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                createUser();
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
            designation = "firstyear";
        }
        else if(rdbtn3.isChecked()){
            year = "second" ;
            designation = "secondyear";
        }
        else if(rdbtn4.isChecked()){
            year = "third";
            designation = "thirdyear";
        }
        else if(rdbtn5.isChecked()){
            year = "fourth";
            designation = "fourthyear";
        }
        else{
            designation = "null";
        }



        if (username1.isEmpty()) {
            username.setError("Please Enter Username");
        } else if (email1.isEmpty()) {
            email.setError("Please Enter a emailId");
        } else if ((phone1.length() <= 9) || (phone1.length() >= 11)||(phone1.startsWith("+91"))) {
            phone.setError("Please Enter a valid Phone Number!");
        } else if (password1.length() <= 7) {
            password.setError("Password length must be greater than 8");
        } else if (designation.equals("null")) {
            Toast.makeText(this, "You! must select your account type", Toast.LENGTH_SHORT).show();
        }
        else{
            if(email1.endsWith("iitism.ac.in") ){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            try{
                            uid = FirebaseAuth.getInstance().getUid();
                            studentinfo student = new studentinfo(username1,email1,phone1,password1,uid,year,designation ,null);
                            reference.child("UserInfo").child(phone1).setValue(student);
                            reference.child(year).child(uid).setValue(student);
                            reference.child("UserInfoWithUid").child(uid).setValue(student);
                            FirebaseAuth.getInstance().signOut();
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(email12,password12);
                            Toast.makeText(ProfessorAddStudent.this, "Registeration Successful!", Toast.LENGTH_SHORT).show();
                            }catch (Exception e){
                                FirebaseAuth.getInstance().signOut();
                                FirebaseAuth.getInstance().signInWithEmailAndPassword(email12,password12);
                                Toast.makeText(ProfessorAddStudent.this, "OOps! Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            FirebaseAuth.getInstance().signOut();
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(email12,password12);
                            Toast.makeText(ProfessorAddStudent.this, "OOps! Try Again Later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
                else {
                    email.setError("Sorry!! Only Official accounts are allowed");
              }
        }
    }
}