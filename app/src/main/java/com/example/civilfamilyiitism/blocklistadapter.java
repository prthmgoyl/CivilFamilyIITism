package com.example.civilfamilyiitism;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class blocklistadapter extends FirebaseRecyclerAdapter<studentinfo,blocklistadapter.holder> {

    Context mContext;

    public blocklistadapter(@NonNull FirebaseRecyclerOptions<studentinfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final holder holder, final int position, @NonNull studentinfo model) {
        holder.txv1.setText(model.getUsername());
        holder.txv2.setText(model.getEmail());
        holder.txv3.setText(model.getPhone());

        try {

            Glide
                    .with(mContext)
                    .load(R.drawable.ic_baseline_fingerprint_24_2)
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
        holder.img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu popupMenu;
                popupMenu = new PopupMenu(mContext,v);
                popupMenu.inflate(R.menu.unblock);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.unblock:
                                FirebaseDatabase.getInstance().getReference("Blocklist")
                                        .child(model.getUid()).removeValue();
                                Toast.makeText(mContext, "Unblocked Successfully!", Toast.LENGTH_SHORT).show();

                                return  true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
                return true;
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

            txv1=(TextView)itemView.findViewById(R.id.textView7);
            txv2=(TextView)itemView.findViewById(R.id.textView6);
            txv3=(TextView)itemView.findViewById(R.id.textView5);
            img = (ImageView)itemView.findViewById(R.id.imageView24);
        }
    }
}

