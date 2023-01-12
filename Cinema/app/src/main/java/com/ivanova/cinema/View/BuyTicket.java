package com.ivanova.cinema.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ivanova.cinema.Model.DBConnector;
import com.ivanova.cinema.R;
import com.ivanova.cinema.View.CinemaListView.CinemaList;
import com.ivanova.cinema.View.CinemaSessionsView.CinemaSessions;
import com.ivanova.cinema.View.MyTicketsView.MyTickets;

public class BuyTicket extends AppCompatActivity {

    private DBConnector dbConnector;

    private TextView tv_cinemaName;
    private TextView tv_filmName;
    private TextView tv_date;
    private TextView tv_time;
    private TextView tv_price;
    private TextView tv_row;
    private TextView tv_place;
    private TextView tv_hall;
    private TextView tv_error;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_ticket);

        dbConnector = new DBConnector(getApplicationContext());

        tv_cinemaName = findViewById(R.id.tv_cinemaName);
        tv_filmName = findViewById(R.id.tv_filmName);
        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);
        tv_price = findViewById(R.id.tv_priceData);
        tv_row = findViewById(R.id.tv_rowData);
        tv_place = findViewById(R.id.tv_placeData);
        tv_hall = findViewById(R.id.tv_hallData);
        tv_error = findViewById(R.id.tv_error);

        tv_error.setVisibility(View.INVISIBLE);

        tv_cinemaName.setText(getIntent().getStringExtra("CINEMA_NAME"));
        tv_filmName.setText(getIntent().getStringExtra("FILM_NAME").toUpperCase());
        tv_date.setText(getIntent().getStringExtra("DATE"));
        tv_time.setText(getIntent().getStringExtra("TIME"));
        tv_price.setText(getIntent().getStringExtra("PRICE"));
        tv_row.setText(getIntent().getStringExtra("ROW"));
        tv_place.setText(getIntent().getStringExtra("PLACE"));
        tv_hall.setText(getIntent().getStringExtra("HALL"));
    }

    public void onBtnCancelClick(View view) {
        finish();
    }

    public void onBtnBuyClick(View view) {
        Integer seatId = Integer.parseInt(getIntent().getStringExtra("SEAT_ID"));
        Integer sessionId = Integer.parseInt(getIntent().getStringExtra("SESSION_ID"));

        if (dbConnector.buyTicket(seatId, sessionId)) {
            tv_error.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(BuyTicket.this, MyTickets.class);
            startActivity(intent);

        } else {
            tv_error.setVisibility(View.VISIBLE);
        }
    }
}
