package com.example.civilfamilyiitism;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class Professormainpage extends AppCompatActivity {

    private ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9,img10 , img11,img12;
    String check = "null",uid;
    Uri filepath;
    ImageView userimage;
    TextView name ,seeall;
    RecyclerView rcv;
    ProfessorRecyclerViewAdapter adapter;
    ImageView barprofile ,barsearch,barsetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professormainpage);

        img1 = findViewById(R.id.imageView13);
        img2 = findViewById(R.id.imageView12);
        img3 = findViewById(R.id.imageView15);
        img4 = findViewById(R.id.imageView14);
        img5 = findViewById(R.id.imageView9);
        img6 = findViewById(R.id.imageView);
        img7 = findViewById(R.id.imageView11);
        img8 = findViewById(R.id.imageView10);
        img9 = findViewById(R.id.imageView17);
        img10 = findViewById(R.id.imageView16);
        img11 = findViewById(R.id.imageView29);
        img12 = findViewById(R.id.imageView30);
        seeall = (TextView)findViewById(R.id.textView37);
       userimage = (ImageView)findViewById(R.id.imageView18);
       name = (TextView)findViewById(R.id.textView11);
       barprofile = (ImageView)findViewById(R.id.imageView25);
       barsearch = (ImageView)findViewById(R.id.imageView27);
       barsetting = (ImageView)findViewById(R.id.imageView19);
       uid = FirebaseAuth.getInstance().getUid();

      try {
          rcv = (RecyclerView) findViewById(R.id.recyclerViewproff);
          rcv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
          FirebaseRecyclerOptions<studentinfo> options =
                  new FirebaseRecyclerOptions.Builder<studentinfo>()
                          .setQuery(FirebaseDatabase.getInstance().getReference().child("zero").orderByChild("username"), studentinfo.class)
                          .build();

          adapter = new ProfessorRecyclerViewAdapter(options);
          rcv.setAdapter(adapter);
          adapter.startListening();

      }
      catch (Exception e){
          Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
      }

        try{
            FirebaseDatabase.getInstance().getReference().child("UserInfoWithUid")
                    .child(uid)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            studentinfo info = snapshot.getValue(studentinfo.class);
                            name.setText("Welcome! "+info.getUsername().toUpperCase());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        userimage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               PopupMenu popupMenu = new PopupMenu(Professormainpage.this,v);
               popupMenu.inflate(R.menu.selectimagemenu);
               popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem item) {
                       switch(item.getItemId()){
                           case R.id.editprofile:
                               startActivity(new Intent(Professormainpage.this,EditMyProfilePage.class));
                               return true;
                           case R.id.changeimage:
                               Intent intent = new Intent(Intent.ACTION_PICK);
                               intent.setType("image/");
                               startActivityForResult(Intent.createChooser(intent,"SelectImage"),1);
                               return true;
                           case R.id.signout:
                               AlertDialog.Builder builder = new AlertDialog.Builder(Professormainpage.this);
                               builder.setTitle("SignOut");
                               builder.setMessage("Are you sure you want to sign out...?");
                               builder.setPositiveButton("SignOut", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       FirebaseAuth.getInstance().signOut();
                                       startActivity(new Intent(Professormainpage.this,MainActivity.class));
                                       finish();
                                   }
                               });
                               builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {

                                   }
                               });
                               builder.show();
                               return true;
                           default:
                               return false;
                       }
                   }
               });
               popupMenu.show();

           }
       });



        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "fourth";
                Intent intent = new Intent(Professormainpage.this,Studentlistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "third";
                Intent intent = new Intent(Professormainpage.this,Studentlistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "second";
                Intent intent = new Intent(Professormainpage.this,Studentlistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "first";
                Intent intent = new Intent(Professormainpage.this,Studentlistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivity(new Intent(Professormainpage.this,UploadPdfpage.class));
            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      startActivity(new Intent(Professormainpage.this,NoticePageProff.class));
            }
        });
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Professormainpage.this, "ASCE PAGE!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Professormainpage.this,Ascepage.class));
            }
        });
        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(Professormainpage.this,ProfessorAddStudent.class));
            }
        });
        img9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Professormainpage.this,Blocklist.class));
                Toast.makeText(Professormainpage.this, "img9", Toast.LENGTH_SHORT).show();
            }
        });
        img10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(Professormainpage.this, "img10", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Professormainpage.this,Stopregisteration.class));
            }
        });
        img11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Professormainpage.this, "img11", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Professormainpage.this,Updateclass.class));
            }
        });
        img12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Professormainpage.this, "img12", Toast.LENGTH_SHORT).show();
            }
        });
        barprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Professormainpage.this,EditMyProfilePage.class));
            }
        });
        barsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Professormainpage.this,searchsplashscreen.class));
            }
        });
        barsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Professormainpage.this,Settingpage.class));
            }
        });
        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Professormainpage.this,Professorslistview.class));
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            filepath = data.getData();
            try {

             /*   File actualImage = new File(filepath.getPath());
                Bitmap compressedImage = new Compressor(this)
                        .setMaxWidth(250)
                        .setMaxHeight(250)
                        .setQuality(40)
                        .compressToBitmap(actualImage);


              *//*Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                byte[] userdata = baos.toByteArray();
*/


                FirebaseStorage.getInstance().getReference().child("images")
                        .child(uid)
                        .putFile(filepath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                FirebaseStorage.getInstance().getReference().child("images")
                                        .child(uid)
                                        .getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Glide
                                                        .with(getApplicationContext())
                                                        .load(uri.toString())
                                                        .centerCrop()
                                                        .into(userimage);
                                            }
                                        });


                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    }
                });


            } catch (Exception e) {

            }
        }
    }
}