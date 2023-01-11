package com.ivanova.cinema;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import com.ivanova.cinema.View.CinemaSessionsView.CinemaSessions;

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
}