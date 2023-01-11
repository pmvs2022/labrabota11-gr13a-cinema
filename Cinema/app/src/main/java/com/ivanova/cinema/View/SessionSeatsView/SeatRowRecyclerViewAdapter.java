package com.ivanova.cinema.View.SessionSeatsView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanova.cinema.Model.Entities.Seat;
import com.ivanova.cinema.R;

import java.util.ArrayList;

public class SeatRowRecyclerViewAdapter extends RecyclerView.Adapter<SeatRowRecyclerViewAdapter.MyViewHolder> {

    private final SeatRecyclerViewInterface recyclerViewInterface;

    ArrayList<Seat> rowSeats;

    public SeatRowRecyclerViewAdapter(ArrayList<Seat> rowSeats, SeatRecyclerViewInterface recyclerViewInterface) {
        this.rowSeats = rowSeats;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seat_item, parent, false);
        SeatRowRecyclerViewAdapter.MyViewHolder holder = new SeatRowRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_seat.setText(rowSeats.get(position).getSeatNumber().toString());
    }

    @Override
    public int getItemCount() {
        return rowSeats.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_seat;

        public MyViewHolder(@NonNull View itemView, SeatRecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tv_seat = itemView.findViewById(R.id.tv_seatNumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAbsoluteAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onSeatItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
