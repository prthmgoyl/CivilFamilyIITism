package com.example.civilfamilyiitism.StudentSideOnly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.civilfamilyiitism.Adapters.ProfessorRecyclerViewAdapter;
import com.example.civilfamilyiitism.EditMyProfilePage;
import com.example.civilfamilyiitism.MainActivity;
import com.example.civilfamilyiitism.NoticePages.Noticepagestudent;
import com.example.civilfamilyiitism.Professorslistview;
import com.example.civilfamilyiitism.R;
import com.example.civilfamilyiitism.Settingpage;
import com.example.civilfamilyiitism.searchsplashscreen;
import com.example.civilfamilyiitism.studentinfo;
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

public class Mainpage extends AppCompatActivity {

    private ImageView img1,img2,img3,img4,img5,img6,img7,img8;
    String check = "null";
    String check2 = "null";
    Uri filepath;
    RecyclerView rcv;
    ProfessorRecyclerViewAdapter adapter;
    String myuid;
    String year = "null";
    ImageView userimage;
    TextView name , seeall;
    ImageView barprofile ,barsearch,barsetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        img1=findViewById(R.id.imageView1);
        img2=findViewById(R.id.imageView2);
        img3=findViewById(R.id.imageView3);
        img4=findViewById(R.id.imageView4);
        img5=findViewById(R.id.imageView5);
        img6=findViewById(R.id.imageView6);
        img7=findViewById(R.id.imageView7);
        img8=findViewById(R.id.imageView8);
        seeall = (TextView)findViewById(R.id.textView37);
        userimage = (ImageView)findViewById(R.id.imageView18);
        name = (TextView)findViewById(R.id.textView11);

        myuid = FirebaseAuth.getInstance().getUid();
        barprofile = (ImageView)findViewById(R.id.imageView25);
        barsearch = (ImageView)findViewById(R.id.imageView27);
        barsetting = (ImageView)findViewById(R.id.imageView19);


        try {
            rcv = (RecyclerView) findViewById(R.id.recyclerViewproffstudentside);
            rcv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            FirebaseRecyclerOptions<studentinfo> options =
                    new FirebaseRecyclerOptions.Builder<studentinfo>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("zero"), studentinfo.class)
                            .build();

            adapter = new ProfessorRecyclerViewAdapter(options);
            rcv.setAdapter(adapter);
            adapter.startListening();

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }



      try {
          FirebaseDatabase.getInstance().getReference().child("UserInfoWithUid").child(myuid)
                  .addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot snapshot) {

                          name.setText("Welcome! " + snapshot.child("username").getValue(String.class).toUpperCase());

                          year = snapshot.child("year").getValue(String.class);
                          // Toast.makeText(Noticepagestudent.this, "this"+year, Toast.LENGTH_SHORT).show();
                          if (year.equals("first")) {
                              check2 = "first";
                          } else if (year.equals("second")) {
                              check2 = "second";
                          } else if (year.equals("third")) {
                              check2 = "third";
                          } else if (year.equals("fourth")) {
                              check2 = "fourth";
                          } else {
                              //   Toast.makeText(Noticepagestudent.this, "Sorry!We are unable to find your account", Toast.LENGTH_SHORT).show();
                          }
                      }


                      @Override
                      public void onCancelled(@NonNull DatabaseError error) {
                          //  Toast.makeText(Noticepagestudent.this, "Sorry!Connection lost", Toast.LENGTH_SHORT).show();
                      }
                  });
      }
      catch (Exception e){
          Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
      }

        try {
            FirebaseStorage.getInstance().getReference().child("images")
                    .child(myuid)
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

                PopupMenu popupMenu = new PopupMenu(Mainpage.this,v);
                popupMenu.inflate(R.menu.selectimagemenu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.editprofile:
                                startActivity(new Intent(Mainpage.this, EditMyProfilePage.class));
                                return true;
                            case R.id.changeimage:
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/");
                                startActivityForResult(Intent.createChooser(intent,"SelectImage"),1);
                                return true;
                            case R.id.signout:
                                AlertDialog.Builder builder = new AlertDialog.Builder(Mainpage.this);
                                builder.setTitle("SignOut");
                                builder.setMessage("Are you sure you want to sign out...?");
                                builder.setPositiveButton("SignOut", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseAuth.getInstance().signOut();
                                        startActivity(new Intent(Mainpage.this, MainActivity.class));
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
            public void onClick(View v){
                        check = "fourth";
                        Toast.makeText(Mainpage.this, "img1"+check, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Mainpage.this, Friendslistviewer.class);
                        intent.putExtra("keycheck",check);
                        startActivity(intent);
                    }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "third";
                Toast.makeText(Mainpage.this, "img2"+check, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Mainpage.this,Friendslistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);

            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "second";
                Toast.makeText(Mainpage.this, "img3"+check, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Mainpage.this,Friendslistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);

            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "first";
                Toast.makeText(Mainpage.this, "img4"+check, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Mainpage.this,Friendslistviewer.class);
                intent.putExtra("keycheck",check);
                startActivity(intent);

            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(Mainpage.this, "img5", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Mainpage.this, PdfPageStudentSide.class);
                intent.putExtra("year",year);
                startActivity(intent);

            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mainpage.this, AscepageStudentSide.class));
            }
        });
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Mainpage.this, Noticepagestudent.class);
                intent.putExtra("check",check2);
                startActivity(intent);

               // startActivity(new Intent(Mainpage.this,Noticepagestudent.class));
                Toast.makeText(Mainpage.this, "img7", Toast.LENGTH_SHORT).show();

            }
        });
        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Mainpage.this, "img8", Toast.LENGTH_SHORT).show();

            }
        });
        barprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mainpage.this,EditMyProfilePage.class));
            }
        });
        barprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mainpage.this,EditMyProfilePage.class));
            }
        });
        barsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mainpage.this, searchsplashscreen.class));
            }
        });
        barsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mainpage.this, Settingpage.class));
            }
        });
        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mainpage.this, Professorslistview.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            filepath = data.getData();
            try {
                FirebaseStorage.getInstance().getReference().child("images")
                        .child(FirebaseAuth.getInstance().getUid())
                        .putFile(filepath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                FirebaseStorage.getInstance().getReference().child("images")
                                        .child(myuid)
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