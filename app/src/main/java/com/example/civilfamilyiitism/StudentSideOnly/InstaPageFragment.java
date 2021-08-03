package com.example.civilfamilyiitism.StudentSideOnly;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.civilfamilyiitism.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstaPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstaPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InstaPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InstaPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InstaPageFragment newInstance(String param1, String param2) {
        InstaPageFragment fragment = new InstaPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        view= inflater.inflate(R.layout.fragment_insta_page, container, false);
        WebView instapage = (WebView)view.findViewById(R.id.instapage);
        instapage.getSettings().setJavaScriptEnabled(true);
        instapage.setWebViewClient(new WebViewClient());
        instapage.loadUrl("https://twitter.com/ASCE_ISM");
        return view;
    }

}