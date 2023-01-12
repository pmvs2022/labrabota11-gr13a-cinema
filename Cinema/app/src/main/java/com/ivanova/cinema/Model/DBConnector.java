package com.ivanova.cinema.Model;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ivanova.cinema.Model.Entities.Cinema;
import com.ivanova.cinema.Model.Entities.Seat;
import com.ivanova.cinema.Model.Entities.Session;
import com.ivanova.cinema.Model.Entities.Ticket;

import java.util.ArrayList;

public class DBConnector {

    private static DBOpenHelper dbOpenHelper = null;
    private static SQLiteDatabase db = null;
    private Context context;

    public DBConnector(Context context) {
        if (dbOpenHelper == null) {
            dbOpenHelper = new DBOpenHelper(context);
            db = null;
        }
        if (db == null) {
            try {
                db = dbOpenHelper.getWritableDatabase();
            } catch (SQLException exc) {
                db = dbOpenHelper.getReadableDatabase();
            }
        }
        this.context = context;
    }

    public boolean userExist(String login, String password) {
        Cursor cursor = db.query(
                "user",
                null,
                ("login = ? and password = ?"),
                new String[]{login, password},
                null,
                null,
                null
        );

        boolean successful = cursor.getCount() != 0;
        if (successful) {
            SharedPreferences pref = context.getSharedPreferences("user_login", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("login", login);
            editor.commit();
        }
        return successful;
    }

    public boolean registerUser(String login, String password) {
        boolean insertSuccessful;
        try {
            db.beginTransaction();

            if (loginExists(login)) {
                return false;
            }

            ContentValues cv = new ContentValues();
            cv.put("login", login);
            cv.put("password", password);
            insertSuccessful = db.insert("user", null, cv) != -1;

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        SharedPreferences pref = context.getSharedPreferences("user_login", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("login", login);
        editor.commit();
        return insertSuccessful;
    }

    public ArrayList<Cinema> getCinemas() {
        Cursor cursor = db.query("cinema", null, null, null, null, null, null);

        int cinemaIdColIndex = cursor.getColumnIndex("cinema_id");
        int titleColIndex = cursor.getColumnIndex("title");
        int descriptionColIndex = cursor.getColumnIndex("description");

        ArrayList<Cinema> cinemaList = new ArrayList<>();
        Cinema nextCinema;

        if (cursor.moveToFirst()) {
            do {
                nextCinema = new Cinema(
                        cursor.getInt(cinemaIdColIndex),
                        cursor.getString(titleColIndex),
                        cursor.getString(descriptionColIndex));
                cinemaList.add(nextCinema);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cinemaList;

        /*ArrayList<Cinema> cinemas = new ArrayList<>();
        cinemas.add(new Cinema(1,"Аврора", "«Аврора» – это современный кинотеатр, афиша которого полна самых разнообразных картин. Любите ли вы беззаботные комедии, будоражащие кровь триллеры, волнующие мелодрамы, сказочное фэнтези, захватывающие дух приключения, меланхоличные или грустные драмы – выбирайте приглянувшийся фильм и наслаждайтесь зрелищем."));
        cinemas.add(new Cinema(2,"Беларусь", "Кинотеатр «Беларусь» вновь открыл свои двери для зрителей в 2008 году - после капитальной реконструкции. Здесь работают пять кинозалов, которые вмещают в себя от 145 до 280 зрителей. В каждом зале установлены комфортные кресла, а проходы между рядами по-европейски большие."));
        cinemas.add(new Cinema(3,"Мир", "Кинематограф является довольно внушительной частью культуры многих стран мира. Сегодня киноиндустрия достигла таких масштабов, что практически ежедневно появляются новые премьеры. Конечно, уследить за всеми новинками нелегко, да и большинству зрителей это не нужно, но каждый из нас желает увидеть картину, которая бы понравилась и запомнилась."));
        cinemas.add(new Cinema(4,"Октябрь", "Посещение кинотеатра – самый простой способ проведения свободного времени со своей семьей, друзьями или любимым человеком. С момента появления кинотеатры никогда не были обделены вниманием зрителей. И это неудивительно, ведь только здесь можно насладиться великолепным качеством картин, увидеть премьеры на большом экране и полностью погрузиться в сюжет, проживая вместе с героями все радостные и печальные моменты."));
        cinemas.add(new Cinema(5,"Ракета", "«Ракета» является одним из самых старых кинотеатров столицы. Интересно, что здание было построено после того, как в космос был запущен первый искусственный спутник – в 1958 году. Долгое время кинотеатр существовал в своем первозданном виде и лишь в 2012 году подвергся реставрации."));
        return cinemas;*/
    }

    public ArrayList<Session> getSessions(Integer cinemaId, String date) {
        String tableJoin = "session join hall using(hall_id) join movie using(movie_id)";
        String[] selectedColumns = new String[]{
                "session_id",
                "date",
                "time",
                "session.hall_id as hall_id",
                "movie_id",
                "movie.title as movie_title",
                "movie_api_id"
        };

        Cursor cursor = db.query(
                tableJoin,
                selectedColumns,
                "date = ? and cinema_id = ?",
                new String[]{date, String.valueOf(cinemaId)},
                null,
                null,
                null);

        int sessionIdColIndex = cursor.getColumnIndex("session_id");
        int dateColIndex = cursor.getColumnIndex("date");
        int timeColIndex = cursor.getColumnIndex("time");
        int hallIdColIndex = cursor.getColumnIndex("hall_id");
        int movieIdColIndex = cursor.getColumnIndex("movie_id");
        int movieTitleColIndex = cursor.getColumnIndex("movie_title");
        int movieApiIDColIndex = cursor.getColumnIndex("movie_api_id");

        ArrayList<Session> sessionList = new ArrayList<>();
        Session nextSession;

        if (cursor.moveToFirst()) {
            do {
                nextSession = new Session(
                        cursor.getInt(sessionIdColIndex),
                        cursor.getString(dateColIndex),
                        cursor.getString(timeColIndex),
                        cursor.getInt(hallIdColIndex),
                        cursor.getInt(movieIdColIndex),
                        cursor.getString(movieTitleColIndex),
                        cursor.getString(movieApiIDColIndex));
                sessionList.add(nextSession);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sessionList;
    }

    public ArrayList<Seat> getAllSeats(Integer sessionId) {
        Integer hallId = getSessionHallId(sessionId);

        Cursor cursor = db.query(
                "seat",
                null,
                "hall_id = ?",
                new String[]{hallId.toString()},
                null,
                null,
                null
        );

        int seatIdColIndex = cursor.getColumnIndex("seat_id");
        int seatNumberColIndex = cursor.getColumnIndex("seat_number");
        int rowNumberColIndex = cursor.getColumnIndex("row_number");
        int priceColIndex = cursor.getColumnIndex("price");
        int hallIdColIndex = cursor.getColumnIndex("hall_id");

        ArrayList<Seat> seatsList = new ArrayList<>();
        Seat nextSeat;

        if (cursor.moveToFirst()) {
            do {
                nextSeat = new Seat(
                        cursor.getInt(seatIdColIndex),
                        cursor.getInt(seatNumberColIndex),
                        cursor.getInt(rowNumberColIndex),
                        cursor.getDouble(priceColIndex),
                        cursor.getInt(hallIdColIndex));
                seatsList.add(nextSeat);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return seatsList;
    }

    public ArrayList<Seat> getFreeSeats(Integer sessionId) {
        Integer hallId = getSessionHallId(sessionId);
        String sqlQuery = "select * from seat where hall_id = " + hallId.toString() + " and not exists "
                + "( select * from ticket where seat_id = seat.seat_id );";
        Cursor cursor = db.rawQuery(sqlQuery, null);

        int seatIdColIndex = cursor.getColumnIndex("seat_id");
        int seatNumberColIndex = cursor.getColumnIndex("seat_number");
        int rowNumberColIndex = cursor.getColumnIndex("row_number");
        int priceColIndex = cursor.getColumnIndex("price");
        int hallIdColIndex = cursor.getColumnIndex("hall_id");

        ArrayList<Seat> freeSeats = new ArrayList<>();
        Seat nextSeat;

        if (cursor.moveToFirst()) {
            do {
                nextSeat = new Seat(
                        cursor.getInt(seatIdColIndex),
                        cursor.getInt(seatNumberColIndex),
                        cursor.getInt(rowNumberColIndex),
                        cursor.getDouble(priceColIndex),
                        cursor.getInt(hallIdColIndex));
                freeSeats.add(nextSeat);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return freeSeats;
    }

    public boolean buyTicket(Integer seatId, Integer sessionId) {
        boolean buySuccessfully = false;
        try {
            db.beginTransaction();

            if (!seatIsFree(seatId, sessionId)) {
                return false;
            }

            Integer curUserId = getCurrentUserId();

            ContentValues cv = new ContentValues();
            cv.put("session_id", sessionId);
            cv.put("seat_id", seatId);
            cv.put("user_id", curUserId);

            db.setTransactionSuccessful();
            buySuccessfully = db.insert("ticket", null, cv) != -1;
        } finally {
            db.endTransaction();
        }
        return buySuccessfully;
    }

    public ArrayList<Ticket> getMyTickets() {
        Integer curUserID = getCurrentUserId();
        Cursor cursor = db.query(
                "ticket",
                null,
                "user_id = ?",
                new String[]{curUserID.toString()},
                null,
                null,
                null
        );

        int ticketIdColIndex = cursor.getColumnIndex("ticket_id");
        int seatIdColIndex = cursor.getColumnIndex("seat_id");
        int sessionIdColIndex = cursor.getColumnIndex("session_id");

        ArrayList<Ticket> myTicketsList = new ArrayList<>(cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                myTicketsList.add(getTicket(
                        cursor.getInt(ticketIdColIndex),
                        cursor.getInt(sessionIdColIndex),
                        cursor.getInt(seatIdColIndex)
                ));
            } while (cursor.moveToNext());
        }

        return myTicketsList;
    }

    public String getMovieApiId(Integer movieID){
        Cursor cursor = db.query(
                "movie",
                new String[]{"movie_api_id"},
                "movie_id = ?",
                new String[]{movieID.toString()},
                null,
                null,
                null
        );

        int movieApiIdColIndex = cursor.getColumnIndex("movie_api_id");
        String movieApiId = "";
        if(cursor.moveToFirst()){
            movieApiId = cursor.getString(movieApiIdColIndex);
        }
        return movieApiId;
    }

    public String getHallName(Integer hallId) {
        Cursor cursor = db.query(
                "hall",
                new String[]{"title"},
                "hall_id = ?",
                new String[]{hallId.toString()},
                null,
                null,
                null
        );

        int titleColIdx = cursor.getColumnIndex("title");
        if (cursor.moveToFirst()) {
            return cursor.getString(titleColIdx);
        }
        return null;
    }

    public String getCinemaName(Integer hallId) {
        Cursor cursor = db.query(
                "hall",
                new String[]{"cinema_id"},
                "hall_id = ?",
                new String[]{hallId.toString()},
                null,
                null,
                null
        );

        int columnIdx = cursor.getColumnIndex("cinema_id");
        Integer cinemaId;
        if (cursor.moveToFirst()) {
            cinemaId = cursor.getInt(columnIdx);
        } else {
            return null;
        }

        cursor = db.query(
                "cinema",
                new String[]{"title"},
                "cinema_id = ?",
                new String[]{cinemaId.toString()},
                null,
                null,
                null
        );

        columnIdx = cursor.getColumnIndex("title");
        if (cursor.moveToFirst()) {
            return cursor.getString(columnIdx);
        }
        return null;
    }

    private boolean loginExists(String login) {
        Cursor cursor = db.query(
                "user",
                null,
                ("login = ?"),
                new String[]{login},
                null,
                null,
                null
        );
        return cursor.getCount() != 0;
    }

    private Integer getSessionHallId(Integer sessionId) {
        Cursor cursor = db.query(
                "session",
                new String[]{"hall_id"},
                "session_id = ?",
                new String[]{String.valueOf(sessionId)},
                null,
                null,
                null
        );

        int hallIdSessionColIndex = cursor.getColumnIndex("hall_id");
        cursor.moveToFirst();
        return cursor.getInt(hallIdSessionColIndex);
    }

    private boolean seatIsFree(Integer seatId, Integer sessionId) {
        Cursor cursor = db.query(
                "ticket",
                null,
                "seat_id = ? and session_id = ?",
                new String[]{seatId.toString(), sessionId.toString()},
                null,
                null,
                null
        );
        return cursor.getCount() == 0;
    }

    private Integer getCurrentUserId() {
        SharedPreferences pref = context.getSharedPreferences("user_login", MODE_PRIVATE);
        String curUserLogin = pref.getString("login", "");

        Cursor cursor = db.query(
                "user",
                new String[]{"user_id"},
                "login = ?",
                new String[]{curUserLogin},
                null,
                null,
                null
        );

        cursor.moveToFirst();
        int userIdColIndex = cursor.getColumnIndex("user_id");
        return cursor.getInt(userIdColIndex);
    }

    private Ticket getTicket(Integer ticketId, Integer sessionId, Integer seatId) {
        Ticket ticket = new Ticket(ticketId, null, null, null, null);

        ticket.setSession(getSession(sessionId));
        ticket.setSeat(getSeat(seatId));

        Integer hallId = getSessionHallId(sessionId);
        Cursor cursor = db.query(
                "hall join cinema using(cinema_id)",
                new String[]{"cinema.title as cinema_title", "hall.title as hall_title"},
                "hall_id = ?",
                new String[]{hallId.toString()},
                null,
                null,
                null
        );

        int cinemaTitleColIndex = cursor.getColumnIndex("cinema_title");
        int hallTitleColIndex = cursor.getColumnIndex("hall_title");

        if (cursor.moveToFirst()) {
            ticket.setHallName(cursor.getString(hallTitleColIndex));
            ticket.setCinemaName(cursor.getString(cinemaTitleColIndex));
        }

        return ticket;
    }

    private Seat getSeat(Integer seatId) {
        Cursor cursor = db.query(
                "seat",
                null,
                "seat_id = ?",
                new String[]{seatId.toString()},
                null,
                null,
                null
        );

        int seatIdColIndex = cursor.getColumnIndex("seat_id");
        int seatNumberColIndex = cursor.getColumnIndex("seat_number");
        int rowNumberColIndex = cursor.getColumnIndex("row_number");
        int priceColIndex = cursor.getColumnIndex("price");
        int hallIdColIndex = cursor.getColumnIndex("hall_id");

        Seat seat = null;
        if (cursor.moveToFirst()) {
            seat = new Seat(
                    cursor.getInt(seatIdColIndex),
                    cursor.getInt(seatNumberColIndex),
                    cursor.getInt(rowNumberColIndex),
                    cursor.getDouble(priceColIndex),
                    cursor.getInt(hallIdColIndex));
        }
        cursor.close();
        return seat;
    }

    private Session getSession(Integer sessionId) {
        String sessionMovieJoin = "session join movie using(movie_id)";
        String[] selectedColumns = new String[]{
                "session_id",
                "date",
                "time",
                "hall_id",
                "movie_id",
                "movie.title as movie_title",
                "movie_api_id"
        };

        Cursor cursor = db.query(
                sessionMovieJoin,
                selectedColumns,
                "session_id = ?",
                new String[]{sessionId.toString()},
                null,
                null,
                null);

        int sessionIdColIndex = cursor.getColumnIndex("session_id");
        int dateColIndex = cursor.getColumnIndex("date");
        int timeColIndex = cursor.getColumnIndex("time");
        int hallIdColIndex = cursor.getColumnIndex("hall_id");
        int movieIdColIndex = cursor.getColumnIndex("movie_id");
        int movieTitleColIndex = cursor.getColumnIndex("movie_title");
        int movieApiIdColIndex = cursor.getColumnIndex("movie_api_id");

        Session session = null;

        if (cursor.moveToFirst()) {
            session = new Session(
                    cursor.getInt(sessionIdColIndex),
                    cursor.getString(dateColIndex),
                    cursor.getString(timeColIndex),
                    cursor.getInt(hallIdColIndex),
                    cursor.getInt(movieIdColIndex),
                    cursor.getString(movieTitleColIndex),
                    cursor.getString(movieApiIdColIndex));
        }
        cursor.close();
        return session;
    }
}
