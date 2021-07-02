package com.example.civilfamilyiitism;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class Blocklist extends AppCompatActivity {

    EditText edtuid;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocklist);

        edtuid = (EditText)findViewById(R.id.editTextTextPersonName10);
        btn = (Button)findViewById(R.id.button13);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Blocklist.this);
                builder.setTitle("Block Confirmation");
                builder.setMessage("Are you sure you want to block this uid");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Blocklist")
                                .child(String.valueOf(System.currentTimeMillis()))
                                .child("uid").setValue(edtuid.getText().toString());
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
        });
    }
}