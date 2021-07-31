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
import com.example.civilfamilyiitism.studentinfo;
import com.example.civilfamilyiitism.studentinfoincludechangeto;
import com.firebase.ui.database.FirebaseArray;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class changeclassadapter extends FirebaseRecyclerAdapter<studentinfoincludechangeto, changeclassadapter.holder> {

    Context mContext;
    studentinfo studinfo;

    public changeclassadapter(@NonNull FirebaseRecyclerOptions<studentinfoincludechangeto> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull changeclassadapter.holder holder, int position, @NonNull studentinfoincludechangeto model) {
        String check = model.getYear();
        if(check.equalsIgnoreCase("zero")){
            holder.txv1.setText("From: "+check);
        }
        else if (check.equalsIgnoreCase("first")){
            holder.txv1.setText("From: First Year");
        }
        else if (check.equalsIgnoreCase("second")){
            holder.txv1.setText("From: Second Year");
        }
        else if (check.equalsIgnoreCase("third")){
            holder.txv1.setText("From: Third Year");
        }
        else if (check.equalsIgnoreCase("fourth")){
            holder.txv1.setText("From: Fourth Year");
        }
        else{
            holder.txv1.setText("From: "+check);
        }

        holder.txv2.setText("To: "+model.getChangeto());
        holder.txv3.setText(model.getUsername());
        holder.txv4.setText(model.getEmail());
        holder.txv5.setText(model.getDesignation());

        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FirebaseDatabase.getInstance().getReference()
                            .child("UserInfoWithUid").child(model.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            studinfo = snapshot.getValue(studentinfo.class);
                            String changeto = model.getChangeto();
                            if(changeto.equalsIgnoreCase("First Year")){
                                changeto = "first";
                                try {
                                    changeclass(changeto);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(changeto.equalsIgnoreCase("Second Year")){
                                changeto = "second";
                                try {
                                    changeclass(changeto);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(changeto.equalsIgnoreCase("Third Year")){
                                changeto = "third";
                                try {
                                    changeclass(changeto);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(changeto.equalsIgnoreCase("Fourth Year")){
                                changeto = "fourth";
                                try {
                                    changeclass(changeto);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            /*
                            else if(changeto.equalsIgnoreCase("Passout")){
                                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");
                                String passoutyear=" null";
                                passoutyear = dateformat.format(Calendar.getInstance().getTime());
                                changeto = "passout" ;
                                changeclass(changeto);
                            }
                            */
                            else{

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(mContext, "Error!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            private void changeclass(String changeto) throws Exception {

                String from = model.getYear();

                if(from.equalsIgnoreCase("first")){
                    try {
                        change(changeto);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(from.equalsIgnoreCase("second")){
                    try {
                        change(changeto);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(from.equalsIgnoreCase("third")){
                    try {
                        change(changeto);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(from.equalsIgnoreCase("fourth")){
                    try {
                        change(changeto);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                /*
                else {
                    if(changeto.equalsIgnoreCase("passout")){
                        FirebaseDatabase.getInstance().getReference()
                                .child("ChangeClassRequests").child(model.getUid()).removeValue();
                    }
                }*/

            }

            private void change(String changeto) throws Exception {

                String from = model.getYear();
                studinfo.setYear(changeto);
                FirebaseDatabase.getInstance().getReference()
                        .child("UserInfoWithUid").child(model.getUid()).setValue(studinfo);
                FirebaseDatabase.getInstance().getReference()
                        .child("UserInfo").child(studinfo.getPhone()).setValue(studinfo);
                FirebaseDatabase.getInstance().getReference()
                        .child(changeto).child(model.getUid()).setValue(studinfo);
                FirebaseDatabase.getInstance().getReference()
                        .child(from).child(model.getUid()).removeValue();
                FirebaseDatabase.getInstance().getReference()
                        .child("ChangeClassRequests").child(model.getUid()).removeValue();
                Toast.makeText(mContext, "Approved", Toast.LENGTH_SHORT).show();


            }
        });

        holder.deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference()
                        .child("ChangeClassRequests").child(model.getUid()).removeValue();
                Toast.makeText(mContext, "Denied !!", Toast.LENGTH_SHORT).show();
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


