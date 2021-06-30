package com.example.civilfamilyiitism;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

    public class noticeadapterstudentside extends FirebaseRecyclerAdapter<noticemodel, noticeadapterstudentside.holder> {

//    Context mContext;

        public noticeadapterstudentside(@NonNull FirebaseRecyclerOptions<noticemodel> options) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noticerowstudentside,parent,false);
            //     mContext = parent.getContext();
            return new holder(view);
        }

        class holder extends RecyclerView.ViewHolder{

            TextView notice , time , sender;
            public holder(@NonNull View itemView) {
                super(itemView);

                notice = (TextView)itemView.findViewById(R.id.textView21);
                time = (TextView)itemView.findViewById(R.id.textView22);
                sender = (TextView)itemView.findViewById(R.id.textView23);
            }
        }
}
