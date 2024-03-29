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
 * Use the {@link AsceFacebookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AsceFacebookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AsceFacebookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AsceFacebookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AsceFacebookFragment newInstance(String param1, String param2) {
        AsceFacebookFragment fragment = new AsceFacebookFragment();
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
        view= inflater.inflate(R.layout.fragment_asce_facebook, container, false);
        WebView fbpage = (WebView)view.findViewById(R.id.facebookpage);
        fbpage.getSettings().setJavaScriptEnabled(true);
        fbpage.setWebViewClient(new WebViewClient());
        fbpage.loadUrl("https://www.facebook.com/asce.iitism/");
        return view;
    }
}