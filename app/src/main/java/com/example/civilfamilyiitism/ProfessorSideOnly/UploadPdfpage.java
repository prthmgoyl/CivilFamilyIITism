package com.example.civilfamilyiitism.ProfessorSideOnly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.civilfamilyiitism.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

public class UploadPdfpage extends AppCompatActivity {

    private EditText edt;
    private RadioButton rdbtn1,rdbtn2,rdbtn3,rdbtn4;
    private Button uploadbtn;
    String filename;
    String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdfpage);

        edt = (EditText)findViewById(R.id.editTextTextPersonName2);
        rdbtn1 = findViewById(R.id.radioButton9);
        rdbtn2 = findViewById(R.id.radioButton8);
        rdbtn3 = findViewById(R.id.radioButton7);
        rdbtn4 = findViewById(R.id.radioButton6);
        uploadbtn = findViewById(R.id.button3);

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rdbtn1.isChecked()){
                    year = "first";

                }
                else if(rdbtn2.isChecked()){
                    year = "second";
                }
                else if(rdbtn3.isChecked()){
                    year = "third";
                }
                else if(rdbtn4.isChecked()){
                    year = "fourth";
                }
                else{
                    year = "notselected";
                    Toast.makeText(UploadPdfpage.this, "Please Select Class", Toast.LENGTH_SHORT).show();
                }

                filename =edt.getText().toString();
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select PDF File"),1);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            uploadPDFfile(data.getData());
        }
    }

    private void uploadPDFfile(Uri data) {

        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setTitle("Uploading...");
        progressdialog.show();
    //    FirebaseStorage.getInstance().getReference().child("uploads/"+System.currentTimeMillis()+".pdf");
        FirebaseStorage.getInstance().getReference().child("uploads/"+String.valueOf(System.currentTimeMillis())+".pdf").putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete());
                            Uri url = uri.getResult();

                            putpdf putpdf = new putpdf(filename,url.toString());
                            FirebaseDatabase.getInstance().getReference().child("uploads").child(year).child(String.valueOf(System.currentTimeMillis())).setValue(putpdf);
                            Toast.makeText(UploadPdfpage.this, "Successful!", Toast.LENGTH_SHORT).show();
                            progressdialog.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                      double progress = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                      progressdialog.setMessage("Uploaded: "+(int)progress+"%");
            }
        });

    }
}