package com.example.pinpointer.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinpointer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
TextView tv;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_search, container, false);

        String strtext = getArguments().getString("search");
        search(strtext);
        return view;
    }

    private void search(String strtext) {
        Toast.makeText(getContext(),strtext,Toast.LENGTH_LONG).show();
    }


}
