package com.ivanova.cinema.View.MyTicketsView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanova.cinema.Model.Entities.Ticket;
import com.ivanova.cinema.R;

import java.util.ArrayList;

public class MyTicketsRecyclerViewAdapter extends RecyclerView.Adapter<MyTicketsRecyclerViewAdapter.MyViewHolder> {

    ArrayList<Ticket> tickets;
    Context context;

    public MyTicketsRecyclerViewAdapter(ArrayList<Ticket> tickets, Context context) {
        this.tickets = tickets;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_ticket_item, parent, false);
        MyTicketsRecyclerViewAdapter.MyViewHolder holder = new MyTicketsRecyclerViewAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_filmName.setText(tickets.get(position).getSession().getFilmName().toUpperCase());
        holder.tv_cinemaName.setText(tickets.get(position).getCinemaName());
        holder.tv_date.setText(tickets.get(position).getSession().getDate());
        holder.tv_time.setText(tickets.get(position).getSession().getTime());
        holder.tv_price.setText(tickets.get(position).getSeat().getPrice().toString());
        holder.tv_row.setText(tickets.get(position).getSeat().getSeatRow().toString());
        holder.tv_place.setText(tickets.get(position).getSeat().getSeatNumber().toString());
        holder.tv_hall.setText(tickets.get(position).getHallName());
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_filmName;
        TextView tv_cinemaName;
        TextView tv_date;
        TextView tv_time;
        TextView tv_price;
        TextView tv_row;
        TextView tv_place;
        TextView tv_hall;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_filmName = itemView.findViewById(R.id.tv_filmName);
            tv_cinemaName = itemView.findViewById(R.id.tv_cinemaName);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_price = itemView.findViewById(R.id.tv_priceData);
            tv_row = itemView.findViewById(R.id.tv_rowData);
            tv_place = itemView.findViewById(R.id.tv_placeData);
            tv_hall = itemView.findViewById(R.id.tv_hallData);
        }
    }
}
