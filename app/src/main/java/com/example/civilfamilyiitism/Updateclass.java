package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Updateclass extends AppCompatActivity {

    String year = "zero";
    String check = "null";
    studentinfo info;
    String professorcode,developerscode;
    EditText edt1,edt2,edt3;
    Button btn,sendotp , restablishbtn ,backup ;
    String codebysystem = "null";
    String uid, email,password,time;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateclass);

        edt1 = (EditText)findViewById(R.id.editTextTextPersonName11);
        edt2 = (EditText)findViewById(R.id.editTextTextPersonName12);
        edt3 = (EditText)findViewById(R.id.editTextTextPersonName13);
        btn = (Button)findViewById(R.id.button15);
        sendotp = (Button)findViewById(R.id.button16);
        uid = FirebaseAuth.getInstance().getUid();
       restablishbtn = (Button)findViewById(R.id.button17);
       backup = (Button)findViewById(R.id.button18);



        FirebaseDatabase.getInstance().getReference("UserInfoWithUid")
                .child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentinfo info = snapshot.getValue(studentinfo.class);
                email = info.getEmail();
                password = info.getPassword();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
                time = format.format(Calendar.getInstance().getTime());

                FirebaseDatabase.getInstance().getReference()
                        .child("UserInfoWithUid").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snp : snapshot.getChildren()) {
                            info = snp.getValue(studentinfo.class);
                            FirebaseDatabase.getInstance().getReference()
                                    .child("Backup").child("Backup" + time).child(info.getUid())
                                    .setValue(info);
                        }
                        Toast.makeText(Updateclass.this, "Successful!", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Updateclass.this, "Connection Rejected!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        restablishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Updateclass.this);
                    builder.setTitle("Cofirmation...");
                    builder.setMessage("Are you sure you want to backup all data..");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(edt1.getText().toString().equals("7027603081")
                                    &&edt2.getText().toString().equals("7027603081")
                                    &&edt3.getText().toString().equals("7027603081") )
                            {
                                Toast.makeText(Updateclass.this, "Plz follow next steps", Toast.LENGTH_SHORT).show();
                                restablishbtn.setText("Confirm Reestablishmant");
                                edt1.setText("");
                                edt1.setError("Now Enter Backup name here");
                                i=1;
                            }
                            else{
                                Toast.makeText(Updateclass.this, "Contact Developers!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }) ;
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Updateclass.this, "Cancelled!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else if(i==1){
                    Toast.makeText(Updateclass.this, edt1.getText().toString(), Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference()
                            .child("Backup").child(edt1.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snp :snapshot.getChildren()){
                                info = snp.getValue(studentinfo.class);
                                FirebaseDatabase.getInstance().getReference()
                                        .child("UserInfoWithUid").child(info.getUid()).setValue(info);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Updateclass.this, "Connection Unavailable!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Access").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        professorcode = snapshot.getValue(String.class);
                        developerscode = professorcode+"7027603081";
                        phoneverificationcode();
                        // Toast.makeText(Updateclass.this, "!!"+professorcode, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Updateclass.this, "Connection lost!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edt3.getText().toString().length()!=6){
                    edt3.setError("Please Enter a valid OTP!");
                }
                else{
                    if(codebysystem.equals("null")){
                        Toast.makeText(Updateclass.this, "Please Enter SendOTP", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        PhoneAuthCredential credential =PhoneAuthProvider.getCredential(codebysystem,edt3.getText().toString());
                        signInWithPhoneAuthCredential(credential);
                    }
                }

            }
        });
    }
    public void phoneverificationcode(){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber("+917027603081")       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(Updateclass.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codebysystem = s;
            Toast.makeText(Updateclass.this, "OTP sent", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
             String code = phoneAuthCredential.getSmsCode();
            Toast.makeText(Updateclass.this, "!!"+code, Toast.LENGTH_SHORT).show();
            signInWithPhoneAuthCredential(phoneAuthCredential);
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
       FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
                            time = format.format(Calendar.getInstance().getTime());

                            FirebaseDatabase.getInstance().getReference()
                                    .child("UserInfoWithUid").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot snp : snapshot.getChildren()){
                                        info = snp.getValue(studentinfo.class);
                                        year = info.getYear();
                                       // Toast.makeText(Updateclass.this, "!!"+year, Toast.LENGTH_SHORT).show();
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("Backup").child("Backup"+time).child(info.getUid())
                                                .setValue(info);

                                        switch (year){
                                            case "first":
                                                info.setYear("second");
                                                info.setDesignation("secondyear");
                                                updatedata();
                                                applydata();
                                                break;
                                            case "second":
                                                info.setYear("third");
                                                info.setDesignation("thirdyear");
                                                updatedata();
                                                applydata();
                                                break;
                                            case "third":
                                                info.setYear("fourth");
                                                info.setDesignation("fourthyear");
                                                updatedata();
                                                applydata();
                                                break;
                                            case "fourth":
                                                info.setYear("passout");
                                                info.setDesignation("passout");
                                                updatedata();
                                                deletedata();
                                                break;
                                            default:
                                                updatedata();
                                        }
                                    }
                                    FirebaseAuth.getInstance().signOut();
                                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password);
                                    Toast.makeText(Updateclass.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                                }

                                private void deletedata() {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("UserInfoWithUid").child(info.getUid()).removeValue();
                                }

                                private void applydata() {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("UserInfoWithUid").child(info.getUid()).setValue(info);
                                }

                                private void updatedata() {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("Applicable").child(info.getUid()).setValue(info);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(Updateclass.this, "Connection Rejected!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            edt3.setError("Invalid Otp");
                        }
                    }
                });
    }
}