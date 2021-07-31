package com.example.civilfamilyiitism.ProfessorSideOnly;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.civilfamilyiitism.Adapters.RecyclerViewAdapter;
import com.example.civilfamilyiitism.R;
import com.example.civilfamilyiitism.studentinfo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Studentlistviewer extends AppCompatActivity {

    RecyclerView rcv;
    RecyclerViewAdapter adapter;
    String check;
    SearchView srch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlistviewer);

        srch = (SearchView)findViewById(R.id.searchView2);
        check = getIntent().getStringExtra("keycheck");
        rcv = (RecyclerView) findViewById(R.id.recyclerView);


        try {
           processsearch("");
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        srch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    processsearch(query);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    processsearch(newText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

    }
    private void processsearch(String s) throws Exception{
        GridLayoutManager manager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        rcv.setLayoutManager(manager);
        FirebaseRecyclerOptions<studentinfo> options =
                new FirebaseRecyclerOptions.Builder<studentinfo>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(check).orderByChild("username").startAt(s).endAt(s+"\uf8ff"), studentinfo.class)
                        .build();

        adapter = new RecyclerViewAdapter(options);
        rcv.setAdapter(adapter);
        adapter.startListening();
    }
}