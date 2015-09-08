package com.melegy.movies.moviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

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

    String[] posters;
    Collection<Movie> movies;
    int myLastVisiblePos;
    private ImageAdapter imageAdapter;
    private List<Movie> movies_list = new ArrayList<>();
    private int page_num = 1;
    private GridView gridview;
    private String sort_type;


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

        gridview = (GridView) view.findViewById(R.id.gridview);
        imageAdapter = new ImageAdapter(getActivity());
        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(getActivity(), detailActivity.class);
                Movie mMovie = movies_list.get(position);
                intent.putExtra("movie", mMovie);
                startActivity(intent);

            }
        });

        myLastVisiblePos = gridview.getFirstVisiblePosition();

        gridview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int currentFirstVisPos = view.getFirstVisiblePosition();
                if (currentFirstVisPos > myLastVisiblePos) {
                    Log.i("SCROLL", "DOWN");
                }
                if (currentFirstVisPos < myLastVisiblePos) {
                    page_num += 1;
                    updateView();
                    Log.i("SCROLL", "UP");
                }
                myLastVisiblePos = currentFirstVisPos;
            }
        });

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
                sort_type = getResources().getString(R.string.pref_sort_popularity);
                reCreateView();
                return true;
            case R.id.action_sort_by_rating:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                sort_type = getResources().getString(R.string.pref_sort_type_rating);
                reCreateView();
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

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Boolean updated = settings.getBoolean("updated", false);
        if (updated) {
            reCreateView();
        }
    }

    private void reCreateView() {
        page_num = 1;
        gridview.setAdapter(null);
        if (movies != null) {
            movies.clear();
        }
        if (movies_list != null) {
            movies_list.clear();
        }
        posters = new String[0];
        imageAdapter = new ImageAdapter(getActivity());
        updateView();
        gridview.setAdapter(imageAdapter);
    }

    private void updateView() {

        fetchMoviesTask task = new fetchMoviesTask();
        task.execute(String.valueOf(page_num));
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
            movies_list.addAll(movies);

            return movies;

        }

        private String[] getPosters(Collection<Movie> movies) {
            String poster_size = "w185";
            String[] postersURLS = new String[movies.size()];
            int i = 0;
            for (Movie movie : movies) {
                Uri poster_url = movie.getPosterURI(poster_size, "poster");
                postersURLS[i] = poster_url.toString();
                i++;
            }
            return postersURLS;
        }

        @Override
        protected void onPostExecute(Collection<Movie> movies) {
            if (movies != null) {
                posters = new String[0];
                posters = getPosters(movies);
                imageAdapter.addPosters(posters);
            }
        }
    }

}
