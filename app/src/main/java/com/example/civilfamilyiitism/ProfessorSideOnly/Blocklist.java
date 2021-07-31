package com.example.civilfamilyiitism.ProfessorSideOnly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.civilfamilyiitism.Adapters.blocklistadapter;
import com.example.civilfamilyiitism.R;
import com.example.civilfamilyiitism.studentinfo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Blocklist extends AppCompatActivity {

    RecyclerView rcv;
    blocklistadapter adapter;
    EditText edtuid;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocklist);

        edtuid = (EditText)findViewById(R.id.editTextTextPersonName10);
        btn = (Button)findViewById(R.id.button13);



        rcv = (RecyclerView)findViewById(R.id.rcvblocked);
        GridLayoutManager manager = new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        rcv.setLayoutManager(manager);

        FirebaseRecyclerOptions<studentinfo> options =
                new FirebaseRecyclerOptions.Builder<studentinfo>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Blocklist").orderByChild("username"), studentinfo.class)
                        .build();

        adapter=new blocklistadapter(options);
        rcv.setAdapter(adapter);






        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  studentinfo info = new studentinfo();
                String uid = edtuid.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("UserInfoWithUid")
                        .child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                       studentinfo info= snapshot.getValue(studentinfo.class);


                        AlertDialog.Builder builder = new AlertDialog.Builder(Blocklist.this);
                        builder.setTitle("Block Confirmation");
                        builder.setMessage("Are you sure you want to block this uid");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance().getReference().child("Blocklist")
                                        .child(uid)
                                        .setValue(info);
                                Toast.makeText(Blocklist.this, "Uid Blocked Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })    ;
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })        ;
                        builder.show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Blocklist.this, "Connection Lost!", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });
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