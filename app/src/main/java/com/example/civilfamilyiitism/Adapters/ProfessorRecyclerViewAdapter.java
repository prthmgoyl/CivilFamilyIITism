package com.example.civilfamilyiitism.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.civilfamilyiitism.studentinfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

public class ProfessorRecyclerViewAdapter extends FirebaseRecyclerAdapter<studentinfo, ProfessorRecyclerViewAdapter.holder> {

    Context mContext;

    public ProfessorRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<studentinfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final holder holder, final int position, @NonNull studentinfo model) {
        holder.txv1.setText(model.getUsername().toUpperCase());
        holder.txv2.setText(model.getDesignation().toUpperCase());
   //     holder.txv3.setText(model.getPhone());

        try {

            Glide
                    .with(mContext)
                    .load(R.drawable.ic_baseline_fingerprint_24)
                    .centerCrop()
                    .into(holder.img);


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

        } catch (Exception e) {
        }
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "tel:"+ model.getPhone();
             //   mContext
                //Intent intent = new Intent();
             //   Intent intent = new Intent(Intent.ACTION_DIAL);
               // intent.setData(Uri.parse(s));
                mContext.startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse(s)));

            }
        });
        holder.mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //  Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:"+model.getEmail()));
            //   intent.putExtra(Intent.EXTRA_EMAIL,model.getEmail());
            // intent.setType("message/rfc822");
             //  mContext.startActivity(Intent.createChooser(intent,"Choose Email Service"));

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+model.getEmail()));
                mContext.startActivity(Intent.createChooser(intent,"Choose Email Service"));

            }
        });


    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.professorrow,parent,false);
        mContext = parent.getContext();
        return new holder(view);
    }

    class holder extends RecyclerView.ViewHolder{
        TextView txv1,txv2;
        ImageView call , mail , img;
     //   ,txv2,txv3
        public holder(@NonNull View itemView) {
            super(itemView);

            //    itemView.setOnClickListener(this);

            txv1=(TextView)itemView.findViewById(R.id.textView13);
            call = (ImageView)itemView.findViewById(R.id.imageView22);
            mail = (ImageView)itemView.findViewById(R.id.imageView23);
            txv2 = (TextView)itemView.findViewById(R.id.textView19);
            img = (ImageView)itemView.findViewById(R.id.imageView21);

       //     txv2=(TextView)itemView.findViewById(R.id.textView11);
      //      txv3=(TextView)itemView.findViewById(R.id.textView12);

        }
    }

}
