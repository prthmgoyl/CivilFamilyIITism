package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Settingpage extends AppCompatActivity {

    Switch autofill;
    ImageView autofillimg;
    String read = "",line="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingpage);

        autofill = (Switch)findViewById(R.id.switch1);
        autofillimg = (ImageView)findViewById(R.id.imageView37);


        try{
            if(ContextCompat.checkSelfPermission(Settingpage.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                 checkautofilldata();
            }
            else{
                ActivityCompat.requestPermissions(Settingpage.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
            }
        }
        catch (Exception e){

        }

        autofillimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autofill.setError("Turning this on here and giving required permission while login you will be able to login with one tap");
            }
        });
        autofill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   if(ContextCompat.checkSelfPermission(Settingpage.this,
                           Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                       saveautofilldata();
                    }
                   else{
                            ActivityCompat.requestPermissions(Settingpage.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                   }
                }
                else{


                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
              saveautofilldata();
        }

        else if(requestCode==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            try {
                checkautofilldata();
            }  catch (Exception e) {
                autofill.setChecked(false);
            }
        }
        else{
            Toast.makeText(this, "Please Allow Permission to continue with autofill", Toast.LENGTH_SHORT).show();
            autofill.setChecked(false);
        }
    }

    private void checkautofilldata() throws Exception {
        File file = new File(Environment.getExternalStorageDirectory(),"autofill.txt");
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader buff = new BufferedReader(isr);
        while ((line= buff.readLine())!=null){
            read = read+line;
        }

       if(read.equals(FirebaseAuth.getInstance().getUid())){
           autofill.setChecked(true);
       }
       else{
           autofill.setChecked(false);
       }

    }

    private void saveautofilldata() {
        File file = new File(Environment.getExternalStorageDirectory(),"autofill.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(FirebaseAuth.getInstance().getUid().getBytes());
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            autofill.setChecked(false);
        } catch (IOException e) {
            autofill.setChecked(false);
        }
        catch (Exception e){
            autofill.setChecked(false);
        }
    }


}