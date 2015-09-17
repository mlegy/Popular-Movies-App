package com.melegy.movies.moviesapp.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.melegy.movies.moviesapp.Adapters.MoviesAdapter;
import com.melegy.movies.moviesapp.Model.Movie;
import com.melegy.movies.moviesapp.R;
import com.melegy.movies.moviesapp.Utility.sensitiveData;
import com.melegy.movies.moviesapp.provider.movie.MovieCursor;
import com.melegy.movies.moviesapp.provider.movie.MovieSelection;

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

public class MainActivityFragment extends Fragment {

    private int page_num = 1;
    private RecyclerView moviesRecyclerView;
    private MoviesAdapter adapter;
    private String sort_type;
    private ProgressBar progressBar;
    private boolean showFavourites;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sort_type = prefs.getString(getString(R.string.pref_sort_type), "popularity.desc");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        moviesRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager mStaggeredLayoutManager = new GridLayoutManager(getActivity(), 2);
        moviesRecyclerView.setLayoutManager(mStaggeredLayoutManager);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem action_sort_by_popularity = menu.findItem(R.id.action_sort_by_popularity);
        MenuItem action_sort_by_rating = menu.findItem(R.id.action_sort_by_rating);

        if (sort_type.contentEquals(getResources().getString(R.string.pref_sort_popularity))) {
            if (!action_sort_by_popularity.isChecked()) {
                action_sort_by_popularity.setChecked(true);
            }
        } else if (sort_type.contentEquals(getResources().getString(R.string.pref_sort_type_rating))) {
            if (!action_sort_by_rating.isChecked()) {
                action_sort_by_rating.setChecked(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort_by_popularity:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                showFavourites = false;
                sort_type = getResources().getString(R.string.pref_sort_popularity);
                updateView();
                return true;
            case R.id.action_sort_by_rating:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                showFavourites = false;
                sort_type = getResources().getString(R.string.pref_sort_type_rating);
                updateView();
                return true;
            case R.id.action_favourites:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                showFavourites = true;
                updateView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        page_num = 1;
        updateView();
    }

    private void updateView() {
        if (showFavourites) {
            fetchFavouritesMovies();
        } else {
            fetchMoviesTask task = new fetchMoviesTask();
            task.execute(String.valueOf(page_num));
        }
    }

    private void setOnClickListenerOnItems(final Collection<Movie> movies) {
        MoviesAdapter.OnItemClickListener onItemClickListener = new MoviesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), detailActivity.class);
                Movie mMovie = (Movie) movies.toArray()[position];
                intent.putExtra("movie", mMovie);
                startActivity(intent);
            }
        };
        adapter.setOnItemClickListener(onItemClickListener);
    }

    private void fetchFavouritesMovies() {
        MovieSelection where = new MovieSelection();
        MovieCursor movieCursor = where.query(getActivity());
        Movie movie;
        List<Movie> favouriteMovies = new ArrayList<>();
        while (movieCursor.moveToNext()) {
            movie = new Movie(movieCursor.getId(), movieCursor.getTitle(), movieCursor.getOverview(),
                    movieCursor.getReleaseDate(), movieCursor.getPoster(), movieCursor.getBackdrop(),
                    movieCursor.getVoteAverage(), movieCursor.getVoteCount());
            favouriteMovies.add(movie);
        }
        movieCursor.close();
        adapter = new MoviesAdapter(getActivity(), favouriteMovies);
        moviesRecyclerView.setAdapter(adapter);
        setOnClickListenerOnItems(favouriteMovies);
    }

    public class fetchMoviesTask extends AsyncTask<String, Void, Collection<Movie>> {

        private final String LOG_TAG = fetchMoviesTask.class.getSimpleName();

        @Override
        protected Collection<Movie> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            String page_num = params[0];
            Log.i("PAGE_NUM", page_num);

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String moviesJSON = null;

            try {

                final String TMDB_URI_SCHEME = "http";
                final String TMDB_URI_AUTHORITY = "api.themoviedb.org";
                final String TMDB_URI_FIRST_PATH = "3";
                final String TMDB_URI_SECOND_PATH = "discover";
                final String TMDB_URI_THIRD_PATH = "movie";
                final String API_PARAM = "api_key";
                final String SORT_PARAM = "sort_by";
                final String PAGE_PARAM = "page";

                Uri.Builder builder = new Uri.Builder();
                builder.scheme(TMDB_URI_SCHEME)
                        .authority(TMDB_URI_AUTHORITY)
                        .appendPath(TMDB_URI_FIRST_PATH)
                        .appendPath(TMDB_URI_SECOND_PATH)
                        .appendPath(TMDB_URI_THIRD_PATH)
                        .appendQueryParameter(API_PARAM, sensitiveData.API_KEY)
                        .appendQueryParameter(SORT_PARAM, sort_type)
                        .appendQueryParameter(PAGE_PARAM, page_num);

                String myUrl = builder.build().toString();
                URL url = new URL(myUrl);
                Log.i("TYPE", sort_type);
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
                return getMoviesData(moviesJSON);
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }


        private Collection<Movie> getMoviesData(String moviesJSON)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MDB_LIST = "results";

            JSONObject forecastJson = new JSONObject(moviesJSON);
            JSONArray moviesArray = forecastJson.getJSONArray(MDB_LIST);

            Collection<Movie> movies = new ArrayList<>();
            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject movieObj = moviesArray.getJSONObject(i);
                Movie movie = Movie.deserialize(movieObj);
                movies.add(movie);
            }

            return movies;

        }


        @Override
        protected void onPostExecute(final Collection<Movie> movies) {
            if (movies != null) {
                adapter = new MoviesAdapter(getActivity(), movies);
                moviesRecyclerView.setAdapter(adapter);
                setOnClickListenerOnItems(movies);
            }
            progressBar.setVisibility(View.GONE);
        }
    }

}
