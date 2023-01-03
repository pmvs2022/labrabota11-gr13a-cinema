package com.ivanova.cinema.Model;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.ivanova.cinema.Model.Entities.Movie;
import com.ivanova.cinema.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TMDBConnector {

    private static final String TAG = "MyApp";

    private static final String apiKey = "07da88d5b91ff7a91458111fac8f67a6";
    public static String posterPath = "https://image.tmdb.org/t/p/w500/";
    public static String imagePath = "https://image.tmdb.org/t/p/w780/";

    private final String movie_id;
    private Movie movie;
    private final Activity activity;
    private final androidx.lifecycle.Lifecycle lifecycle;

    ImageView poster;
    TextView movieName;
    TextView year;
    TextView country;
    TextView genres;
    TextView overview;

    public TMDBConnector(String id, Activity activity, androidx.lifecycle.Lifecycle lifecycle) {
        this.movie_id = id;

        String request = "https://api.themoviedb.org/3/movie/" + id
                + "?api_key=" + apiKey + "&language=en-US";

        this.activity = activity;
        this.lifecycle = lifecycle;

        Log.d(TAG, "In constructor");
        GetMovieData getMovieData = new GetMovieData();
        getMovieData.execute(request);
        Log.d(TAG, "Execute done");
    }

    public void getTrailer() {
        GetMovieTrailer getMovieTrailer = new GetMovieTrailer();
        getMovieTrailer.execute("https://api.themoviedb.org/3/movie/"+ movie_id
                + "/videos?api_key=" + apiKey + "&language=en-US");
    }

    public void getBackdrops() {
        GetMovieBackdrops getMovieBackdrops = new GetMovieBackdrops();
        getMovieBackdrops.execute("https://api.themoviedb.org/3/movie/" + movie_id
                + "/images?api_key=" + apiKey + "&language=en-US");
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public class GetMovieData extends AsyncTask<String, String, String>  {

        @Override
        protected String doInBackground(String... strings) {
            return loadData(strings);
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
// o5F8MOz_IDw
                Movie new_movie = new Movie(movie_id);
                new_movie.setDate(jsonObject.getString("release_date"));
                new_movie.setOverview(jsonObject.getString("overview"));
                new_movie.setName(jsonObject.getString("title"));
                new_movie.setPoster_path(jsonObject.getString("poster_path"));

                JSONArray jsonGenres = jsonObject.getJSONArray("genres");
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(jsonGenres.getJSONObject(0).getString("name"));
                for (int i = 1; i < jsonGenres.length(); i++) {
                    stringBuilder.append(", ").append(jsonGenres.getJSONObject(i).getString("name"));
                }
                new_movie.setGenres(stringBuilder.toString());

                JSONArray jsonCountries = jsonObject.getJSONArray("production_countries");
                stringBuilder = new StringBuilder();

                stringBuilder.append(jsonCountries.getJSONObject(0).getString("iso_3166_1"));
                for (int i = 1; i < jsonCountries.length(); i++) {
                    stringBuilder.append(", ").append(jsonCountries.getJSONObject(i).getString("iso_3166_1"));
                }
                new_movie.setCountry(stringBuilder.toString());

                setMovie(new_movie);

                Log.d(TAG, "Parse done, search elements");
                poster = activity.findViewById(R.id.poster);
                movieName = activity.findViewById(R.id.movieNameTxtField);
                year = activity.findViewById(R.id.yearTxtField);
                country = activity.findViewById(R.id.countryTxtField);
                genres = activity.findViewById(R.id.genresTxtField);
                overview = activity.findViewById(R.id.overviewTxtField);

                movieName.setText(movie.getName());
                year.setText(movie.getDate());
                country.setText(movie.getCountry());
                genres.setText(movie.getGenres());
                overview.setText(movie.getOverview());

                if (movie.getPoster_path() != null) {
                    Glide.with(activity)
                        .load(TMDBConnector.posterPath + movie.getPoster_path())
                        .into(poster);
                }
                Log.d(TAG, "All done");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class GetMovieTrailer extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            return loadData(strings);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonResults = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonResults.length(); i++) {
                    JSONObject curr = jsonResults.getJSONObject(i);
                    String site = curr.getString("site");
                    String type = curr.getString("type");

                    if (site.equals("YouTube") && type.equals("Trailer")) {
                        YouTubePlayerView youTubePlayerView = activity.findViewById(R.id.youtube_player_view);
                        lifecycle.addObserver(youTubePlayerView);

                        String videoId = curr.getString("key");
                        Log.d(TAG, videoId);
                        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                youTubePlayer.cueVideo(videoId, 0);
                            }
                        });
                        break;
                    };
                }

                Log.d(TAG, "All done");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class GetMovieBackdrops extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            return loadData(strings);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonBackdrops = jsonObject.getJSONArray("backdrops");

                ImageView []imageViews = new ImageView[3];
                imageViews[0] = activity.findViewById(R.id.image1);
                imageViews[1] = activity.findViewById(R.id.image2);
                imageViews[2] = activity.findViewById(R.id.image3);

                Log.d(TAG, String.valueOf(jsonBackdrops.length()));
                for (int i = 0; i < jsonBackdrops.length() && i < 3; i++) {
                    JSONObject curr = jsonBackdrops.getJSONObject(i);
                    String file_path = curr.getString("file_path");
                    Log.d(TAG, file_path);
                    
                    Glide.with(activity)
                            .load(TMDBConnector.imagePath + file_path)
                            .into(imageViews[i]);
                }

                Log.d(TAG, "All done");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static String loadData(String ... strings) {
        String current = "";

        try {
            URL url;
            HttpURLConnection httpURLConnection = null;

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream is = httpURLConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int data = isr.read();
                while(data != -1) {

                    current += (char) data;
                    data = isr.read();

                }

                return current;

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return current;
    }

}
