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

import com.example.civilfamilyiitism.R;
import com.example.civilfamilyiitism.ProfessorSideOnly.putpdf;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PdfRecyclerViewAdapter extends FirebaseRecyclerAdapter<putpdf,PdfRecyclerViewAdapter.holder> {


    Context mContext;

    public PdfRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<putpdf> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final holder holder, final int position, @NonNull putpdf model) {
       holder.txv1.setText(model.getName());
       holder.img1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setType("application/pdf");
               intent.setData(Uri.parse(model.getUrl()));
               mContext.startActivity(intent);
           }
       });
       holder.txv1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setType("application/pdf");
               intent.setData(Uri.parse(model.getUrl()));
               mContext.startActivity(intent);
           }
       });

    //    holder.txv2.setText(model.getEmail());
      //  holder.txv3.setText(model.getPhone());
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdfrow,parent,false);
        mContext = parent.getContext();
        return new holder(view);
    }

    class holder extends RecyclerView.ViewHolder{
        TextView txv1;
        ImageView img1;
                //,txv2,txv3;

        public holder(@NonNull View itemView) {
            super(itemView);

      txv1=(TextView)itemView.findViewById(R.id.textView16);
      img1 = (ImageView)itemView.findViewById(R.id.imageView26);
        //    txv2=(TextView)itemView.findViewById(R.id.textView11);
       //     txv3=(TextView)itemView.findViewById(R.id.textView12);

        }
    }

}
