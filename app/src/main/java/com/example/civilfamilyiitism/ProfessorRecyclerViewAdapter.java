package com.example.civilfamilyiitism;

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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ProfessorRecyclerViewAdapter extends FirebaseRecyclerAdapter<studentinfo, ProfessorRecyclerViewAdapter.holder> {

    Context mContext;

    public ProfessorRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<studentinfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final holder holder, final int position, @NonNull studentinfo model) {
        holder.txv1.setText(model.getUsername());
 //       holder.txv2.setText(model.getEmail());
   //     holder.txv3.setText(model.getPhone());


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
        TextView txv1;
        ImageView call , mail;
     //   ,txv2,txv3
        public holder(@NonNull View itemView) {
            super(itemView);

            //    itemView.setOnClickListener(this);

            txv1=(TextView)itemView.findViewById(R.id.textView13);
            call = (ImageView)itemView.findViewById(R.id.imageView22);
            mail = (ImageView)itemView.findViewById(R.id.imageView23);
       //     txv2=(TextView)itemView.findViewById(R.id.textView11);
      //      txv3=(TextView)itemView.findViewById(R.id.textView12);

        }
    }

}
