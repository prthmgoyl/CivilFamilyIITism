package com.example.civilfamilyiitism.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.civilfamilyiitism.R;
import com.example.civilfamilyiitism.studentinfoincludechangeto;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class changeclassadapter extends FirebaseRecyclerAdapter<studentinfoincludechangeto, changeclassadapter.holder> {

    Context mContext;

    public changeclassadapter(@NonNull FirebaseRecyclerOptions<studentinfoincludechangeto> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull changeclassadapter.holder holder, int position, @NonNull studentinfoincludechangeto model) {
        holder.txv1.setText(model.getYear());
        holder.txv2.setText(model.getChangeto());
        holder.txv3.setText(model.getUsername());
        holder.txv4.setText(model.getEmail());
        holder.txv5.setText(model.getUid());

        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Approved", Toast.LENGTH_SHORT).show();
            }
        });

        holder.deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Deny", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @NonNull
    @Override
    public com.example.civilfamilyiitism.Adapters.changeclassadapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classrequestrow,parent,false);
        mContext = parent.getContext();
        return new holder(view);
    }

    class holder extends RecyclerView.ViewHolder{
        TextView txv1,txv2,txv3 , txv4,txv5;
        Button approve, deny;
        public holder(@NonNull View itemView) {
            super(itemView);

            txv1=(TextView)itemView.findViewById(R.id.textView41);
            txv2=(TextView)itemView.findViewById(R.id.textView40);
            txv3=(TextView)itemView.findViewById(R.id.textView39);
            txv4=(TextView)itemView.findViewById(R.id.textView38);
            txv5=(TextView)itemView.findViewById(R.id.textView36);
            approve= (Button) itemView.findViewById(R.id.button24);
            deny= (Button) itemView.findViewById(R.id.button25);
        }
    }
}


