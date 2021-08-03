package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
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

public class MyPersonalInformation extends AppCompatActivity {
    Switch visibleswitch;
    TextView name;
    String uid;
    Button update;
    personalinfo myinfo;
    ImageView userimage;
    EditText alternatephone , fatherphone , motherphone , presentadd , permanentadd , dob;
    String visibility , calternatephone, cfatherphone , cmotherphone , cpresentadd , cpermanentadd , cdob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_personal_information);

        uid = FirebaseAuth.getInstance().getUid();

        visibleswitch = (Switch)findViewById(R.id.switch2);
        name = (TextView)findViewById(R.id.textView29);
        update = (Button)findViewById(R.id.button21);
        alternatephone = (EditText)findViewById(R.id.editTextPhone5);
        fatherphone = (EditText)findViewById(R.id.editTextPhone6);
        motherphone = (EditText)findViewById(R.id.editTextPhone7);
        presentadd= (EditText)findViewById(R.id.editTextTextMultiLine3);
        permanentadd = (EditText)findViewById(R.id.editTextTextMultiLine4);
        dob = (EditText)findViewById(R.id.editTextDate);
        userimage = (ImageView)findViewById(R.id.imageView38);


        try{
        FirebaseDatabase.getInstance().getReference()
                .child("UserInfoWithUid").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        studentinfo info = snapshot.getValue(studentinfo.class);
                        name.setText(info.getUsername().toUpperCase());

                        try {
                            Glide
                                    .with(getApplicationContext())
                                    .load(R.drawable.ic_baseline_person_24_userimage)
                                    .centerCrop()
                                    .into(userimage);

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
                            Toast.makeText(MyPersonalInformation.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                        FirebaseDatabase.getInstance().getReference()
                                .child("personalinfo").child(uid)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                     if(snapshot.exists()){
                                         myinfo = snapshot.getValue(personalinfo.class);
                                         visibility = myinfo.getVisibility();
                                         calternatephone = myinfo.getAlternatephone();
                                         cfatherphone = myinfo.getFatherphone();
                                         cmotherphone =  myinfo.getMotherphone();
                                         cpresentadd = myinfo.getPresentaddress();
                                         cpermanentadd = myinfo.getPermanentaddress();
                                         cdob = myinfo.getDob();

                                         if(visibility.equals("public")){
                                             visibleswitch.setChecked(true);
                                         }
                                         alternatephone.setText(calternatephone);
                                         fatherphone.setText(cfatherphone);
                                         motherphone.setText(cmotherphone);
                                         presentadd.setText(cpresentadd);
                                         permanentadd.setText(cpermanentadd);
                                         dob.setText(cdob);


                                     }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(MyPersonalInformation.this, "Sorry!your data loading failed...", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MyPersonalInformation.this, "Unable to find your data", Toast.LENGTH_SHORT).show();
                    }
                });}
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(visibleswitch.isChecked()){
                    visibility = "public";
                }
                else{
                    visibility = "private";
                }
                calternatephone = alternatephone.getText().toString();
                cfatherphone = fatherphone.getText().toString();
                cmotherphone =  motherphone.getText().toString();
                cpresentadd = presentadd.getText().toString();
                cpermanentadd = permanentadd.getText().toString();
                cdob =dob.getText().toString();

                personalinfo newinfo = new personalinfo(visibility ,calternatephone , cfatherphone, cmotherphone ,cpresentadd,cpermanentadd,cdob);

                FirebaseDatabase.getInstance().getReference()
                        .child("personalinfo").child(uid).setValue(newinfo);

                Toast.makeText(MyPersonalInformation.this, "Data Saved Successfully..", Toast.LENGTH_SHORT).show();

            }
        });
        visibleswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    visibility = "public";
                  //  HashMap<String, String> map = new HashMap<String,String>();
                 //   map.put("visibility",visibility)
                    FirebaseDatabase.getInstance().getReference()
                            .child("personalinfo").child(uid).child("visibility").setValue(visibility);
                  //  Toast.makeText(MyPersonalInformation.this, "Info set to public", Toast.LENGTH_SHORT).show();
                }
                else {
                    visibility = "private";
                    FirebaseDatabase.getInstance().getReference()
                            .child("personalinfo").child(uid).child("visibility").setValue(visibility);
                  //  Toast.makeText(MyPersonalInformation.this, "Info set to private", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}