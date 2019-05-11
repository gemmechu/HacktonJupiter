package com.example.pinpointer.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pinpointer.R;
import com.example.pinpointer.model.Facility;

import java.util.List;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.ViewHolder> {


    private List<Facility> facilityList;
    private Context context;


    public FacilityAdapter(List<Facility> facilityList,Context context){
        this.facilityList = facilityList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Facility facility = facilityList.get(i);

        viewHolder.name.setText(facility.getName());
        viewHolder.type.setText(facility.getType());
        viewHolder.views.setText(facility.getViews());
        viewHolder.distance.setText("loading...");

    }

    @Override
    public int getItemCount() {
        return facilityList.size() ;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        private TextView name,type,views,distance;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            views = itemView.findViewById(R.id.views);
            distance = itemView.findViewById(R.id.distance);
        }
    }

}
