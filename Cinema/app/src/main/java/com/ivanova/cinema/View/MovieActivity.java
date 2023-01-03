package com.ivanova.cinema.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ivanova.cinema.Model.Entities.Movie;
import com.ivanova.cinema.Model.TMDBConnector;
import com.ivanova.cinema.R;
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
}
