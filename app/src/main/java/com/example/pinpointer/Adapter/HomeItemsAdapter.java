package com.example.pinpointer.Adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pinpointer.R;

import java.util.List;

public class HomeItemsAdapter extends BaseAdapter {
    private Context _context;
    private List<HomeItems> homeItems;
    public HomeItemsAdapter(Context _context,List<HomeItems> homeItems) {
        this._context = _context;
        this.homeItems=homeItems;
    }

    @Override
    public int getCount() {
        return homeItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if(convertView==null){
            gridView=new View(_context);
            gridView=inflater.inflate(R.layout.home_items,null);
            TextView textView=gridView.findViewById(R.id.category);
            FloatingActionButton fab=gridView.findViewById(R.id.img);
            textView.setText(homeItems.get(position).category);
            fab.setImageResource(homeItems.get(position).image);
        }
        else{

            gridView=convertView;
        }
        return gridView;
    }
}
