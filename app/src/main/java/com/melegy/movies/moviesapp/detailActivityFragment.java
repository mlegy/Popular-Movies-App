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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
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


public class detailActivityFragment extends Fragment implements AdapterView.OnItemClickListener {

    private Movie movie;
    private ListView trailersListView;
    private ArrayAdapter<String> mTrailersNamesAdapter;

    public detailActivityFragment() {
    }

    private static void setListViewHeightBasedOnChildren(ListView iListView) {
        ListAdapter listAdapter = iListView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(iListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, iListView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, RelativeLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = iListView.getLayoutParams();
        params.height = totalHeight + (iListView.getDividerHeight() * (listAdapter.getCount() - 1));
        Log.d("TAG", "ListView Height --- " + params.height);
        iListView.setLayoutParams(params);
        iListView.requestLayout();
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

        mTrailersNamesAdapter =
                new ArrayAdapter<>(
                        getActivity(),
                        R.layout.list_item_trailers,
                        R.id.trailer_title,
                        new ArrayList<String>()
                );

        trailersListView = (ListView) view.findViewById(R.id.list_item_trailers);
        trailersListView.setAdapter(mTrailersNamesAdapter);
        trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });


        return view;
    }

    @Override
    public void onStart() {
        fetchTrailersTask fetchTrailersTask = new fetchTrailersTask();
        fetchTrailersTask.execute();
        fetchReviewsTask fetchReviewsTask = new fetchReviewsTask();
        fetchReviewsTask.execute();
        super.onStart();
    }

    private void setMovieData(Movie movie, View view) {
        ImageView poster_imageView = (ImageView) view.findViewById(R.id.moviePoster);
        Picasso.with(getActivity()).load(movie.getPosterURI("w500", "poster")).into(poster_imageView);

        TextView movie_title = (TextView) view.findViewById(R.id.movieTitle);
        movie_title.setText(movie.getTitle());

        String release_date = movie.getRelease_date();
        if (release_date.contains("-")) {
            release_date = release_date.split("-")[0];
        }

        TextView release_year = (TextView) view.findViewById(R.id.detail_release);
        release_year.setText(release_date);

        TextView vote = (TextView) view.findViewById(R.id.detail_rating);
        vote.setText(movie.getVote_average() + "/10");

        TextView overview = (TextView) view.findViewById(R.id.detail_overview);
        overview.setText(movie.getOverview());

    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
    }


    public class fetchTrailersTask extends AsyncTask<Void, Void, Collection<Trailer>> {

        private final String LOG_TAG = fetchTrailersTask.class.getSimpleName();

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
                mTrailersNamesAdapter.clear();
                if (trailers.size() > 0) {
                    for (Trailer trailer : trailers) {
                        mTrailersNamesAdapter.add(trailer.getName());
                    }
                }
            }
            setListViewHeightBasedOnChildren(trailersListView);
        }
    }

    public class fetchReviewsTask extends AsyncTask<Void, Void, Collection<Review>> {

        private final String LOG_TAG = fetchReviewsTask.class.getSimpleName();

        @Override
        protected Collection<Review> doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String moviesJSON = null;

            try {

                final String TMDB_URI_SCHEME = "http";
                final String TMDB_URI_AUTHORITY = "api.themoviedb.org";
                final String TMDB_URI_FIRST_PATH = "3";
                final String TMDB_URI_SECOND_PATH = "movie";
                final String TMDB_URI_THIRD_PATH = movie.getId() + "";
                final String TMDB_URI_FOURTH_PATH = "reviews";
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
                return getReviews(moviesJSON);
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }


        private Collection<Review> getReviews(String trailersObject)
                throws JSONException {

            final String MDB_LIST = "results";

            JSONObject reviewsJSON = new JSONObject(trailersObject);
            JSONArray reviewsArray = reviewsJSON.getJSONArray(MDB_LIST);

            Collection<Review> reviews = new ArrayList<>();
            for (int i = 0; i < reviewsArray.length(); i++) {
                JSONObject reviewObj = reviewsArray.getJSONObject(i);
                Review review = new Review(reviewObj);
                reviews.add(review);
            }
            return reviews;

        }

        @Override
        protected void onPostExecute(Collection<Review> reviews) {
            if (reviews != null) {
                if (reviews.size() > 0) {
                    for (Review review : reviews) {
                        Log.i("Review", review.getContent());
                    }
                }
            }
        }
    }

}
