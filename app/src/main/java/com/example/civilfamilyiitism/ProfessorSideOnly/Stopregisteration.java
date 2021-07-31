package com.example.civilfamilyiitism.ProfessorSideOnly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.civilfamilyiitism.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Stopregisteration extends AppCompatActivity {

    DatabaseReference reference;
    String access  , changeclass , Securitycode;
    Button btn;
    EditText edt;
    int i=0,j=0;
    Switch accessswitch ,changeclassswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopregisteration);

        btn = findViewById(R.id.button6);
        edt = findViewById(R.id.editTextTextPersonName4);
        accessswitch = (Switch)findViewById(R.id.switch3);
        changeclassswitch = (Switch)findViewById(R.id.switch4);
        reference = FirebaseDatabase.getInstance().getReference();

        try{
            reference.child("permissions").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        try{
                            access = snapshot.child("Access").getValue(String.class);
                            if(access.equalsIgnoreCase("Deny")){
                                i=1;
                                accessswitch.setChecked(true);
                            }
                            else{
                                accessswitch.setChecked(false);
                            }
                        }
                        catch (Exception a){
                            accessswitch.setChecked(false);
                        }
                        try{
                            changeclass = snapshot.child("Changeclass").getValue(String.class);
                            if(changeclass.equalsIgnoreCase("Allow")){
                                j=1;
                                changeclassswitch.setChecked(true);
                            }
                            else {
                               changeclassswitch.setChecked(false);
                            }
                        }
                        catch (Exception b){
                            changeclassswitch.setChecked(false);
                        }
                        try{
                            Securitycode = snapshot.child("Securitycode").getValue(String.class);
                            edt.setText(Securitycode);
                        }
                        catch (Exception c){
                            Toast.makeText(Stopregisteration.this, "Unable to get Security code", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Stopregisteration.this, "Connection Lost!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e){
            
        }

      accessswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if (i == 1) {
                 i=0;
              } else {
                  if (isChecked) {
                      AlertDialog.Builder dialog = new AlertDialog.Builder(Stopregisteration.this);
                      dialog.setTitle("Deny Access");
                      dialog.setMessage("Are you sure you want to deny access to new registerations..?");
                      dialog.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              try {
                                  reference.child("permissions").child("Access").setValue("Deny");
                                  accessswitch.setChecked(true);
                              } catch (Exception e) {
                                  accessswitch.setChecked(false);
                                  Toast.makeText(Stopregisteration.this, "Error!!!", Toast.LENGTH_SHORT).show();
                              }
                          }
                      });
                      dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              accessswitch.setChecked(false);
                          }
                      });
                      dialog.show();
                  } else {
                      try {
                          reference.child("permissions").child("Access").setValue("Allow");
                          accessswitch.setChecked(false);
                      } catch (Exception e) {
                          accessswitch.setChecked(true);
                          Toast.makeText(Stopregisteration.this, "Error!!!", Toast.LENGTH_SHORT).show();
                      }

                  }
              }
          }
      });


      changeclassswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (j == 1) {
                    j=0;
                }
                else{
                    if (isChecked) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Stopregisteration.this);
                        dialog.setTitle("Change Class Access");
                        dialog.setMessage("Are you sure you want to ALLOW access to STUDENTS to change their class..?");
                        dialog.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    reference.child("permissions").child("Changeclass").setValue("Allow");
                                    changeclassswitch.setChecked(true);
                                } catch (Exception e) {
                                    changeclassswitch.setChecked(false);
                                    Toast.makeText(Stopregisteration.this, "Error!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                changeclassswitch.setChecked(false);
                            }
                        });
                        dialog.show();
                    } else {
                        try {
                            reference.child("permissions").child("Changeclass").setValue("Deny");
                            changeclassswitch.setChecked(false);
                        } catch (Exception e) {
                            changeclassswitch.setChecked(true);
                            Toast.makeText(Stopregisteration.this, "Error!!!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });


      btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              reference.child("permissions").child("Securitycode").setValue(edt.getText().toString());
              edt.setText(edt.getText().toString());
              Toast.makeText(Stopregisteration.this, "Code!! "+edt.getText().toString(), Toast.LENGTH_SHORT).show();
          }
      });
    }
}