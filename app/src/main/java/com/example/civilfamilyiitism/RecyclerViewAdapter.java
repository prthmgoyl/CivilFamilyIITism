package com.example.civilfamilyiitism;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class RecyclerViewAdapter extends FirebaseRecyclerAdapter<studentinfo,RecyclerViewAdapter.holder> {


    public RecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<studentinfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final holder holder, final int position, @NonNull studentinfo model) {
        holder.txv1.setText(model.getUsername());
        holder.txv2.setText(model.getEmail());
        holder.txv3.setText(model.getPhone());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(holder.txv2.getContext());
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
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.txv2.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,1000)
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
                        Map<String,Object> map = new HashMap<>();
                        map.put("username",name.getText().toString());
                        map.put("phone",phone.getText().toString());
                        map.put("password",model.getPassword().toString());
                        map.put("uid",model.getUid().toString());
                        map.put("year",model.getYear().toString());
                        map.put("email",model.getEmail().toString());

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
                                                                }) ;
                                                    }
                                                });

                                    }
                                });
                    }
                });
              //  dialogPlus.show();
            }
        });
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new holder(view);
    }

    class holder extends RecyclerView.ViewHolder{
        TextView txv1,txv2,txv3;
        ImageView edit, delete;

        public holder(@NonNull View itemView) {
            super(itemView);

        //    itemView.setOnClickListener(this);

            txv1=(TextView)itemView.findViewById(R.id.textView7);
            txv2=(TextView)itemView.findViewById(R.id.textView6);
            txv3=(TextView)itemView.findViewById(R.id.textView5);
            delete = (ImageView)itemView.findViewById(R.id.imageView19);
            edit = (ImageView)itemView.findViewById(R.id.imageView18);

        }



    }

}
