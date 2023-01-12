package com.ivanova.cinema.View.CinemaSessionsView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ivanova.cinema.Model.DBConnector;
import com.ivanova.cinema.Model.Entities.FilmSession;
import com.ivanova.cinema.Model.Entities.Session;
import com.ivanova.cinema.R;
import com.ivanova.cinema.View.CinemaListView.CinemaList;
import com.ivanova.cinema.View.LoginView.Login;
import com.ivanova.cinema.View.MovieActivity;
import com.ivanova.cinema.View.MyTicketsView.MyTickets;
import com.ivanova.cinema.View.SessionSeatsView.SessionSeats;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CinemaSessions extends AppCompatActivity implements CinemaSessionsRecyclerViewInterface, SessionsRecyclerViewInterface {

    private BottomNavigationView menu;
    private SharedPreferences pref;

    private TextView tv_cinemaName;
    private TextView tv_date;
    private TextView tv_noFilms;

    private RelativeLayout rl_dateBtn;
    private DatePickerDialog datePickerDialog;

    private int yearPicked;
    private int monthPicked;
    private int dayPicked;

    private ArrayList<Session> sessions;
    private ArrayList<FilmSession> filmSessions;

    private DBConnector dbConnector;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinema_sessions);

        pref = getSharedPreferences("user_login", MODE_PRIVATE);
        setUpMenu();

        dbConnector = new DBConnector(getApplicationContext());

        Calendar calendar = Calendar.getInstance();
        yearPicked = calendar.get(Calendar.YEAR);
        monthPicked = calendar.get(Calendar.MONTH) + 1;
        dayPicked = calendar.get(Calendar.DAY_OF_MONTH);

        tv_cinemaName = findViewById(R.id.tv_cinemaName);
        tv_cinemaName.setText(getIntent().getStringExtra("CINEMA_NAME"));

        tv_noFilms = findViewById(R.id.tv_noFilms);

        tv_date = findViewById(R.id.tv_date);
        String dateStr = convertToDateString(dayPicked, monthPicked, yearPicked);
        tv_date.setText(dateStr);

        // ---------------------- Date Picker -----------------------------
        rl_dateBtn = findViewById(R.id.date_picker);
        rl_dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(CinemaSessions.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        yearPicked = year;
                        monthPicked = month + 1;
                        dayPicked = day;

                        String dateStr = convertToDateString(dayPicked, monthPicked, yearPicked);
                        tv_date.setText(dateStr);
                        setFilmSessions();
                    }
                }, yearPicked, monthPicked - 1, dayPicked);
                datePickerDialog.show();
            }
        });

        setFilmSessions();
    }

    private void setFilmSessions() {
        tv_noFilms.setVisibility(View.INVISIBLE);

        Integer cinemaId = Integer.parseInt(getIntent().getStringExtra("CINEMA_ID"));
        String date = convertToDateString(dayPicked, monthPicked, yearPicked);
        sessions = dbConnector.getSessions(cinemaId, date);
        filmSessions = fromSessionsToFilmSessions(sessions);

        // ---------------------- Sessions Recycler View -----------------------------
        recyclerView = findViewById(R.id.cinemaSessionsRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new CinemaSessionsRecyclerViewAdapter(filmSessions, this, CinemaSessions.this);
        recyclerView.setAdapter(recyclerViewAdapter);

        if (filmSessions.size() == 0) {
            tv_noFilms.setVisibility(View.VISIBLE);
        }
    }

    public static String convertToDateString(int day, int month, int year) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        try {
            date = sdf.parse(day + "." + month + "." + year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }

    public static ArrayList<FilmSession> fromSessionsToFilmSessions(ArrayList<Session> sessions) {
        HashMap<Integer, FilmSession> filmSessions = new HashMap<>();
        for (Session session : sessions) {
            if (!filmSessions.containsKey(session.getFilmId())) {
                filmSessions.put(session.getFilmId(), new FilmSession(session.getFilmId(), session.getFilmName(), Integer.parseInt(session.getFilmApiId()), new ArrayList<>()));
            }
            FilmSession filmSession = filmSessions.get(session.getFilmId());
            filmSession.addSession(session);
        }
        return new ArrayList<>(filmSessions.values());
    }

    @Override
    public void onFilmItemClick(int position) {
        Intent intent = new Intent(CinemaSessions.this, MovieActivity.class);
        intent.putExtra("movie_id", filmSessions.get(position).getFilmApiId().toString());
        startActivity(intent);
    }

    @Override
    public void onSessionItemClick(int position, FilmSession filmSession) {
        Intent intent = new Intent(CinemaSessions.this, SessionSeats.class);
        intent.putExtra("SESSION_ID", filmSession.getSessions().get(position).getId().toString());
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
                        Intent intent = new Intent(CinemaSessions.this, CinemaList.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.myTicketsItem: {
                        Intent intent = new Intent(CinemaSessions.this, MyTickets.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.exitItem: {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(CinemaSessions.this, Login.class);
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
