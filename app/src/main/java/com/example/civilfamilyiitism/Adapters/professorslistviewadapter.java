package com.example.civilfamilyiitism.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.civilfamilyiitism.R;
import com.example.civilfamilyiitism.studentinfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

public class professorslistviewadapter extends FirebaseRecyclerAdapter<studentinfo,professorslistviewadapter.holder> {

    Context mContext;

    public professorslistviewadapter(@NonNull FirebaseRecyclerOptions<studentinfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final holder holder, final int position, @NonNull studentinfo model) {
        holder.txv1.setText(model.getUsername());
        holder.txv2.setText(model.getDesignation());
        holder.txv3.setText(model.getPhone());
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
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "tel:"+ model.getPhone();
                mContext.startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse(s)));
            }
        });
        holder.mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+model.getEmail()));
                mContext.startActivity(Intent.createChooser(intent,"Choose Email Service"));
            }
        });

    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.professrowsetting,parent,false);
        mContext = parent.getContext();
        return new holder(view);
    }

    class holder extends RecyclerView.ViewHolder{
        TextView txv1,txv2,txv3 , txv4;
        ImageButton call ,mail;
        ImageView img;
        public holder(@NonNull View itemView) {
            super(itemView);

            txv1=(TextView)itemView.findViewById(R.id.textView32);
            txv2=(TextView)itemView.findViewById(R.id.textView33);
            txv3=(TextView)itemView.findViewById(R.id.textView34);
            txv4=(TextView)itemView.findViewById(R.id.textView35);
            img = (ImageView)itemView.findViewById(R.id.imageView39);
            call = (ImageButton)itemView.findViewById(R.id.imageButton);
            mail= (ImageButton)itemView.findViewById(R.id.imageButton2);
        }
    }
}
