package com.example.pinpointer.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinpointer.Adapter.HomeItems;
import com.example.pinpointer.Adapter.HomeItemsAdapter;
import com.example.pinpointer.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    Context _context;
    GridView gridView;
    ArrayList<HomeItems> homeItems;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        gridView= view.findViewById(R.id.home_grid);
        _context=view.getContext();
        setUpHomePage();

        return view;
    }
    private void setUpHomePage() {
        homeItems=new ArrayList();
        homeItems.add(new HomeItems("Gas Station",R.drawable.ic_local_gas_station_black_24dp));
        homeItems.add(new HomeItems("Dining",R.drawable.ic_local_dining_black_24dp));
        homeItems.add(new HomeItems("Atm",R.drawable.ic_local_atm_black_24dp));
        homeItems.add(new HomeItems("Hotel",R.drawable.ic_local_dining_black_24dp));
        homeItems.add(new HomeItems("Dining",R.drawable.ic_local_dining_black_24dp));
        homeItems.add(new HomeItems("Atm",R.drawable.ic_local_atm_black_24dp));



        gridView.setAdapter(new HomeItemsAdapter(_context,homeItems));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //implement the search here
                //String category = ((TextView)view.findViewById(R.id.category)).getText()
                Toast.makeText(_context,((TextView)view.findViewById(R.id.category)).getText(),Toast.LENGTH_SHORT).show();
                Log.d("item", String.valueOf(((TextView)view.findViewById(R.id.category)).getText()));
                Log.d("to be", homeItems.get(position).getCategory());
            }
        });
    }
}
