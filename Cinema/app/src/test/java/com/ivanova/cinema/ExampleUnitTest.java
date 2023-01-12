package com.ivanova.cinema;

import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

import static org.junit.Assert.*;

import com.ivanova.cinema.Model.Entities.FilmSession;
import com.ivanova.cinema.Model.Entities.Seat;
import com.ivanova.cinema.Model.Entities.SeatUI;
import com.ivanova.cinema.Model.Entities.Session;
import com.ivanova.cinema.View.CinemaSessionsView.CinemaSessions;
import com.ivanova.cinema.View.LoginView.LoginFragment;
import com.ivanova.cinema.View.SessionSeatsView.SessionSeats;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {

    @Test
    public void convertToDateStringTest() {
        assertEquals("15.12.2022", CinemaSessions.convertToDateString(15, 12, 2022));
        assertEquals("01.01.2023", CinemaSessions.convertToDateString(1, 1, 2023));
        assertEquals("06.01.2010", CinemaSessions.convertToDateString(6, 1, 2010));
    }

    @Test
    public void filmSessionTest() {
        ArrayList<Session> sessions = new ArrayList<>();
        sessions.add(new Session(1, "13.01.2023", "15:00", 1,
                1, "Avatar", "76600"));

        FilmSession filmSession = new FilmSession(1, "Avatar", 76600,
                sessions);

        assertEquals(1, filmSession.getSessions().size());
        assertEquals(filmSession.getFilmId(), filmSession.getSessions().get(0).getFilmId());
    }

    @Test
    public void signInValidationTest() {
        assertTrue(LoginFragment.isValidLogin("daria@gmail.com"));
        assertFalse(LoginFragment.isValidLogin("daria@gmail"));
        assertFalse(LoginFragment.isValidLogin("daria%gmail.com"));

        assertFalse(LoginFragment.isValidPassword("412352"));
        assertTrue(LoginFragment.isValidPassword("sdfREG24"));
        assertFalse(LoginFragment.isValidPassword("111"));
    }

    @Test
    public void fromSessionsToFilmSessionsTest() {
        Session session1 = new Session(1, "19.01.2023", "12:00", 1, 1, "Avatar", "1111");
        Session session2 = new Session(2, "20.01.2023", "13:00", 1, 2, "Friends", "2222");
        Session session3 = new Session(3, "21.01.2023", "14:00", 2, 1, "Avatar", "1111");
        Session session4 = new Session(4, "22.01.2023", "15:00", 2, 2, "Friends", "2222");

        ArrayList<Session> sessions = new ArrayList<>();
        sessions.add(session1);
        sessions.add(session2);
        sessions.add(session3);
        sessions.add(session4);

        ArrayList<Session> sessionsToFilmAvatar = new ArrayList<>();
        sessionsToFilmAvatar.add(session1);
        sessionsToFilmAvatar.add(session3);

        ArrayList<Session> sessionsToFilmFriends = new ArrayList<>();
        sessionsToFilmFriends.add(session2);
        sessionsToFilmFriends.add(session4);

        ArrayList<FilmSession> filmSessions = new ArrayList<>();
        filmSessions.add(new FilmSession(1, "Avatar", 1111, sessionsToFilmAvatar));
        filmSessions.add(new FilmSession(2, "Friends", 2222, sessionsToFilmFriends));

        assertEquals(filmSessions.size(), CinemaSessions.fromSessionsToFilmSessions(sessions).size());
        assertEquals(filmSessions.get(0).getFilmName(), CinemaSessions.fromSessionsToFilmSessions(sessions).get(0).getFilmName());
        assertEquals(filmSessions.get(1).getFilmName(), CinemaSessions.fromSessionsToFilmSessions(sessions).get(1).getFilmName());
        assertEquals(filmSessions.get(0).getSessions().size(), CinemaSessions.fromSessionsToFilmSessions(sessions).get(0).getSessions().size());
        assertEquals(filmSessions.get(1).getSessions().size(), CinemaSessions.fromSessionsToFilmSessions(sessions).get(1).getSessions().size());
        assertEquals(filmSessions.get(0).getSessions().get(0), CinemaSessions.fromSessionsToFilmSessions(sessions).get(0).getSessions().get(0));
    }

    @Test
    public void fromSeatToSeatUITest() {
        Seat seat1 = new Seat(1, 1, 1, 1.0, 1);
        Seat seat2 = new Seat(2, 2, 2, 2.0, 2);
        Seat seat3 = new Seat(3, 3, 3, 3.0, 3);

        ArrayList<Seat> freeSeats = new ArrayList<>();
        freeSeats.add(seat1);
        freeSeats.add(seat3);

        SeatUI seatUI1 = new SeatUI(1, 1, 1, 1.0, 1, true);
        SeatUI seatUI2 = new SeatUI(2, 2, 2, 2.0, 2, false);
        SeatUI seatUI3 = new SeatUI(3, 3, 3, 3.0, 3, true);

        assertEquals(seatUI1.getFree(), SessionSeats.fromSeatToSeatUI(seat1, freeSeats).getFree());
        assertEquals(seatUI2.getFree(), SessionSeats.fromSeatToSeatUI(seat2, freeSeats).getFree());
        assertEquals(seatUI3.getFree(), SessionSeats.fromSeatToSeatUI(seat3, freeSeats).getFree());
    }

    @Test
    public void addFilmSessionTest() {
        Session session1 = new Session(1, "19.01.2023", "12:00", 1, 1, "Avatar", "1111");
        Session session2 = new Session(2, "20.01.2023", "13:00", 1, 2, "Friends", "2222");

        ArrayList<Session> sessions = new ArrayList<>();
        sessions.add(session1);
        sessions.add(session2);

        ArrayList<Session> sessionsCopy = new ArrayList<>();
        sessionsCopy.add(session1);

        FilmSession filmSession = new FilmSession(1,"Avatar", 1111, sessions);
        FilmSession filmSessionCopy = new FilmSession(1,"Avatar", 1111, sessionsCopy);

        filmSessionCopy.addSession(session2);

        assertEquals(filmSession.getSessions().size(), filmSessionCopy.getSessions().size());
        assertEquals(filmSession.getSessions().get(1), filmSessionCopy.getSessions().get(1));
    }
}