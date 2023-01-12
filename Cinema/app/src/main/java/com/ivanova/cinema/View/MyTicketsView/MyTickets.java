package com.ivanova.cinema.View.MyTicketsView;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanova.cinema.Model.DBConnector;
import com.ivanova.cinema.Model.Entities.Ticket;
import com.ivanova.cinema.R;

import java.util.ArrayList;

public class MyTickets extends AppCompatActivity {

    private ArrayList<Ticket> tickets;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private DBConnector dbConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_tickets);

        dbConnector = new DBConnector(getApplicationContext());

        // ---------------------- MyTickets Recycler View -----------------------------
        tickets = dbConnector.getMyTickets();

        recyclerView = findViewById(R.id.myTicketsRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new MyTicketsRecyclerViewAdapter(tickets, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
