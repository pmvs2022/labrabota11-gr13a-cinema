package com.ivanova.cinema.View.SessionSeatsView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ivanova.cinema.Model.DBConnector;
import com.ivanova.cinema.Model.Entities.Seat;
import com.ivanova.cinema.Model.Entities.SeatUI;
import com.ivanova.cinema.Model.Entities.Session;
import com.ivanova.cinema.R;
import com.ivanova.cinema.View.BuyTicket;
import com.ivanova.cinema.View.CinemaListView.CinemaList;
import com.ivanova.cinema.View.CinemaSessionsView.CinemaSessions;
import com.ivanova.cinema.View.LoginView.Login;
import com.ivanova.cinema.View.MyTicketsView.MyTickets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class SessionSeats extends AppCompatActivity implements SeatRecyclerViewInterface {

    private BottomNavigationView menu;
    private SharedPreferences pref;

    private DBConnector dbConnector;

    private ArrayList<Seat> seats;
    private ArrayList<Seat> freeSeats;
    private TreeMap<Integer, ArrayList<SeatUI>> tm_seats;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    @SuppressWarnings("InvalidSetHasFixedSize")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_seats);

        pref = getSharedPreferences("user_login", MODE_PRIVATE);
        setUpMenu();

        Integer sessionId = Integer.parseInt(getIntent().getStringExtra("SESSION_ID"));
        dbConnector = new DBConnector(getApplicationContext());
        seats = dbConnector.getAllSeats(sessionId);
        freeSeats = dbConnector.getFreeSeats(sessionId);

        fromArrayListToHashMapSeats(seats, freeSeats);

        // ---------------------- Seats Recycler View -----------------------------
        recyclerView = findViewById(R.id.seatsRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new SessionSeatsRecyclerViewAdapter(tm_seats, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void fromArrayListToHashMapSeats(ArrayList<Seat> seats, ArrayList<Seat> freeSeats) {
        TreeMap<Integer, ArrayList<SeatUI>> treeMap = new TreeMap<>();
        for (Seat seat : seats) {
            if (!treeMap.containsKey(seat.getSeatRow())) {
                treeMap.put(seat.getSeatRow(), new ArrayList<>());
            }
            treeMap.get(seat.getSeatRow()).add(fromSeatToSeatUI(seat, freeSeats));
        }
        for (Map.Entry<Integer, ArrayList<SeatUI>> mapEntry : treeMap.entrySet()) {
            ArrayList<SeatUI> rowSeats = mapEntry.getValue();
            Collections.sort(rowSeats, new Comparator<SeatUI>() {
                @Override
                public int compare(SeatUI seat1, SeatUI seat2) {
                    return seat1.getSeatNumber() - seat2.getSeatNumber();
                }
            });
            treeMap.put(mapEntry.getKey(), rowSeats);
        }
        tm_seats = treeMap;
    }

    private SeatUI fromSeatToSeatUI(Seat seat, ArrayList<Seat> freeSeats) {
        boolean isFree = false;
        for (Seat freeSeat : freeSeats) {
            if (seat.getId() == freeSeat.getId()) {
                isFree = true;
            }
        }
        SeatUI seatUI = new SeatUI(seat.getId(), seat.getSeatNumber(), seat.getSeatRow(), seat.getPrice(), seat.getHallId(), isFree);
        return seatUI;
    }

    @Override
    public void onSeatItemClick(int position, SeatUI seat) {
        Intent intent = new Intent(SessionSeats.this, BuyTicket.class);

        Integer sessionId = Integer.parseInt(getIntent().getStringExtra("SESSION_ID"));
        Session session = dbConnector.getSession(sessionId);

        intent.putExtra("SEAT_ID", seat.getId().toString());
        intent.putExtra("SESSION_ID", String.valueOf(sessionId));

        intent.putExtra("CINEMA_NAME", dbConnector.getCinemaName(seat.getHallId()));
        intent.putExtra("FILM_NAME", session.getFilmName());

        intent.putExtra("DATE", session.getDate());
        intent.putExtra("TIME", session.getTime());

        intent.putExtra("PRICE", seat.getPrice().toString());
        intent.putExtra("ROW", seat.getSeatRow().toString());
        intent.putExtra("PLACE", seat.getSeatNumber().toString());
        intent.putExtra("HALL", dbConnector.getHallName(seat.getHallId()));

        startActivity(intent);
    }

    private void setUpMenu() {
        // ---------------------- Menu -----------------------------
        menu = findViewById(R.id.bottomNavigation);
        menu.getMenu().setGroupCheckable(0, false, true);
        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cinemasItem: {
                        Intent intent = new Intent(SessionSeats.this, CinemaList.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.myTicketsItem: {
                        Intent intent = new Intent(SessionSeats.this, MyTickets.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.exitItem: {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(SessionSeats.this, Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        return true;
                    }
                }
                return true;
            }
        });
    }
}
