package com.example.civilfamilyiitism.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.civilfamilyiitism.R;
import com.example.civilfamilyiitism.studentinfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class asceteamadapter extends FirebaseRecyclerAdapter<studentinfo,asceteamadapter.holder> {

    Context mContext;

    public asceteamadapter(@NonNull FirebaseRecyclerOptions<studentinfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull asceteamadapter.holder holder, int position, @NonNull studentinfo model) {

        holder.txv1.setText(model.getUsername());
        holder.txv2.setText(model.getDesignation());
        holder.txv3.setText(model.getYear());
        holder.txv4.setText(model.getEmail());


                Glide
                        .with(mContext)
                        .load(R.drawable.ic_baseline_fingerprint_24_2)
                        .centerCrop()
                        .into(holder.img);
                try {
                    if(model.getImgurl()!=null && !((model.getImgurl()).isEmpty())){
                        Glide
                                .with(mContext)
                                .load(model.getImgurl())
                                .centerCrop()
                                .into(holder.img);
                    }else{
                        try{
                            FirebaseStorage.getInstance().getReference().child("images")
                                    .child(model.getUid())
                                    .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    if(uri!=null){
                                        Glide
                                                .with(mContext)
                                                .load(uri.toString())
                                                .centerCrop()
                                                .into(holder.img);
                                    }
                                }
                            });
                        }
                        catch (Exception ee){

                        }
                    }

                } catch (Exception e) {

                }




                try{
                Query check = FirebaseDatabase.getInstance().getReference()
                        .child("asceteam").orderByChild("uid").equalTo(model.getUid());
                check.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            holder.add.setVisibility(View.INVISIBLE);
                            holder.minus.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                }catch (Exception e){

                }



        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference()
                        .child("UserInfoWithUid").child(model.getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                studentinfo info = snapshot.getValue(studentinfo.class);
                                FirebaseDatabase.getInstance().getReference().child("asceteam")
                                        .child(model.getUid()).setValue(info);
                                holder.add.setVisibility(View.INVISIBLE);
                                holder.minus.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("asceteam")
                        .child(model.getUid()).removeValue();
                holder.add.setVisibility(View.VISIBLE);
                holder.minus.setVisibility(View.INVISIBLE);
            }
        });

    }

    @NonNull
    @Override
    public asceteamadapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asceteamrow,parent,false);
        mContext = parent.getContext();
        return new asceteamadapter.holder(view);
    }

    public class holder extends RecyclerView.ViewHolder{

        TextView txv1 , txv2 , txv3 , txv4;
        ImageButton add , minus;
        ImageView img;
        public holder(@NonNull View itemView) {
            super(itemView);

            txv1 = (TextView) itemView.findViewById(R.id.textView32);
            txv2 = (TextView) itemView.findViewById(R.id.textView33);
            txv3 = (TextView) itemView.findViewById(R.id.textView34);
            txv4 = (TextView) itemView.findViewById(R.id.textView35);
            add = (ImageButton) itemView.findViewById(R.id.imageButton);
            img = (ImageView)itemView.findViewById(R.id.imageView39);
            minus = (ImageButton) itemView.findViewById(R.id.imageButton2);

        }
    }
}
