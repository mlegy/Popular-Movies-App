package com.melegy.movies.moviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A placeholder fragment containing a simple view.
 */
public class detailActivityFragment extends Fragment {

    private Movie movie;

    public detailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();

        if (intent != null) {
            Bundle data = intent.getExtras();
            movie = data.getParcelable("movie");
            setMovieData(movie, view);
        }

        return view;
    }

    @Override
    public void onStart() {
        fetchReviewsTask task = new fetchReviewsTask();
        task.execute();
        super.onStart();
    }

    private void setMovieData(Movie movie, View view) {
        ImageView poster_imageView = (ImageView) view.findViewById(R.id.moviePoster);
        Picasso.with(getActivity()).load(movie.getPosterURI("w780", "backdrop")).into(poster_imageView);

        TextView movie_title = (TextView) view.findViewById(R.id.movieTitle);
        movie_title.setText(movie.getTitle());

        TextView overview_text = (TextView) view.findViewById(R.id.detail_overview);
        overview_text.setText(movie.getOverview());

    }

    public class fetchReviewsTask extends AsyncTask<Void, Void, Collection<Trailer>> {

        private final String LOG_TAG = fetchReviewsTask.class.getSimpleName();

        @Override
        protected Collection<Trailer> doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String moviesJSON = null;

            try {

                final String TMDB_URI_SCHEME = "http";
                final String TMDB_URI_AUTHORITY = "api.themoviedb.org";
                final String TMDB_URI_FIRST_PATH = "3";
                final String TMDB_URI_SECOND_PATH = "movie";
                final String TMDB_URI_THIRD_PATH = movie.getId() + "";
                final String TMDB_URI_FOURTH_PATH = "videos";
                final String API_PARAM = "api_key";


                Uri.Builder builder = new Uri.Builder();
                builder.scheme(TMDB_URI_SCHEME)
                        .authority(TMDB_URI_AUTHORITY)
                        .appendPath(TMDB_URI_FIRST_PATH)
                        .appendPath(TMDB_URI_SECOND_PATH)
                        .appendPath(TMDB_URI_THIRD_PATH)
                        .appendPath(TMDB_URI_FOURTH_PATH)
                        .appendQueryParameter(API_PARAM, sensitiveData.API_KEY);

                String myUrl = builder.build().toString();
                URL url = new URL(myUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                moviesJSON = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getYoutbeVideos(moviesJSON);
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }


        private Collection<Trailer> getYoutbeVideos(String trailersObject)
                throws JSONException {

            final String MDB_LIST = "results";

            JSONObject trailersJSON = new JSONObject(trailersObject);
            JSONArray trailersArray = trailersJSON.getJSONArray(MDB_LIST);

            Collection<Trailer> trailers = new ArrayList<>();
            for (int i = 0; i < trailersArray.length(); i++) {
                JSONObject trailerObj = trailersArray.getJSONObject(i);
                if (trailerObj.getString("site").contentEquals("YouTube")) {
                    Trailer trailer = new Trailer(trailerObj);
                    trailers.add(trailer);
                }
            }
            return trailers;

        }

        @Override
        protected void onPostExecute(Collection<Trailer> trailers) {
            if (trailers != null) {
                if (trailers.size() > 0) {
                    for (Trailer trailer : trailers) {
                        Log.i("Trailer", trailer.getKey());
                    }
                }
            }
        }
    }
}
