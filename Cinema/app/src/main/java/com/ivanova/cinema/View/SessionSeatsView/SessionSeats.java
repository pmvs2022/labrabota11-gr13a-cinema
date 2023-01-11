package com.ivanova.cinema.View.SessionSeatsView;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanova.cinema.Model.DBConnector;
import com.ivanova.cinema.Model.Entities.Seat;
import com.ivanova.cinema.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class SessionSeats extends AppCompatActivity implements SeatRecyclerViewInterface {

    private DBConnector dbConnector;

    private ArrayList<Seat> seats;
    private TreeMap<Integer, ArrayList<Seat>> tm_seats;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_seats);

        Integer sessionId = Integer.parseInt(getIntent().getStringExtra("SESSION_ID"));
        dbConnector = new DBConnector(getApplicationContext());
        seats = dbConnector.getAllSeats(sessionId);

        fromArrayListToHashMapSeats(seats);

        // ---------------------- Seats Recycler View -----------------------------
        recyclerView = findViewById(R.id.seatsRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new SessionSeatsRecyclerViewAdapter(tm_seats, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void fromArrayListToHashMapSeats(ArrayList<Seat> seats) {
        TreeMap<Integer, ArrayList<Seat>> treeMap = new TreeMap<>();
        for (Seat seat : seats) {
            if (!treeMap.containsKey(seat.getSeatRow())) {
                treeMap.put(seat.getSeatRow(), new ArrayList<>());
            }
            treeMap.get(seat.getSeatRow()).add(seat);
        }
        for (Map.Entry<Integer, ArrayList<Seat>> mapEntry : treeMap.entrySet()) {
            ArrayList<Seat> rowSeats = mapEntry.getValue();
            Collections.sort(rowSeats, new Comparator<Seat>() {
                @Override
                public int compare(Seat seat1, Seat seat2) {
                    return seat1.getSeatNumber() - seat2.getSeatNumber();
                }
            });
            treeMap.put(mapEntry.getKey(), rowSeats);
        }
        tm_seats = treeMap;
    }

    @Override
    public void onSeatItemClick(int position) {

    }
}
