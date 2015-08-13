package com.melegy.movies.moviesapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    //private final List<String> urls = new ArrayList<>();
    String[] posters;
    private ImageAdapter imageAdapter;


    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        imageAdapter = new ImageAdapter(getActivity());
        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        fetchMoviesTask task = new fetchMoviesTask();
        task.execute("popularity.desc");

    }

    public class fetchMoviesTask extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = fetchMoviesTask.class.getSimpleName();

        @Override
        protected String[] doInBackground(String... params) {
            // If there's no zip code, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {
                return null;
            }
            Log.i(LOG_TAG, "DO");
            String sort_type = params[0];
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String moviesJSON = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                final String TMDB_URI_SCHEME = "http";
                final String TMDB_URI_AUTHORITY = "api.themoviedb.org";
                final String TMDB_URI_FIRST_PATH = "3";
                final String TMDB_URI_SECOND_PATH = "discover";
                final String TMDB_URI_THIRD_PATH = "movie";
                final String API_PARAM = "api_key";
                final String SORT_PARAM = "sort_by";


                Uri.Builder builder = new Uri.Builder();
                builder.scheme(TMDB_URI_SCHEME)
                        .authority(TMDB_URI_AUTHORITY)
                        .appendPath(TMDB_URI_FIRST_PATH)
                        .appendPath(TMDB_URI_SECOND_PATH)
                        .appendPath(TMDB_URI_THIRD_PATH)
                        .appendQueryParameter(API_PARAM, sensitiveData.API_KEY)
                        .appendQueryParameter(SORT_PARAM, sort_type);

                String myUrl = builder.build().toString();
                URL url = new URL(myUrl);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                moviesJSON = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
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
                String[] moviesData = getMoviesData(moviesJSON);
                posters = getPosters(moviesData);
                return posters;
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }


        private String[] getMoviesData(String moviesJSON)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MDB_LIST = "results";
            final String MDB_POSTER = "poster_path";

            JSONObject forecastJson = new JSONObject(moviesJSON);
            JSONArray moviesArray = forecastJson.getJSONArray(MDB_LIST);

            String[] resultStrs = new String[moviesArray.length()];
            for (int i = 0; i < moviesArray.length(); i++) {
                String poster;
                JSONObject movieData = moviesArray.getJSONObject(i);
                poster = movieData.getString(MDB_POSTER);
                resultStrs[i] = poster;
            }

            return resultStrs;

        }

        private String[] getPosters(String[] postersIDs) {
            String BASE_URL = "http://image.tmdb.org/t/p/";
            String SIZE = "w185";
            String[] postersURLS = new String[postersIDs.length];
            for (int i = 0; i < postersIDs.length; i++) {
                postersURLS[i] = (BASE_URL + SIZE + postersIDs[i]);
            }
            return postersURLS;
        }

        @Override
        protected void onPostExecute(String[] postersURLS) {
            if (postersURLS != null) {
                posters = new String[0];
                posters = postersURLS.clone();
                imageAdapter.addPosters(posters);
            }
        }
    }

}
