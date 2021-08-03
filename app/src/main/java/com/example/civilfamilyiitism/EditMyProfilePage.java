package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import org.jetbrains.annotations.NotNull;

public class EditMyProfilePage extends AppCompatActivity {
    EditText admno , username , phoneno;
    TextView email , year;
    String uid,number;
    Button updatebtn;
    ImageView userimage;
    studentinfo info;
    String check;
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
                 info = snapshot.getValue(studentinfo.class);
                 number = info.getPhone();
                 admno.setText(info.getDesignation());
                username.setText(info.getUsername());
                phoneno.setText(info.getPhone());
                email.setText(info.getEmail());
                check = info.getYear();

                if(check.equals("zero")){
                    year.setText("Professor");
                }
                else if (check.equals("first")){
                    year.setText("First Year");
                }
                else if (check.equals("second")){
                    year.setText("Second Year");
                }
                else if (check.equals("third")){
                    year.setText("Third Year");
                }
                else if (check.equals("fourth")){
                    year.setText("Fourth Year");
                }
                else{
                    year.setText("Not Found");
                }


                Glide
                        .with(getApplicationContext())
                        .load(R.drawable.ic_baseline_person_24_userimage)
                        .centerCrop()
                        .into(userimage);

                if(info.getImgurl()!=null && !((info.getImgurl()).isEmpty())){
                    Glide
                            .with(getApplicationContext())
                            .load(info.getImgurl())
                            .centerCrop()
                            .into(userimage);
                }
                else{
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditMyProfilePage.this, "Connection Rejected!", Toast.LENGTH_SHORT).show();
            }
        });

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference()
                        .child("permissions").child("Changeclass").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            try{
                                if((snapshot.getValue(String.class)).equalsIgnoreCase("allow")&&
                                   !(check.equalsIgnoreCase("zero"))){
                                    Dialog dialog  = new Dialog(EditMyProfilePage.this);
                                    dialog.setContentView(R.layout.classdialog);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog.show();
                                    Spinner spin = (Spinner)dialog.findViewById(R.id.spinner);
                                    Button btn = (Button)dialog.findViewById(R.id.button22);

                                    btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                           String changedclass = spin.getSelectedItem().toString();
                                            dialog.dismiss();
                                            sendrequest(changedclass);
                                            Toast.makeText(EditMyProfilePage.this,"Request Sent: " +changedclass, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                else{
                                    Toast.makeText(EditMyProfilePage.this, "Contact Professor!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e){
                                Toast.makeText(EditMyProfilePage.this, "Error!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(EditMyProfilePage.this, "Contact Professor!!!", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        Toast.makeText(EditMyProfilePage.this, "Connection lost", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditMyProfilePage.this);
                builder.setTitle("Update Profile");
                builder.setMessage("Are you sure you want to update your profile...");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        info.setDesignation(admno.getText().toString());
                        info.setUsername(username.getText().toString());
                        info.setPhone(phoneno.getText().toString());

                        FirebaseDatabase.getInstance().getReference()
                                .child("UserInfoWithUid").child(uid).setValue(info);
                        FirebaseDatabase.getInstance().getReference()
                                .child("UserInfo").child(number).removeValue();
                        FirebaseDatabase.getInstance().getReference()
                                .child("UserInfo").child(info.getPhone()).setValue(info);
                        FirebaseDatabase.getInstance().getReference()
                                .child(info.getYear()).child(info.getUid()).setValue(info);
                        Toast.makeText(EditMyProfilePage.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                        number = phoneno.getText().toString();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });
    }

    private void sendrequest(String changedclass) {
        try{

            FirebaseDatabase.getInstance().getReference()
                    .child("ChangeClassRequests").child(uid).setValue(info);
            FirebaseDatabase.getInstance().getReference()
                    .child("ChangeClassRequests").child(uid).child("changeto").setValue(changedclass);
        }
        catch (Exception e){

        }
    }
}