package com.ivanova.cinema.View.CinemaSessionsView;

import com.ivanova.cinema.Model.Entities.FilmSession;

public interface SessionsRecyclerViewInterface {
    void onSessionItemClick(int position, FilmSession filmSession);
}
