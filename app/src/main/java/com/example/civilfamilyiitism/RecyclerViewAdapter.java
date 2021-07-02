package com.example.civilfamilyiitism;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class RecyclerViewAdapter extends FirebaseRecyclerAdapter<studentinfo,RecyclerViewAdapter.holder> {

    Context mContext;

    public RecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<studentinfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final holder holder, final int position, @NonNull studentinfo model) {
        holder.txv1.setText(model.getUsername());
        holder.txv2.setText(model.getEmail());
        holder.txv3.setText(model.getPhone());

        holder.txv1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu popupMenu;
                popupMenu = new PopupMenu(mContext, v);
                popupMenu.inflate(R.menu.itemsforproffsidestudentlist);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_profile: {
                                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.txv2.getContext())
                                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                                        .setExpanded(true, 1000)
                                        .create();

                                View myview = dialogPlus.getHolderView();
                                EditText name = myview.findViewById(R.id.editTextTextPersonName3);
                                TextView email = myview.findViewById(R.id.editTextTextEmailAddress3);
                                EditText phone = myview.findViewById(R.id.editTextPhone2);
                                Button submit = myview.findViewById(R.id.button5);

                                name.setText(model.getUsername());
                                email.setText(model.getEmail());
                                phone.setText(model.getPhone());

                                dialogPlus.show();
                                submit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("username", name.getText().toString());
                                        map.put("phone", phone.getText().toString());
                                        map.put("password", model.getPassword().toString());
                                        map.put("uid", model.getUid().toString());
                                        map.put("year", model.getYear().toString());
                                        map.put("email", model.getEmail().toString());

                                        FirebaseDatabase.getInstance().getReference().child(model.getYear().toString())
                                                .child(getRef(position).getKey()).updateChildren(map)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

                                                        FirebaseDatabase.getInstance().getReference()
                                                                .child("UserInfo").child(model.getPhone().toString()).removeValue()
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                        FirebaseDatabase.getInstance().getReference()
                                                                                .child("UserInfo").child(phone.getText().toString()).setValue(map)
                                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                    @Override
                                                                                    public void onSuccess(Void unused) {
                                                                                        FirebaseDatabase.getInstance().getReference()
                                                                                                .child("UserInfoWithUid").child(model.getUid().toString()).updateChildren(map);

                                                                                        dialogPlus.dismiss();
                                                                                    }
                                                                                });
                                                                    }
                                                                });

                                                    }
                                                });
                                    }
                                });
                                //  dialogPlus.show();
                            }
                            return true;


                            case R.id.delete_profile: {
                                AlertDialog.Builder builder = new AlertDialog.Builder(holder.txv2.getContext());
                                builder.setTitle("Delete Panel");
                                builder.setMessage("Are You Sure...?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("UserInfo").child(model.getPhone().toString()).removeValue()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        FirebaseDatabase.getInstance().getReference()
                                                                .child("UserInfoWithUid").child(model.getUid().toString()).removeValue()
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                        FirebaseDatabase.getInstance().getReference()
                                                                                .child(model.getYear().toString()).child(model.getUid().toString()).removeValue();
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder.show();
                            }
                            return true;

                            case R.id.block:

                                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                                alert.setTitle("Block");
                                alert.setMessage("Are you sure you want to block this user");
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String uid = model.getUid();
                                        FirebaseDatabase.getInstance().getReference().child("UserInfoWithUid")
                                                .child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                studentinfo info = snapshot.getValue(studentinfo.class);


                                                FirebaseDatabase.getInstance().getReference()
                                                        .child("Blocklist").child(uid)
                                                        .setValue(info);
                                                Toast.makeText(mContext, "Blocked Successfuly!", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Toast.makeText(mContext, "Connection lost!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alert.show();

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        mContext = parent.getContext();
        return new holder(view);
    }

    class holder extends RecyclerView.ViewHolder{
        TextView txv1,txv2,txv3;
        public holder(@NonNull View itemView) {
            super(itemView);

            txv1=(TextView)itemView.findViewById(R.id.textView7);
            txv2=(TextView)itemView.findViewById(R.id.textView6);
            txv3=(TextView)itemView.findViewById(R.id.textView5);
    }
}
}
