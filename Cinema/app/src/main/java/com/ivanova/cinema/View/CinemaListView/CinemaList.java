package com.ivanova.cinema.View.CinemaListView;

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
import com.ivanova.cinema.Model.Entities.Cinema;
import com.ivanova.cinema.R;
import com.ivanova.cinema.View.CinemaSessionsView.CinemaSessions;
import com.ivanova.cinema.View.LoginView.Login;
import com.ivanova.cinema.View.MyTicketsView.MyTickets;

import java.util.ArrayList;

public class CinemaList extends AppCompatActivity implements CinemaListRecyclerViewInterface {

    private BottomNavigationView menu;
    private SharedPreferences pref;

    private ArrayList<Cinema> cinemas;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private DBConnector dbConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinema_list);

        pref = getSharedPreferences("user_login", MODE_PRIVATE);
        setUpMenu();

        dbConnector = new DBConnector(getApplicationContext());

        // ---------------------- Cinema List View -----------------------------
        cinemas = dbConnector.getCinemas();

        recyclerView = findViewById(R.id.cinemaListRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new CinemaListRecyclerViewAdapter(cinemas, this, CinemaList.this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onCinemaItemClick(int position) {
        Intent intent = new Intent(CinemaList.this, CinemaSessions.class);

        intent.putExtra("CINEMA_ID", cinemas.get(position).getId().toString());
        intent.putExtra("CINEMA_NAME", cinemas.get(position).getName());
        intent.putExtra("CINEMA_DESCRIPTION", cinemas.get(position).getDescription());

        startActivity(intent);
    }

    private void setUpMenu() {
        // ---------------------- Menu -----------------------------
        menu = findViewById(R.id.bottomNavigation);
        menu.getMenu().getItem(0).setChecked(true);
        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cinemasItem: {
                        return true;
                    }
                    case R.id.myTicketsItem: {
                        Intent intent = new Intent(CinemaList.this, MyTickets.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.exitItem: {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(CinemaList.this, Login.class);
                        startActivity(intent);
                        return true;
                    }
                }
                return true;
            }
        });
    }
}
