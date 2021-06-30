package com.example.civilfamilyiitism;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class noticeadapter extends FirebaseRecyclerAdapter<noticemodel, noticeadapter.holder> {

    Context mContext;
    String name;

    public noticeadapter(@NonNull FirebaseRecyclerOptions<noticemodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final holder holder, final int position, @NonNull noticemodel model) {

        holder.notice.setText(model.getMessage());
        holder.time.setText(model.getTime());
        holder.sender.setText(model.getUsername().toUpperCase());

    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noticerowproffside,parent,false);
        mContext = parent.getContext();
        return new holder(view);
    }

    class holder extends RecyclerView.ViewHolder{

        TextView notice , sender , time;

        public holder(@NonNull View itemView) {
            super(itemView);

            notice = (TextView)itemView.findViewById(R.id.textView20);
            time = (TextView)itemView.findViewById(R.id.textView22);
            sender = (TextView)itemView.findViewById(R.id.textView23);
        }
    }

}

