package com.example.pinpointer.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ListViewHolder> implements Filterable {
    @NonNull
    @Override
    public ItemAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ListViewHolder listViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //public final TextView wordItemView;
        final ListViewHolder mAdapter;

        /**
         * Creates a new custom view holder to hold the view to display in the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         * @param adapter The adapter that manages the the data and views for the RecyclerView.
         */
        public ListViewHolder(View itemView, ListViewHolder adapter) {
            super(itemView);
            //wordItemView = (TextView) itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // All we do here is prepend "Clicked! " to the text in the view, to verify that
            // the correct item was clicked. The underlying data does not change.
           // wordItemView.setText ("Clicked! "+ wordItemView.getText());
        }
    }
}




