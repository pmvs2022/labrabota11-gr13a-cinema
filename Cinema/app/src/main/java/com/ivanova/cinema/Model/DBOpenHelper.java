package com.ivanova.cinema.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ivanova.cinema.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(Context context) {
        super(context, "cinemaDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.beginTransaction();

            // delete tables, if exist
            db.execSQL("drop table if exists cinema");
            db.execSQL("drop table if exists hall");
            db.execSQL("drop table if exists seat");
            db.execSQL("drop table if exists movie");
            db.execSQL("drop table if exists session");
            db.execSQL("drop table if exists ticket");
            db.execSQL("drop table if exists user");

            // create tables
            db.execSQL(
                    "create table cinema ("
                            + "cinema_id integer primary key,"
                            + "title text,"
                            + "description text" + ");"
            );
            db.execSQL(
                    "create table hall ("
                            + "hall_id integer primary key,"
                            + "title text,"
                            + "cinema_id integer,"
                            + "constraint cinema_fk foreign key (cinema_id) references cinema (cinema_id)"
                            + ");"
            );
            db.execSQL(
                    "create table seat ("
                            + "seat_id integer primary key,"
                            + "seat_number integer,"
                            + "row_number integer,"
                            + "hall_id integer,"
                            + "price real,"
                            + "constraint hall_fk foreign key (hall_id) references hall (hall_id)"
                            + ");"
            );
            db.execSQL(
                    "create table movie ("
                            + "movie_id integer primary key,"
                            + "title text,"
                            + "movie_api_id text" + ");"
            );
            db.execSQL(
                    "create table session ("
                            + "session_id integer primary key,"
                            + "date text,"
                            + "time text,"
                            + "hall_id integer,"
                            + "movie_id integer,"
                            + "constraint session_hall_fk foreign key (hall_id) references hall (hall_id),"
                            + "constraint session_movie_fk foreign key (movie_id) references movie (movie_id)"
                            + ");"
            );
            db.execSQL(
                    "create table ticket ("
                            + "ticket_id integer primary key,"
                            + "session_id integer,"
                            + "seat_id integer,"
                            + "user_id integer,"
                            + "constraint session_fk foreign key (session_id) references session (session_id),"
                            + "constraint seat_fk foreign key (seat_id) references seat (seat_id),"
                            + "constraint user_fk foreign key (user_id) references user (user_id)"
                            + ");"
            );
            db.execSQL(
                    "create table user ("
                            + "user_id integer primary key,"
                            + "login text unique,"
                            + "password text" + ");"
            );

            // insert data into tables
            ContentValues cv = new ContentValues();

            cv.put("cinema_id", 1);
            cv.put("title", "??????????????");
            cv.put("description", "?? ???????????????????? ?????????????????? ?? ???????????? ?????????????? ?????????? ???????????????????? ?????????? ?????????? ???????????? ?? ???????????????? 2D ?? 3D." +
                    "?? ???????????????????? ?????????????????? ?????? ?????????????????????????????? ????????: ??????????????????, ??????????????, ?? ?????????? ???????????????????????????????????? ?? 2014 ???????? ??VIP??-??????.?????? ???????????????? ???????????? ????????????????????, ?? ?????????? ?????????????????????? ???????????? ???????????? ?? ??????????????, ?? ?????????? ?????????????? ?????????? ?????????????? ?????????? ???????????????????? ???????? ???????????????? ?????????????????? ?? ?????????????????? ???? ????????????????.");
            db.insert("cinema", null, cv);
            cv.clear();

            cv.put("cinema_id", 2);
            cv.put("title", "????????????");
            cv.put("description", "???????????????? ??? ?????? ???????????? ?????????????????? ?? ????????????, ?? ?????????????? ?????????? ?????????????????????????????? ???????????? ?? ???????????????? 2D ?? 3D. " +
                    "?????? ?????? ?????????? ???????????????? ???? ???????????? ?????????????? ?????????? ???????? ???? ?????????? ????????. " +
                    "???? ?????????? ?????????????????????? ???????????????? ???????????????????? ?????????????????????????? ?? ???????? ?????????? ?????????????????????????? ????????????????: ???? ?????????????? ??????????????, ???? ??????????????-???????????? ?? ?? VIP-???????? ?? ???????????????? ?? ??????????????????. " +
                    "?????? ???????????????????? ?????????????? ???????????????? ???????????????????????? ?????????????????????????? Christie, ???????????????? ?????????????????? ???????????????? Dolby.");
            db.insert("cinema", null, cv);
            cv.clear();

            cv.put("cinema_id", 3);
            cv.put("title", "????????????");
            cv.put("description", "?????????????????????????? ???????????????????????? ???????????????????? ???????????????? ???????????????? ?????? ????????, ?????? ???? ???????????????? ?????????????????????????? ?? ???????????????? ?? ???????????? ??????????. " +
                    "???????????? ???????????? ???????????????????? ?????????? ???? ?????????? 20:00." +
                    " ?????? ?? ?????????? ???????????????????? ?????????? ????????????????????????. " +
                    "?????????????????? ?????????? ???????????????? ?? ????????????????, ?? ?????????? ???????????????? ???????????????????? ?????? ???? ?????? ??????????.");
            db.insert("cinema", null, cv);
            cv.clear();

            cv.put("hall_id", 1);
            cv.put("title", "?????????????? ??????");
            cv.put("cinema_id", 1);
            db.insert("hall", null, cv);
            cv.clear();

            cv.put("hall_id", 2);
            cv.put("title", "?????????? ??????");
            cv.put("cinema_id", 1);
            db.insert("hall", null, cv);
            cv.clear();

            cv.put("hall_id", 3);
            cv.put("title", "vip-??????");
            cv.put("cinema_id", 1);
            db.insert("hall", null, cv);
            cv.clear();

            cv.put("hall_id", 4);
            cv.put("title", "");
            cv.put("cinema_id", 2);
            db.insert("hall", null, cv);
            cv.clear();

            cv.put("hall_id", 5);
            cv.put("title", "");
            cv.put("cinema_id", 3);
            db.insert("hall", null, cv);
            cv.clear();

            // seats
            // ??????????????, ?????????????? ??????
            cv.put("seat_id", 1);
            cv.put("seat_number", 1);
            cv.put("row_number", 1);
            cv.put("hall_id", 1);
            cv.put("price", 8.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 2);
            cv.put("row_number", 1);
            cv.put("hall_id", 1);
            cv.put("price", 8.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 3);
            cv.put("row_number", 1);
            cv.put("hall_id", 1);
            cv.put("price", 8.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 4);
            cv.put("row_number", 1);
            cv.put("hall_id", 1);
            cv.put("price", 8.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 5);
            cv.put("row_number", 1);
            cv.put("hall_id", 1);
            cv.put("price", 8.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 1);
            cv.put("row_number", 2);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 2);
            cv.put("row_number", 2);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 3);
            cv.put("row_number", 2);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 4);
            cv.put("row_number", 2);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 5);
            cv.put("row_number", 2);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 1);
            cv.put("row_number", 3);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 2);
            cv.put("row_number", 3);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 3);
            cv.put("row_number", 3);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 4);
            cv.put("row_number", 3);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 5);
            cv.put("row_number", 3);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 1);
            cv.put("row_number", 4);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 2);
            cv.put("row_number", 4);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 3);
            cv.put("row_number", 4);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 4);
            cv.put("row_number", 4);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 5);
            cv.put("row_number", 4);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 1);
            cv.put("row_number", 5);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 2);
            cv.put("row_number", 5);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 3);
            cv.put("row_number", 5);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 4);
            cv.put("row_number", 5);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 5);
            cv.put("row_number", 5);
            cv.put("hall_id", 1);
            cv.put("price", 7.0);
            db.insert("seat", null, cv);
            cv.clear();

            // ?????????? ??????
            cv.put("seat_number", 1);
            cv.put("row_number", 1);
            cv.put("hall_id", 2);
            cv.put("price", 9.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 2);
            cv.put("row_number", 1);
            cv.put("hall_id", 2);
            cv.put("price", 9.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 3);
            cv.put("row_number", 1);
            cv.put("hall_id", 2);
            cv.put("price", 9.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 4);
            cv.put("row_number", 1);
            cv.put("hall_id", 2);
            cv.put("price", 9.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 1);
            cv.put("row_number", 2);
            cv.put("hall_id", 2);
            cv.put("price", 9.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 2);
            cv.put("row_number", 2);
            cv.put("hall_id", 2);
            cv.put("price", 9.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 3);
            cv.put("row_number", 2);
            cv.put("hall_id", 2);
            cv.put("price", 9.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 4);
            cv.put("row_number", 2);
            cv.put("hall_id", 2);
            cv.put("price", 9.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 1);
            cv.put("row_number", 3);
            cv.put("hall_id", 2);
            cv.put("price", 9.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 2);
            cv.put("row_number", 3);
            cv.put("hall_id", 2);
            cv.put("price", 9.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 3);
            cv.put("row_number", 3);
            cv.put("hall_id", 2);
            cv.put("price", 9.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 4);
            cv.put("row_number", 3);
            cv.put("hall_id", 2);
            cv.put("price", 9.0);
            db.insert("seat", null, cv);
            cv.clear();

            //vip ??????
            cv.put("seat_number", 1);
            cv.put("row_number", 1);
            cv.put("hall_id", 3);
            cv.put("price", 12.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 2);
            cv.put("row_number", 1);
            cv.put("hall_id", 3);
            cv.put("price", 12.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 3);
            cv.put("row_number", 1);
            cv.put("hall_id", 3);
            cv.put("price", 12.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 1);
            cv.put("row_number", 2);
            cv.put("hall_id", 3);
            cv.put("price", 12.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 2);
            cv.put("row_number", 2);
            cv.put("hall_id", 3);
            cv.put("price", 12.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 3);
            cv.put("row_number", 2);
            cv.put("hall_id", 3);
            cv.put("price", 12.0);
            db.insert("seat", null, cv);
            cv.clear();

            // ????????????
            cv.put("seat_number", 1);
            cv.put("row_number", 1);
            cv.put("hall_id", 4);
            cv.put("price", 10.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 2);
            cv.put("row_number", 1);
            cv.put("hall_id", 4);
            cv.put("price", 10.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 3);
            cv.put("row_number", 1);
            cv.put("hall_id", 4);
            cv.put("price", 10.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 1);
            cv.put("row_number", 2);
            cv.put("hall_id", 4);
            cv.put("price", 10.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 2);
            cv.put("row_number", 2);
            cv.put("hall_id", 4);
            cv.put("price", 10.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 3);
            cv.put("row_number", 2);
            cv.put("hall_id", 4);
            cv.put("price", 10.0);
            db.insert("seat", null, cv);
            cv.clear();

            // ????????????
            cv.put("seat_number", 1);
            cv.put("row_number", 1);
            cv.put("hall_id", 5);
            cv.put("price", 10.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 2);
            cv.put("row_number", 1);
            cv.put("hall_id", 5);
            cv.put("price", 10.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 3);
            cv.put("row_number", 1);
            cv.put("hall_id", 5);
            cv.put("price", 10.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 1);
            cv.put("row_number", 2);
            cv.put("hall_id", 5);
            cv.put("price", 10.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 2);
            cv.put("row_number", 2);
            cv.put("hall_id", 5);
            cv.put("price", 10.0);
            db.insert("seat", null, cv);
            cv.clear();

            cv.put("seat_number", 3);
            cv.put("row_number", 2);
            cv.put("hall_id", 5);
            cv.put("price", 10.0);
            db.insert("seat", null, cv);
            cv.clear();

            // ????????????
            cv.put("movie_id", 1);
            cv.put("title", "????????????: ???????? ????????");
            cv.put("movie_api_id", "76600");
            db.insert("movie", null, cv);
            cv.clear();

            cv.put("movie_id", 2);
            cv.put("title", "?????? ?? ?????????????? 2: ?????????????????? ??????????????");
            cv.put("movie_api_id", "315162");
            db.insert("movie", null, cv);
            cv.clear();

            cv.put("movie_id", 3);
            cv.put("title", "???????????????????????????? ??????????: ?????????? ??????????????????");
            cv.put("movie_api_id", "338953");
            db.insert("movie", null, cv);
            cv.clear();

            cv.put("movie_id", 4);
            cv.put("title", "??????????????????");
            cv.put("movie_api_id", "751171");
            db.insert("movie", null, cv);
            cv.clear();

            cv.put("movie_id", 5);
            cv.put("title", "?????????????? ?????????? ???????????? ??????????????");
            cv.put("movie_api_id", "659988");
            db.insert("movie", null, cv);
            cv.clear();

            cv.put("movie_id", 6);
            cv.put("title", "?????????? ?? ??????????????????: ?????????? ??????????????????");
            cv.put("movie_api_id", "893364");
            db.insert("movie", null, cv);
            cv.clear();

            // ????????????
            cv.put("session_id",1);
            cv.put("date", "15.01.2023");
            cv.put("time", "15:00");
            cv.put("hall_id", 1);
            cv.put("movie_id", 6);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",2);
            cv.put("date", "15.01.2023");
            cv.put("time", "18:30");
            cv.put("hall_id", 1);
            cv.put("movie_id", 6);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",3);
            cv.put("date", "15.01.2023");
            cv.put("time", "20:30");
            cv.put("hall_id", 1);
            cv.put("movie_id", 5);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",4);
            cv.put("date", "15.01.2023");
            cv.put("time", "12:00");
            cv.put("hall_id", 2);
            cv.put("movie_id", 4);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",5);
            cv.put("date", "15.01.2023");
            cv.put("time", "15:00");
            cv.put("hall_id", 2);
            cv.put("movie_id", 4);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",6);
            cv.put("date", "15.01.2023");
            cv.put("time", "17:00");
            cv.put("hall_id", 2);
            cv.put("movie_id", 2);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",7);
            cv.put("date", "15.01.2023");
            cv.put("time", "19:00");
            cv.put("hall_id", 2);
            cv.put("movie_id", 3);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",8);
            cv.put("date", "15.01.2023");
            cv.put("time", "21:00");
            cv.put("hall_id", 2);
            cv.put("movie_id", 3);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",9);
            cv.put("date", "15.01.2023");
            cv.put("time", "18:00");
            cv.put("hall_id", 3);
            cv.put("movie_id", 1);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",10);
            cv.put("date", "15.01.2023");
            cv.put("time", "21:30");
            cv.put("hall_id", 3);
            cv.put("movie_id", 1);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",11);
            cv.put("date", "15.01.2023");
            cv.put("time", "12:00");
            cv.put("hall_id", 4);
            cv.put("movie_id", 2);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",12);
            cv.put("date", "15.01.2023");
            cv.put("time", "14:00");
            cv.put("hall_id", 4);
            cv.put("movie_id", 2);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",13);
            cv.put("date", "15.01.2023");
            cv.put("time", "17:00");
            cv.put("hall_id", 4);
            cv.put("movie_id", 6);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",14);
            cv.put("date", "15.01.2023");
            cv.put("time", "19:30");
            cv.put("hall_id", 4);
            cv.put("movie_id", 6);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",15);
            cv.put("date", "15.01.2023");
            cv.put("time", "21:00");
            cv.put("hall_id", 5);
            cv.put("movie_id", 5);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",16);
            cv.put("date", "15.01.2023");
            cv.put("time", "23:30");
            cv.put("hall_id", 5);
            cv.put("movie_id", 1);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",16);
            cv.put("date", "15.01.2023");
            cv.put("time", "23:30");
            cv.put("hall_id", 5);
            cv.put("movie_id", 1);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",17);
            cv.put("date", "16.01.2023");
            cv.put("time", "15:00");
            cv.put("hall_id", 1);
            cv.put("movie_id", 2);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",18);
            cv.put("date", "16.01.2023");
            cv.put("time", "18:00");
            cv.put("hall_id", 1);
            cv.put("movie_id", 2);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",19);
            cv.put("date", "16.01.2023");
            cv.put("time", "21:00");
            cv.put("hall_id", 1);
            cv.put("movie_id", 3);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",20);
            cv.put("date", "16.01.2023");
            cv.put("time", "16:00");
            cv.put("hall_id", 2);
            cv.put("movie_id", 3);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",21);
            cv.put("date", "16.01.2023");
            cv.put("time", "18:30");
            cv.put("hall_id", 2);
            cv.put("movie_id", 1);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",22);
            cv.put("date", "16.01.2023");
            cv.put("time", "21:00");
            cv.put("hall_id", 2);
            cv.put("movie_id", 1);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",23);
            cv.put("date", "16.01.2023");
            cv.put("time", "18:00");
            cv.put("hall_id", 3);
            cv.put("movie_id", 5);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",24);
            cv.put("date", "16.01.2023");
            cv.put("time", "21:00");
            cv.put("hall_id", 3);
            cv.put("movie_id", 5);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",25);
            cv.put("date", "16.01.2023");
            cv.put("time", "13:00");
            cv.put("hall_id", 4);
            cv.put("movie_id", 4);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",26);
            cv.put("date", "16.01.2023");
            cv.put("time", "15:30");
            cv.put("hall_id", 4);
            cv.put("movie_id", 4);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",27);
            cv.put("date", "16.01.2023");
            cv.put("time", "18:00");
            cv.put("hall_id", 4);
            cv.put("movie_id", 3);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",28);
            cv.put("date", "16.01.2023");
            cv.put("time", "21:30");
            cv.put("hall_id", 4);
            cv.put("movie_id", 3);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",29);
            cv.put("date", "16.01.2023");
            cv.put("time", "20:00");
            cv.put("hall_id", 5);
            cv.put("movie_id", 3);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",30);
            cv.put("date", "16.01.2023");
            cv.put("time", "22:30");
            cv.put("hall_id", 5);
            cv.put("movie_id", 1);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",30);
            cv.put("date", "16.01.2023");
            cv.put("time", "01:00");
            cv.put("hall_id", 5);
            cv.put("movie_id", 1);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",31);
            cv.put("date", "17.01.2023");
            cv.put("time", "18:00");
            cv.put("hall_id", 1);
            cv.put("movie_id", 2);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",32);
            cv.put("date", "17.01.2023");
            cv.put("time", "21:00");
            cv.put("hall_id", 1);
            cv.put("movie_id", 2);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",33);
            cv.put("date", "17.01.2023");
            cv.put("time", "17:00");
            cv.put("hall_id", 2);
            cv.put("movie_id", 3);
            db.insert("session", null, cv);
            cv.clear();

            cv.put("session_id",34);
            cv.put("date", "17.01.2023");
            cv.put("time", "20:00");
            cv.put("hall_id", 2);
            cv.put("movie_id", 3);
            db.insert("session", null, cv);
            cv.clear();

            //users
            cv.put("user_id", 1);
            cv.put("login", "daria@gmail.com");
            cv.put("password", "d_pswd_123");
            db.insert("user", null, cv);
            cv.clear();

            cv.put("user_id", 2);
            cv.put("login", "maria@gmail.com");
            cv.put("password", "m_pswd_123");
            db.insert("user", null, cv);
            cv.clear();

            // tickets
            cv.put("ticket_id", 1);
            cv.put("session_id", 6);
            cv.put("seat_id", 27);
            cv.put("user_id", 1);
            db.insert("ticket", null, cv);
            cv.clear();

            cv.put("ticket_id", 2);
            cv.put("session_id", 6);
            cv.put("seat_id", 28);
            cv.put("user_id", 1);
            db.insert("ticket", null, cv);
            cv.clear();

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
