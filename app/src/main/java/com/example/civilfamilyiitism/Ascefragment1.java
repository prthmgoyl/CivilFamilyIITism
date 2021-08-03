package com.example.civilfamilyiitism;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.civilfamilyiitism.Adapters.RecyclerViewAdapter;
import com.example.civilfamilyiitism.Adapters.asceteamadapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Ascefragment1 extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ascefragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Ascefragment1 newInstance(String param1, String param2) {
        Ascefragment1 fragment = new Ascefragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Ascefragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_ascefragment1, container, false);
        SearchView srch = (SearchView)view.findViewById(R.id.searchView4);
        CardView card = (CardView)view.findViewById(R.id.cardView3);
        RecyclerView rcv = (RecyclerView)view.findViewById(R.id.rcv);


        srch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.isEmpty()){
                    card.setVisibility(View.INVISIBLE);

                }
                else{
                    card.setVisibility(View.VISIBLE);
                    search(query);
                }

                return true;
            }

            private void search(String s) {
                asceteamadapter adapter;
                rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
                FirebaseRecyclerOptions<studentinfo> options =
                        new FirebaseRecyclerOptions.Builder<studentinfo>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("UserInfoWithUid").orderByChild("username").startAt(s).endAt(s + "\uf8ff"), studentinfo.class)
                                .build();

                adapter = new asceteamadapter(options);
                rcv.setAdapter(adapter);
                adapter.startListening();
            }



            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    card.setVisibility(View.INVISIBLE);
                }
                else{
                    card.setVisibility(View.VISIBLE);
                    search(newText);
                }
                return true;
            }
        });


        return view;
    }
}