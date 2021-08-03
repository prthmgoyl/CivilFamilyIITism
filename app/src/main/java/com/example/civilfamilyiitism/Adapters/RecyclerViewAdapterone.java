package com.example.civilfamilyiitism.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.civilfamilyiitism.R;
import com.example.civilfamilyiitism.personalinfo;
import com.example.civilfamilyiitism.studentinfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class RecyclerViewAdapterone extends FirebaseRecyclerAdapter<studentinfo, RecyclerViewAdapterone.holder> {
    personalinfo personal;
    Context mContext;
    public RecyclerViewAdapterone(@NonNull FirebaseRecyclerOptions<studentinfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final holder holder, final int position, @NonNull studentinfo model) {
        holder.txv1.setText(model.getUsername());
        holder.txv2.setText(model.getDesignation());

        String year = model.getYear();
        if(year.equalsIgnoreCase("zero")){
            holder.txv3.setText(model.getDesignation().toUpperCase());
        }
        else if(year.equalsIgnoreCase("first")){
            holder.txv3.setText("First Year");
        }
        else if(year.equalsIgnoreCase("second")){
            holder.txv3.setText("Second Year");
        }
        else if(year.equalsIgnoreCase("third")){
            holder.txv3.setText("Third Year");
        }
        else if(year.equalsIgnoreCase("fourth")){
            holder.txv3.setText("Fourth Year");
        }
        else{
            holder.txv3.setText(year);
        }

        Glide
                .with(mContext)
                .load(R.drawable.ic_baseline_fingerprint_24)
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
       // holder.txv4.setText(model.getDesignation());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.infodialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ImageView image = (ImageView) dialog.findViewById(R.id.imageView35);
                 TextView txv2 = (TextView)dialog.findViewById(R.id.editTextTextPersonName14);
                 TextView txv3 = (TextView)dialog.findViewById(R.id.editTextTextPersonName15);
                 TextView txv4 = (TextView)dialog.findViewById(R.id.editTextTextEmailAddress5);
                 TextView txv5 = (TextView)dialog.findViewById(R.id.editTextPhone4);
                TextView txv6 = (TextView)dialog.findViewById(R.id.textView25);
                TextView more = (TextView)dialog.findViewById(R.id.textView30);
                TextView back = (TextView)dialog.findViewById(R.id.textView31);


                txv2.setText(model.getUsername());
                txv3.setText(model.getDesignation());
                txv4.setText(model.getEmail());
                txv5.setText(model.getPhone());

                String year = model.getYear();
                if(year.equalsIgnoreCase("zero")){
                    txv6.setText(model.getDesignation().toUpperCase());
                }
                else if(year.equalsIgnoreCase("first")){
                    txv6.setText("First Year");
                }
                else if(year.equalsIgnoreCase("second")){
                    txv6.setText("Second Year");
                }
                else if(year.equalsIgnoreCase("third")){
                    txv6.setText("Third Year");
                }
                else if(year.equalsIgnoreCase("fourth")){
                    txv6.setText("Fourth Year");
                }
                else{
                    txv6.setText(year);
                }


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
                                        .into(image);
                            }
                        }
                    });
                }catch (Exception e){

                }
                try{
                    FirebaseDatabase.getInstance().getReference()
                            .child("personalinfo").child(model.getUid())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                         personal = snapshot.getValue(personalinfo.class);
                                       if((personal.getVisibility()).equals("public")){

                                           more.setVisibility(View.VISIBLE);
                                       }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
                catch(Exception e){

                }

                dialog.show();
                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        more.setVisibility(View.INVISIBLE);
                        back.setVisibility(View.VISIBLE);

                        txv2.setText(personal.getAlternatephone());
                        txv3.setText(personal.getPresentaddress());
                        txv4.setText(personal.getPermanentaddress());
                        txv5.setText(personal.getDob());
                        txv6.setText("");

                    }
                });
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        more.setVisibility(View.VISIBLE);
                        back.setVisibility(View.INVISIBLE);

                        txv2.setText(model.getUsername());
                        txv3.setText(model.getDesignation());
                        txv4.setText(model.getEmail());
                        txv5.setText(model.getPhone());
                        txv6.setText(model.getYear());
                    }
                });
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
        ImageView img;

        public holder(@NonNull View itemView) {
            super(itemView);

        //    itemView.setOnClickListener(this);

            txv1=(TextView)itemView.findViewById(R.id.textView7);
            txv2=(TextView)itemView.findViewById(R.id.textView6);
            txv3=(TextView)itemView.findViewById(R.id.textView5);
            img = (ImageView)itemView.findViewById(R.id.imageView24);
        //    txv4 = (TextView)itemView.findViewById(R.id.textView19);

        }
    }

}
