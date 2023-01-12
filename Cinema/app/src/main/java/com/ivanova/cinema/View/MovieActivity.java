package com.ivanova.cinema.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ivanova.cinema.Model.Entities.Movie;
import com.ivanova.cinema.Model.TMDBConnector;
import com.ivanova.cinema.R;
import com.ivanova.cinema.View.CinemaListView.CinemaList;
import com.ivanova.cinema.View.CinemaSessionsView.CinemaSessions;
import com.ivanova.cinema.View.LoginView.Login;
import com.ivanova.cinema.View.MyTicketsView.MyTickets;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

/**
 * Класс активности Movie
 * Можно перейти следующим образом
 * Intent intent = new Intent(MainActivity.this, MovieActivity.class);
 * intent.putExtra("movie_id", "76600");
 * startActivity(intent);
 *
 * Можно передавать название фильма, но тогда все равно нужно достать id из бд
 */
public class MovieActivity extends AppCompatActivity {

    private BottomNavigationView menu;
    private SharedPreferences pref;

//    ImageView poster;
//    TextView movieName;
//    TextView year;
//    TextView country;
//    TextView genres;
//    TextView overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        pref = getSharedPreferences("user_login", MODE_PRIVATE);
        setUpMenu();

        Intent intent = this.getIntent();

//        poster = findViewById(R.id.poster);
//        movieName = findViewById(R.id.movieNameTxtField);
//        year = findViewById(R.id.yearTxtField);
//        country = findViewById(R.id.countryTxtField);
//        genres = findViewById(R.id.genresTxtField);
//        overview = findViewById(R.id.overviewTxtField);

        if (intent != null) {
            String movieID = intent.getStringExtra("movie_id");

            TMDBConnector connector = new TMDBConnector(movieID, this, getLifecycle());
            connector.getBackdrops();
            connector.getTrailer();
        }
    }

    private void setUpMenu() {
        // ---------------------- Menu -----------------------------
        menu = findViewById(R.id.bottomNavigation);
        menu.getMenu().setGroupCheckable(0, false, true);
        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cinemasItem: {
                        Intent intent = new Intent(MovieActivity.this, CinemaList.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.myTicketsItem: {
                        Intent intent = new Intent(MovieActivity.this, MyTickets.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.exitItem: {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(MovieActivity.this, Login.class);
                        startActivity(intent);
                        return true;
                    }
                }
                return true;
            }
        });
    }
}
