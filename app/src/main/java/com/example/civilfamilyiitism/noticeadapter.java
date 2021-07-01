package com.example.civilfamilyiitism;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class noticeadapter extends FirebaseRecyclerAdapter<noticemodel, noticeadapter.holder> {

    Context mContext;
    String name;

    public noticeadapter(@NonNull FirebaseRecyclerOptions<noticemodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final holder holder, final int position, @NonNull noticemodel model) {

        holder.notice.setText(model.getMessage());
        holder.time.setText(model.getTime());
        holder.sender.setText(model.getUsername().toUpperCase());

        holder.notice.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu popupMenu = new PopupMenu(mContext,v);
                        popupMenu.inflate(R.menu.itemsforproffsidestudentlist);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case R.id.edit_profile:

                                        DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                                                .setExpanded(true,600)
                                                .setContentHolder(new ViewHolder(R.layout.noticeeditdesign))
                                                .setContentBackgroundResource(R.drawable.noticedialogbg)
                                                .create();
                                        dialogPlus.show();

                                        View view = dialogPlus.getHolderView();
                                        Button btn = view.findViewById(R.id.button12);
                                        EditText editmessage = view.findViewById(R.id.editTextTextMultiLine2);
                                        editmessage.setText(model.getMessage());

                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notice");

                                        btn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String timeone = model.getTimeone();
                                                String to = model.getTo();
                                                String year = model.getYear();
                                                String message = editmessage.getText().toString();
                                                Map<String,Object> map = new HashMap<>();
                                                map.put("message", message);
                                                map.put("time" , model.getTime());
                                                map.put("timeone",timeone);
                                                map.put("uid",model.getUid());
                                                map.put("username", model.getUsername());
                                                map.put("year",year);
                                                map.put("to",to);

                                                reference.child("all").child(timeone).updateChildren(map);
                                                if(to.equals("all")){
                                                    reference.child("first").child(timeone).updateChildren(map);
                                                    reference.child("second").child(timeone).updateChildren(map);
                                                    reference.child("third").child(timeone).updateChildren(map);
                                                    reference.child("fourth").child(timeone).updateChildren(map);
                                                }
                                                else{
                                                    reference.child(year).child(timeone).updateChildren(map);
                                                }
                                                Toast.makeText(mContext, "Edited Successfully!", Toast.LENGTH_SHORT).show();
                                                dialogPlus.dismiss();
                                            }
                                        });



                                        return true;
                                    case R.id.delete_profile:
                                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                        builder.setTitle("Delete");
                                        builder.setMessage("Are you sure you want to delete this notice..");
                                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String timeone = model.getTimeone();
                                                String to = model.getTo();
                                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notice");
                                               reference.child("all").child(timeone).removeValue();

                                               if(to.equals("all")){
                                                   reference.child("first").child(timeone).removeValue();
                                                   reference.child("second").child(timeone).removeValue();
                                                   reference.child("third").child(timeone).removeValue();
                                                   reference.child("fourth").child(timeone).removeValue();
                                               }
                                               else{
                                                   FirebaseDatabase.getInstance().getReference("Notice").child(model.getYear())
                                                           .child(timeone).removeValue();
                                               }
                                                Toast.makeText(mContext, "Deleted Successfully!", Toast.LENGTH_SHORT).show();


                                  //              FirebaseDatabase.getInstance().getReference("Notice").child("all")

                                            }
                                        });
                                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }) ;
                                        builder.show();
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
                        popupMenu.show();
                        return true;
            }
        });

    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noticerowproffside,parent,false);
        mContext = parent.getContext();
        return new holder(view);
    }

    class holder extends RecyclerView.ViewHolder{

        TextView notice , sender , time;


        public holder(@NonNull View itemView) {
            super(itemView);

            notice = (TextView)itemView.findViewById(R.id.textView20);
            time = (TextView)itemView.findViewById(R.id.textView22);
            sender = (TextView)itemView.findViewById(R.id.textView23);
        }
    }

}

