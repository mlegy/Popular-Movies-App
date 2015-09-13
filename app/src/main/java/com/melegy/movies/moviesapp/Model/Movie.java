package com.melegy.movies.moviesapp.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by ahmad on 14/08/15.
 */
public class Movie implements Parcelable {

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER = "poster_path";
    private static final String KEY_backdrop = "backdrop_path";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_RELEASE_DATE = "release_date";
    private long id;
    private String title;
    private String overview;
    private String release_date;
    private String poster_path;
    private String backdrop_path;
    private double vote_average;
    private long vote_count;

    public Movie(long id, String title, String overview, String release_date, String poster_path, String backdrop_path, double vote_average, long vote_count) {
        this.setId(id);
        this.setTitle(title);
        this.setOverview(overview);
        this.setRelease_date(release_date);
        this.setPoster_path(poster_path);
        this.setBackdrop_path(backdrop_path);
        this.setVote_average(vote_average);
        this.setVote_count(vote_count);
    }

    private Movie(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.poster_path = in.readString();
        this.backdrop_path = in.readString();
        this.vote_average = in.readDouble();
        this.vote_count = in.readLong();
    }

    public static Movie deserialize(JSONObject movieJsonObject) throws JSONException {

        Long id = movieJsonObject.getLong(getKeyId());
        String title = movieJsonObject.getString(getKeyTitle());
        String overview = movieJsonObject.getString(getKeyOverview());
        String release_date = movieJsonObject.getString(getKeyReleaseDate());
        String poster_path = movieJsonObject.getString(getKeyPoster());
        String backdrop_path = movieJsonObject.getString(getKEY_backdrop());
        double vote_average = movieJsonObject.getDouble(getKeyVoteAverage());
        long vote_count = movieJsonObject.getLong(getKeyVoteCount());

        Movie movie = new Movie(id, title, overview, release_date, poster_path, backdrop_path, vote_average, vote_count);
        return movie;
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    public static String getKeyTitle() {
        return KEY_TITLE;
    }

    public static String getKeyOverview() {
        return KEY_OVERVIEW;
    }

    public static String getKeyReleaseDate() {
        return KEY_RELEASE_DATE;
    }

    public static String getKeyPoster() {
        return KEY_POSTER;
    }

    public static String getKEY_backdrop() {
        return KEY_backdrop;
    }

    public static String getKeyVoteAverage() {
        return KEY_VOTE_AVERAGE;
    }

    public static String getKeyVoteCount() {
        return KEY_VOTE_COUNT;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public long getVote_count() {
        return vote_count;
    }

    public void setVote_count(long vote_count) {
        this.vote_count = vote_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.poster_path);
        dest.writeString(this.backdrop_path);
        dest.writeDouble(this.vote_average);
        dest.writeLong(this.vote_count);
    }

    public Uri getPosterURI(String size, String type) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        String image_path = null;
        if (type.equals("poster")) {
            image_path = getPoster_path();
        } else if (type.equals("backdrop")) {
            image_path = getBackdrop_path();
        }
        Uri poster_uri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath(size)
                .appendEncodedPath(image_path)
                .build();
        return poster_uri;
    }

}
