-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2023-01-12 11:46:48.992

-- tables
-- Table: cinema
CREATE TABLE cinema (
    cinema_id integer NOT NULL CONSTRAINT cinema_pk PRIMARY KEY,
    title Text() NOT NULL,
    description Text()
);

-- Table: hall
CREATE TABLE hall (
    hall_id integer NOT NULL CONSTRAINT hall_pk PRIMARY KEY,
    title Text() NOT NULL,
    cinema_id integer NOT NULL,
    cinema_cinema_id integer NOT NULL,
    CONSTRAINT hall_cinema FOREIGN KEY (cinema_cinema_id)
    REFERENCES cinema (cinema_id)
);

-- Table: movie
CREATE TABLE movie (
    movie_id integer NOT NULL CONSTRAINT movie_pk PRIMARY KEY,
    title Text() NOT NULL,
    movie_api_id Text() NOT NULL
);

-- Table: seat
CREATE TABLE seat (
    seat_id integer NOT NULL CONSTRAINT seat_pk PRIMARY KEY,
    seat_number integer NOT NULL,
    row_number integer NOT NULL,
    price Float() NOT NULL,
    hall_id integer NOT NULL,
    hall_hall_id integer NOT NULL,
    CONSTRAINT seat_hall FOREIGN KEY (hall_hall_id)
    REFERENCES hall (hall_id)
);

-- Table: session
CREATE TABLE session (
    session_id integer NOT NULL CONSTRAINT session_pk PRIMARY KEY,
    date Text() NOT NULL,
    time Text() NOT NULL,
    hall_id integer NOT NULL,
    movie_id integer NOT NULL,
    hall_hall_id integer NOT NULL,
    movie_movie_id integer NOT NULL,
    CONSTRAINT session_hall FOREIGN KEY (hall_hall_id)
    REFERENCES hall (hall_id),
    CONSTRAINT session_movie FOREIGN KEY (movie_movie_id)
    REFERENCES movie (movie_id)
);

-- Table: ticket
CREATE TABLE ticket (
    ticket_id integer NOT NULL CONSTRAINT ticket_pk PRIMARY KEY,
    session_id integer NOT NULL,
    seat_id integer NOT NULL,
    user_id integer NOT NULL,
    session_session_id integer NOT NULL,
    seat_seat_id integer NOT NULL,
    user_login Text() NOT NULL,
    CONSTRAINT ticket_session FOREIGN KEY (session_session_id)
    REFERENCES session (session_id),
    CONSTRAINT ticket_seat FOREIGN KEY (seat_seat_id)
    REFERENCES seat (seat_id),
    CONSTRAINT ticket_user FOREIGN KEY (user_login)
    REFERENCES user (login)
);

-- Table: user
CREATE TABLE user (
    login Text() NOT NULL CONSTRAINT user_pk PRIMARY KEY,
    password Text() NOT NULL
);

-- End of file.

