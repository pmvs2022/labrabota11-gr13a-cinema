package com.ivanova.cinema;

import org.junit.Test;

import static org.junit.Assert.*;

import com.ivanova.cinema.Model.Entities.FilmSession;
import com.ivanova.cinema.Model.Entities.Session;
import com.ivanova.cinema.View.CinemaSessionsView.CinemaSessions;
import com.ivanova.cinema.View.LoginView.LoginFragment;

import java.util.ArrayList;

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
}