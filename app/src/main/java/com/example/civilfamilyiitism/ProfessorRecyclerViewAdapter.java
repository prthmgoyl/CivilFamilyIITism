package com.example.civilfamilyiitism;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ProfessorRecyclerViewAdapter extends FirebaseRecyclerAdapter<studentinfo, ProfessorRecyclerViewAdapter.holder> {


    public ProfessorRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<studentinfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final holder holder, final int position, @NonNull studentinfo model) {
        holder.txv1.setText(model.getUsername());
 //       holder.txv2.setText(model.getEmail());
   //     holder.txv3.setText(model.getPhone());
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.professorrow,parent,false);
        return new holder(view);
    }

    class holder extends RecyclerView.ViewHolder{
        TextView txv1;
     //   ,txv2,txv3
        public holder(@NonNull View itemView) {
            super(itemView);

            //    itemView.setOnClickListener(this);

            txv1=(TextView)itemView.findViewById(R.id.textView13);
       //     txv2=(TextView)itemView.findViewById(R.id.textView11);
      //      txv3=(TextView)itemView.findViewById(R.id.textView12);

        }
    }

}
