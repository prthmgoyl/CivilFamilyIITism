package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NoticePageProff extends AppCompatActivity {

    Button btn;
    DatabaseReference reference;
    String uid , time ,timeone , year = "null",to , username;
    RadioButton rdbtn1 , rdbtn2 , rdbtn3 , rdbtn4, rdbtn5;
    RecyclerView rcv;
    noticeadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_page_proff);

        btn = (Button)findViewById(R.id.button9);
        btn.setText("Add Notice");
        reference = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getUid();




        rcv = (RecyclerView)findViewById(R.id.recyclerviewnotice);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<noticemodel> options =
                new FirebaseRecyclerOptions.Builder<noticemodel>()
                        .setQuery(reference.child("Notice").child("all"), noticemodel.class)
                        .build();

        adapter=new noticeadapter(options);
        rcv.setAdapter(adapter);


        FirebaseDatabase.getInstance().getReference().child("zero").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        username = snapshot.child("username").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(NoticePageProff.this, "OOps!Connection lost", Toast.LENGTH_SHORT).show();
                    }
                });




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogPlus dialogPlus = DialogPlus.newDialog(NoticePageProff.this)
                        .setExpanded(true,500)
                        .setContentHolder(new ViewHolder(R.layout.noticealertdialog))
                        .setContentBackgroundResource(R.drawable.noticedialogbg)
                        .create();

                View view = dialogPlus.getHolderView();
                EditText message = (EditText)view.findViewById(R.id.editTextTextPersonName9);
                Button publishbtn = (Button)view.findViewById(R.id.button10);
                rdbtn1= view.findViewById(R.id.radioButton11);
                rdbtn2=view.findViewById(R.id.radioButton12);
                rdbtn3=view.findViewById(R.id.radioButton13);
                rdbtn4=view.findViewById(R.id.radioButton14);
                rdbtn5=view.findViewById(R.id.radioButton15);

                dialogPlus.show();

                publishbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy hh:mm:ss aa");
                        Calendar calendar = Calendar.getInstance();
                        timeone  = simpleDateFormat.format(calendar.getTime());
                        time = timeone + " " +String.valueOf(System.currentTimeMillis());
                        String noticemessage = message.getText().toString();
                        if(noticemessage.isEmpty()){
                            message.setError("Please! Type a message");
                        }
                        else{
                            if(rdbtn1.isChecked()){
                                to = "all";
                                year = "all";
                                noticemodel messageinfo = new noticemodel(uid , timeone ,noticemessage , to , username , time , year);
                                reference.child("Notice").child("all").child(time).setValue(messageinfo);

                                year = "first";
                                reference.child("Notice").child(year).child(time).setValue(messageinfo);

                                year = "second";;
                                reference.child("Notice").child(year).child(time).setValue(messageinfo);

                                year = "third";
                                reference.child("Notice").child(year).child(time).setValue(messageinfo);

                                year = "fourth";
                                reference.child("Notice").child(year).child(time).setValue(messageinfo);
                                dialogPlus.dismiss();
                            }
                            else if(rdbtn2.isChecked()){
                                to = "first";
                                year = "first";
                                noticemodel messageinfo = new noticemodel(uid , timeone ,noticemessage , to, username , time , year);
                                //Toast.makeText(NoticePageProff.this, "=="+noticemessage, Toast.LENGTH_SHORT).show();
                                reference.child("Notice").child(year).child(time).setValue(messageinfo);
                                reference.child("Notice").child("all").child(time).setValue(messageinfo);
                                dialogPlus.dismiss();
                            }
                            else if (rdbtn3.isChecked()){
                                to = "second";
                                year = "second";
                                noticemodel messageinfo = new noticemodel(uid , timeone ,noticemessage , to, username , time , year);
                                //Toast.makeText(NoticePageProff.this, "=="+noticemessage, Toast.LENGTH_SHORT).show();
                                reference.child("Notice").child(year).child(time).setValue(messageinfo);
                                reference.child("Notice").child("all").child(time).setValue(messageinfo);
                                dialogPlus.dismiss();
                            }
                            else if(rdbtn4.isChecked()){
                                to = "third";
                                year = "third";
                                noticemodel messageinfo = new noticemodel(uid , timeone ,noticemessage , to, username , time,year);
                                //Toast.makeText(NoticePageProff.this, "=="+noticemessage, Toast.LENGTH_SHORT).show();
                                reference.child("Notice").child(year).child(time).setValue(messageinfo);
                                reference.child("Notice").child("all").child(time).setValue(messageinfo);
                                dialogPlus.dismiss();
                            }
                            else if(rdbtn5.isChecked()){
                                to = "fourth";
                                year = "fourth";
                                noticemodel messageinfo = new noticemodel(uid , timeone ,noticemessage , to, username , time,year);
                                //Toast.makeText(NoticePageProff.this, "=="+noticemessage, Toast.LENGTH_SHORT).show();
                                reference.child("Notice").child(year).child(time).setValue(messageinfo);
                                reference.child("Notice").child("all").child(time).setValue(messageinfo);
                                dialogPlus.dismiss();
                            }
                            else{
                                Toast.makeText(NoticePageProff.this, "You must need to select a category", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });


            }
        });

     //   getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.noticepagebackground));
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}