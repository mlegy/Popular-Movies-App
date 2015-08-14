package com.melegy.movies.moviesapp;

import android.net.Uri;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by ahmad on 14/08/15.
 */
public class Movie {

    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_OVERVIEW = "overview";
    public static final String KEY_RELEASE_DATE = "release_date";
    public static final String KEY_POSTER = "poster_path";
    public static final String KEY_VOTE_AVERAGE = "vote_average";
    public static final String KEY_VOTE_COUNT = "vote_count";

    public long id;
    public String title;
    public String overview;
    public String release_date;
    public String poster_path;
    public double vote_average;
    public long vote_count;


    public Movie(long id, String title, String overview, String release_date, String poster_path, double vote_average, long vote_count) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public Movie(Bundle bundle) {
        this(
                bundle.getLong(KEY_ID),
                bundle.getString(KEY_TITLE),
                bundle.getString(KEY_OVERVIEW),
                bundle.getString(KEY_RELEASE_DATE),
                bundle.getString(KEY_POSTER),
                bundle.getDouble(KEY_VOTE_AVERAGE),
                bundle.getLong(KEY_VOTE_COUNT)
        );
    }

    public Bundle toBundle() {

        Bundle bundle = new Bundle();

        bundle.putLong(KEY_ID, id);
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_OVERVIEW, overview);
        bundle.putString(KEY_RELEASE_DATE, release_date);
        bundle.putString(KEY_POSTER, poster_path);
        bundle.putDouble(KEY_VOTE_AVERAGE, vote_average);
        bundle.putLong(KEY_VOTE_COUNT, vote_count);

        return bundle;
    }

    public static Movie deserialize(JSONObject movieJsonObject) throws JSONException {

        Long id = movieJsonObject.getLong(KEY_ID);
        String title = movieJsonObject.getString(KEY_TITLE);
        String overview = movieJsonObject.getString(KEY_OVERVIEW);
        String release_date = movieJsonObject.getString(KEY_RELEASE_DATE);
        String poster_path = movieJsonObject.getString(KEY_POSTER);
        double vote_average = movieJsonObject.getDouble(KEY_VOTE_AVERAGE);
        long vote_count = movieJsonObject.getLong(KEY_VOTE_COUNT);

        Movie movie = new Movie(id, title, overview, release_date, poster_path, vote_average, vote_count);
        return movie;
    }

    public Uri getPosterURI(String size) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        Uri poster_uri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath(size)
                .appendEncodedPath(poster_path)
                .build();
        return poster_uri;
    }
}
