package com.ivanova.cinema.View.MyTicketsView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ivanova.cinema.Model.DBConnector;
import com.ivanova.cinema.Model.Entities.Ticket;
import com.ivanova.cinema.R;
import com.ivanova.cinema.View.BuyTicket;
import com.ivanova.cinema.View.CinemaListView.CinemaList;
import com.ivanova.cinema.View.LoginView.Login;

import java.util.ArrayList;

public class MyTickets extends AppCompatActivity {

    private BottomNavigationView menu;
    private SharedPreferences pref;

    private ArrayList<Ticket> tickets;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private DBConnector dbConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_tickets);

        pref = getSharedPreferences("user_login", MODE_PRIVATE);
        setUpMenu();

        dbConnector = new DBConnector(getApplicationContext());

        // ---------------------- MyTickets Recycler View -----------------------------
        tickets = dbConnector.getMyTickets();

        recyclerView = findViewById(R.id.myTicketsRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerViewAdapter = new MyTicketsRecyclerViewAdapter(tickets, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void setUpMenu() {
        // ---------------------- Menu -----------------------------
        menu = findViewById(R.id.bottomNavigation);
        menu.getMenu().getItem(1).setChecked(true);
        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cinemasItem: {
                        Intent intent = new Intent(MyTickets.this, CinemaList.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.myTicketsItem: {
                        return true;
                    }
                    case R.id.exitItem: {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(MyTickets.this, Login.class);
                        startActivity(intent);
                        return true;
                    }
                }
                return true;
            }
        });
    }
}
