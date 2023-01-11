package com.ivanova.cinema.View.SessionSeatsView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanova.cinema.Model.Entities.Seat;
import com.ivanova.cinema.R;
import com.ivanova.cinema.View.CinemaSessionsView.CinemaSessionsRecyclerViewInterface;

import java.util.ArrayList;
import java.util.TreeMap;

public class SessionSeatsRecyclerViewAdapter extends RecyclerView.Adapter<SessionSeatsRecyclerViewAdapter.MyViewHolder> {

    TreeMap<Integer, ArrayList<Seat>> seats;
    Context context;

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public SessionSeatsRecyclerViewAdapter(TreeMap<Integer, ArrayList<Seat>> seats, Context context) {
        this.seats = seats;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seat_row, parent, false);
        SessionSeatsRecyclerViewAdapter.MyViewHolder holder = new SessionSeatsRecyclerViewAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.recView_seats.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new GridLayoutManager(holder.recView_seats.getContext(), seats.get(position + 1).size());
        ArrayList<Seat> rowSeats = seats.get(position + 1);

        RecyclerView.Adapter recyclerViewAdapter = new SeatRowRecyclerViewAdapter(rowSeats, (SeatRecyclerViewInterface) context);
        holder.recView_seats.setLayoutManager(layoutManager);
        holder.recView_seats.setAdapter(recyclerViewAdapter);
        holder.recView_seats.setRecycledViewPool(viewPool);


        holder.tv_rowNum1.setText(String.valueOf(position + 1));
        holder.tv_rowNum2.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recView_seats;
        TextView tv_rowNum1;
        TextView tv_rowNum2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recView_seats = itemView.findViewById(R.id.seatsRowRecyclerView);
            tv_rowNum1 = itemView.findViewById(R.id.tv_rowNumber1);
            tv_rowNum2 = itemView.findViewById(R.id.tv_rowNumber2);
        }
    }
}
