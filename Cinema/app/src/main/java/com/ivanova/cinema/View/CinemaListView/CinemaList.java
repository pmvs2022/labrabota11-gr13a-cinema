package com.ivanova.cinema.View.CinemaListView;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanova.cinema.Model.DBConnector;
import com.ivanova.cinema.Model.Entities.Cinema;
import com.ivanova.cinema.R;
import com.ivanova.cinema.View.CinemaSessionsView.CinemaSessions;

import java.util.ArrayList;

public class CinemaList extends AppCompatActivity implements CinemaListRecyclerViewInterface {

    private ArrayList<Cinema> cinemas;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private DBConnector dbConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinema_list);

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
}
