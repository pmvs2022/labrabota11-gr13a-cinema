package com.ivanova.cinema.View.CinemaListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanova.cinema.Model.Entities.Cinema;
import com.ivanova.cinema.R;

import java.util.ArrayList;

public class CinemaListRecyclerViewAdapter extends RecyclerView.Adapter<CinemaListRecyclerViewAdapter.MyViewHolder> {

    private final CinemaListRecyclerViewInterface recyclerViewInterface;

    ArrayList<Cinema> cinemas;
    Context context;

    public CinemaListRecyclerViewAdapter(ArrayList<Cinema> cinemas, Context context, CinemaListRecyclerViewInterface recyclerViewInterface) {
        this.cinemas = cinemas;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cinema_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view, recyclerViewInterface);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_cinemaName.setText(cinemas.get(position).getName());
        holder.tv_cinemaDescription.setText(cinemas.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return cinemas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_cinemaName;
        TextView tv_cinemaDescription;

        public MyViewHolder(@NonNull View itemView, CinemaListRecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tv_cinemaName = itemView.findViewById(R.id.tv_cinemaName);
            tv_cinemaDescription = itemView.findViewById(R.id.tv_cinemaDescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAbsoluteAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onCinemaItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
