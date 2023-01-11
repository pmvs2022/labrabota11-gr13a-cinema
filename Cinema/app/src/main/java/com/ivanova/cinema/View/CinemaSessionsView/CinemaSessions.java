package com.ivanova.cinema.View.CinemaSessionsView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ivanova.cinema.Model.DBConnector;
import com.ivanova.cinema.Model.Entities.Session;
import com.ivanova.cinema.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CinemaSessions extends AppCompatActivity {

    private TextView tv_cinemaName;
    private TextView tv_date;

    private RelativeLayout rl_dateBtn;
    private DatePickerDialog datePickerDialog;

    private int yearPicked;
    private int monthPicked;
    private int dayPicked;

    private ArrayList<Session> sessions;

    private DBConnector dbConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinema_sessions);

        Calendar calendar = Calendar.getInstance();
        yearPicked = calendar.get(Calendar.YEAR);
        monthPicked = calendar.get(Calendar.MONTH) + 1;
        dayPicked = calendar.get(Calendar.DAY_OF_MONTH);

        tv_cinemaName = findViewById(R.id.tv_cinemaName);
        tv_cinemaName.setText(getIntent().getStringExtra("CINEMA_NAME"));

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
                    }
                }, yearPicked, monthPicked - 1, dayPicked);
                datePickerDialog.show();
            }
        });

        dbConnector = new DBConnector(getApplicationContext());
        sessions = dbConnector.getSessions(Integer.parseInt(getIntent().getStringExtra("CINEMA_ID")),
                convertToDateString(dayPicked, monthPicked, yearPicked));
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
//        return "15.12.2022";
    }
}
