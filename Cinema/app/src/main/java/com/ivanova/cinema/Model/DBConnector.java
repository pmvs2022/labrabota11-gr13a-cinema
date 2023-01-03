package com.ivanova.cinema.Model;

import com.ivanova.cinema.Model.Entities.Cinema;
import com.ivanova.cinema.Model.Entities.Seat;
import com.ivanova.cinema.Model.Entities.Session;
import com.ivanova.cinema.Model.Entities.Ticket;

import java.util.ArrayList;

public class DBConnector {

    public static boolean userExist(String login, String password) {
        // To do...
        return true;
    }

    public static boolean registerUser(String login, String password) {
        // To do...
        return true;
    }

    public static ArrayList<Cinema> getCinemas() {
        // To do...

        ArrayList<Cinema> cinemas = new ArrayList<>();
        cinemas.add(new Cinema(1,"Аврора", "«Аврора» – это современный кинотеатр, афиша которого полна самых разнообразных картин. Любите ли вы беззаботные комедии, будоражащие кровь триллеры, волнующие мелодрамы, сказочное фэнтези, захватывающие дух приключения, меланхоличные или грустные драмы – выбирайте приглянувшийся фильм и наслаждайтесь зрелищем."));
        cinemas.add(new Cinema(2,"Беларусь", "Кинотеатр «Беларусь» вновь открыл свои двери для зрителей в 2008 году - после капитальной реконструкции. Здесь работают пять кинозалов, которые вмещают в себя от 145 до 280 зрителей. В каждом зале установлены комфортные кресла, а проходы между рядами по-европейски большие."));
        cinemas.add(new Cinema(3,"Мир", "Кинематограф является довольно внушительной частью культуры многих стран мира. Сегодня киноиндустрия достигла таких масштабов, что практически ежедневно появляются новые премьеры. Конечно, уследить за всеми новинками нелегко, да и большинству зрителей это не нужно, но каждый из нас желает увидеть картину, которая бы понравилась и запомнилась."));
        cinemas.add(new Cinema(4,"Октябрь", "Посещение кинотеатра – самый простой способ проведения свободного времени со своей семьей, друзьями или любимым человеком. С момента появления кинотеатры никогда не были обделены вниманием зрителей. И это неудивительно, ведь только здесь можно насладиться великолепным качеством картин, увидеть премьеры на большом экране и полностью погрузиться в сюжет, проживая вместе с героями все радостные и печальные моменты."));
        cinemas.add(new Cinema(5,"Ракета", "«Ракета» является одним из самых старых кинотеатров столицы. Интересно, что здание было построено после того, как в космос был запущен первый искусственный спутник – в 1958 году. Долгое время кинотеатр существовал в своем первозданном виде и лишь в 2012 году подвергся реставрации."));
        return cinemas;
    }

    public static ArrayList<Session> getSessions(Integer cinemaId, String date) {
        // To do...
        return new ArrayList<>();
    }

    public static ArrayList<Seat> getAllSeats(Integer sessionId) {
        // To do...
        return new ArrayList<>();
    }

    public static ArrayList<Seat> getFreeSeats(Integer sessionId) {
        // To do...
        return new ArrayList<>();
    }

    public static boolean buyTicket(Integer seatId, Integer sessionId) {
        // To do...
        return true;
    }

    public static ArrayList<Ticket> getMyTickets() {
        // To do...
        return new ArrayList<>();
    }
}
