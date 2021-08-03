package com.example.civilfamilyiitism.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.civilfamilyiitism.R;
import com.example.civilfamilyiitism.studentinfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class asceteamadapter extends FirebaseRecyclerAdapter<studentinfo,asceteamadapter.holder> {

    Context mContext;

    public asceteamadapter(@NonNull FirebaseRecyclerOptions<studentinfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull asceteamadapter.holder holder, int position, @NonNull studentinfo model) {

    }

    @NonNull
    @Override
    public asceteamadapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asceteamrow,parent,false);
        mContext = parent.getContext();
        return new asceteamadapter.holder(view);
    }

    public class holder extends RecyclerView.ViewHolder{
        public holder(@NonNull View itemView) {

            super(itemView);
        }
    }
}
