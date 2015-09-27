package com.melegy.movies.moviesapp.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.melegy.movies.moviesapp.Adapters.ReviewsAdapter;
import com.melegy.movies.moviesapp.Adapters.TrailersAdapter;
import com.melegy.movies.moviesapp.Model.Movie;
import com.melegy.movies.moviesapp.Model.Review;
import com.melegy.movies.moviesapp.Model.Trailer;
import com.melegy.movies.moviesapp.R;
import com.melegy.movies.moviesapp.Utility.Utility;
import com.melegy.movies.moviesapp.Utility.sensitiveData;
import com.melegy.movies.moviesapp.provider.review.ReviewCursor;
import com.melegy.movies.moviesapp.provider.review.ReviewSelection;
import com.melegy.movies.moviesapp.provider.trailer.TrailerCursor;
import com.melegy.movies.moviesapp.provider.trailer.TrailerSelection;
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
import java.util.List;

public class detailActivityFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String LOG_TAG = detailActivityFragment.class.getSimpleName();
    private Movie movie;
    private ArrayList<Trailer> trailers;
    private ArrayList<Review> mReviews;
    private boolean hasArguments;
    private boolean isFavoured;
    private ShareActionProvider mShareActionProvider;
    private MenuItem menuItem;
    private String trailer_title;
    private String trailer_key;
    private boolean trailerFound;

    public detailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        Utility utility = new Utility(getActivity());
        Bundle arguments = getArguments();
        if (arguments != null) {
            hasArguments = true;
            if (arguments.getBoolean("twoPane")) {
                movie = arguments.getParcelable("movie");
            } else {
                movie = getActivity().getIntent().getExtras().getParcelable("movie");
            }
            assert movie != null;
            isFavoured = Utility.isFavoured(movie.getId());
            setMovieData(movie, view);

            final ToggleButton add_bookmark = (ToggleButton) view.findViewById(R.id.favouriteButton);
            add_bookmark.setChecked(isFavoured);
            add_bookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                    if (isChecked) {
                        Utility.addToFavourites(movie, mReviews, trailers);
                    } else {
                        if (isFavoured) {
                            Utility.removeFromFavourites(movie.getId());
                        }
                    }
                }
            });
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (isFavoured && !Utility.isNetworkAvailable()) {
            fetchOfflineTrailers();
            fetchOfflineReviews();
        } else if (hasArguments) {
            fetchTrailersTask fetchTrailersTask = new fetchTrailersTask();
            fetchTrailersTask.execute();
            fetchReviewsTask fetchReviewsTask = new fetchReviewsTask();
            fetchReviewsTask.execute();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.menu_detail, menu);
            menuItem = menu.findItem(R.id.share_action);
            menuItem.setVisible(trailerFound);
    }

    private Intent createShareIntent(String movie_name, String trailer_name, String key) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                "Watch " + movie_name + ": " + trailer_name
                        + " http://www.youtube.com/watch?v="
                        + key + " #Udacity_Movies_App");
        return shareIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getActivity().invalidateOptionsMenu();
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                getActivity().supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepareShare() {
        mShareActionProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(menuItem);
        if (trailer_title != null && trailer_key != null) {
            trailerFound = true;
            mShareActionProvider.setShareIntent(createShareIntent(movie.getTitle(),
                    trailer_title, trailer_key));
        } else {
            trailerFound = false;
        }
    }

    private void setMovieData(Movie movie, View view) {
        ImageView poster_imageView = (ImageView) view.findViewById(R.id.moviePoster);
        Picasso
                .with(getActivity())
                .load(movie.getPosterURI("w500", "poster"))
                .into(poster_imageView);

        TextView movie_title = (TextView) view.findViewById(R.id.movieTitle);
        movie_title.setText(movie.getTitle());

        String release_date = movie.getRelease_date();
        if (release_date.contains("-")) {
            release_date = release_date.split("-")[0];
        }

        TextView release_year = (TextView) view.findViewById(R.id.detail_release);
        release_year.setText(release_date);

        TextView vote = (TextView) view.findViewById(R.id.detail_rating);
        vote.setText(String.format("%.1f", movie.getVote_average()) + "/10");

        TextView overview = (TextView) view.findViewById(R.id.detail_overview);
        overview.setText(movie.getOverview());

    }

    private void fetchOfflineReviews() {
        ReviewSelection where = new ReviewSelection();
        where.movieId(movie.getId());
        ReviewCursor reviewCursor = where.query(getActivity());
        Review review;
        List<Review> favouriteReviews = new ArrayList<>();
        while (reviewCursor.moveToNext()) {
            review = new Review(reviewCursor.getReviewAuthor(), reviewCursor.getReviewContent());
            favouriteReviews.add(review);
        }
        reviewCursor.close();
        addReviewsToUI(favouriteReviews);
    }

    private void fetchOfflineTrailers() {
        TrailerSelection where = new TrailerSelection();
        where.movieId(movie.getId());
        TrailerCursor trailerCursor = where.query(getActivity());
        Trailer trailer;
        List<Trailer> favouriteTrailers = new ArrayList<>();
        while (trailerCursor.moveToNext()) {
            trailer = new Trailer(trailerCursor.getTrailerName(), trailerCursor.getTrailerKey());
            favouriteTrailers.add(trailer);
        }
        trailerCursor.close();
        addTrailersToUI(favouriteTrailers);
    }

    private void addReviewsToUI(List<Review> favouriteReviews) {
        if (favouriteReviews.size() > 0) {
            TextView reviews_title = (TextView) getActivity().findViewById(R.id.reviews_title);
            reviews_title.setVisibility(View.VISIBLE);
            mReviews = new ArrayList<>();
            mReviews.addAll(favouriteReviews);
            ReviewsAdapter mReviewsAdapter = new ReviewsAdapter(getActivity(), mReviews);
            LinearLayout reviewsListView = (LinearLayout) getActivity()
                    .findViewById(R.id.list_item_reviews);
            for (int i = 0; i < mReviewsAdapter.getCount(); i++) {
                View view = mReviewsAdapter.getView(i, null, null);
                reviewsListView.addView(view);
            }
        }
    }

    private void addTrailersToUI(List<Trailer> trailers) {
        if (trailers.size() > 0) {
            TextView trailers_title = (TextView) getActivity().findViewById(R.id.trailers_title);
            trailers_title.setVisibility(View.VISIBLE);
            final ArrayList<Trailer> mTrailers = new ArrayList<>();
            mTrailers.addAll(trailers);
            TrailersAdapter mTrailersAdapter = new TrailersAdapter(getActivity(), mTrailers);
            LinearLayout trailersListView = (LinearLayout) getActivity().findViewById(R.id.list_item_trailers);

            for (int i = 0; i < mTrailersAdapter.getCount(); i++) {
                View view = mTrailersAdapter.getView(i, null, null);
                final int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String youtubeLink = "https://www.youtube.com/watch?v=" + mTrailers.get(finalI).getKey();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink));
                        Utility.preferPackageForIntent(getActivity(), intent,
                                Utility.YOUTUBE_PACKAGE_NAME);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        startActivity(intent);
                    }
                });
                trailersListView.addView(view);
                trailer_title = trailers.get(0).getName();
                trailer_key = trailers.get(0).getKey();
