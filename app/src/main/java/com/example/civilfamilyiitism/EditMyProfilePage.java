package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class EditMyProfilePage extends AppCompatActivity {
    EditText admno , username , phoneno;
    TextView email , year;
    String uid;
    Button updatebtn;
    ImageView userimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile_page);

        uid = FirebaseAuth.getInstance().getUid();
        admno= (EditText)findViewById(R.id.editTextTextPersonName14);
        username= (EditText)findViewById(R.id.editTextTextPersonName15);
        email = (TextView) findViewById(R.id.editTextTextEmailAddress5);
        phoneno= (EditText)findViewById(R.id.editTextPhone4);
        year = (TextView)findViewById(R.id.textView25);
        updatebtn = (Button)findViewById(R.id.button20);
        userimage = (ImageView)findViewById(R.id.imageView32);


        FirebaseDatabase.getInstance().getReference().child("UserInfoWithUid")
                .child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentinfo info = snapshot.getValue(studentinfo.class);
                username.setText(info.getUsername());
                phoneno.setText(info.getPhone());
                email.setText(info.getEmail());
                year.setText(info.getYear().toUpperCase());

                try {
                    FirebaseStorage.getInstance().getReference().child("images")
                            .child(uid)
                            .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if(uri!=null){
                                Glide
                                        .with(getApplicationContext())
                                        .load(uri.toString())
                                        .centerCrop()
                                        .into(userimage);
                            }
                        }
                    });

                } catch (Exception e) {
                    Toast.makeText(EditMyProfilePage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditMyProfilePage.this, "Connection Rejected!", Toast.LENGTH_SHORT).show();
            }
        });


        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}