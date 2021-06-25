package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Stopregisteration extends AppCompatActivity {

    DatabaseReference reference;
    String access;
    Button btn;
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopregisteration);

        btn = findViewById(R.id.button6);
        edt = findViewById(R.id.editTextTextPersonName4);

        reference = FirebaseDatabase.getInstance().getReference();

    //    edt.setText(FirebaseDatabase.getInstance().getReference("Access").get);

        reference.child("Access").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    access=snapshot.getValue(String.class);
                    edt.setText(access);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Stopregisteration.this, "Connection Lost!", Toast.LENGTH_SHORT).show();
            }
        });

      btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              reference.child("Access").setValue(edt.getText().toString());
              edt.setText(edt.getText().toString());
          }
      });
    //    edt.setText(reference.child("Access").child("type").get);

    }
}