//                if (mShareActionProvider != null) {
//                    mShareActionProvider.setShareIntent(createShareIntent(
//                            trailers.get(0).getName(), trailers.get(0).getKey()));
//                }
            }
        }
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
                return getYoutubeVideos(moviesJSON);
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }


        private Collection<Trailer> getYoutubeVideos(String trailersObject)
                throws JSONException {

            final String MDB_LIST = "results";

            JSONObject trailersJSON = new JSONObject(trailersObject);
            JSONArray trailersArray = trailersJSON.getJSONArray(MDB_LIST);

            trailers = new ArrayList<>();
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
        protected void onPostExecute(final Collection<Trailer> trailers) {
            if (trailers != null) {
                List<Trailer> trailerList = new ArrayList<>(trailers);
                addTrailersToUI(trailerList);
                prepareShare();
                getActivity().invalidateOptionsMenu();
                if (isFavoured) {
                    TrailerSelection where = new TrailerSelection();
                    where.movieId(movie.getId());
                    TrailerCursor trailerCursor = where.query(getActivity());
                    if (!trailerCursor.moveToFirst()) {
                        for (Trailer trailer : trailerList) {
                            Utility.addTrailerToContentProvider(movie.getId(), trailer);
                        }
                    }
                }
            }
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
                List<Review> reviewList = new ArrayList<>(reviews);
                addReviewsToUI(reviewList);
                if (isFavoured) {
                    ReviewSelection where = new ReviewSelection();
                    where.movieId(movie.getId());
                    ReviewCursor reviewCursor = where.query(getActivity());
                    if (!reviewCursor.moveToFirst()) {
                        for (Review review : reviewList) {
                            Utility.addReviewToContentProvider(movie.getId(), review);
                        }
                    }
                }
            }
        }
    }

}
