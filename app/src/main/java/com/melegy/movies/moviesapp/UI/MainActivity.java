package com.melegy.movies.moviesapp.UI;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.melegy.movies.moviesapp.Model.Movie;
import com.melegy.movies.moviesapp.R;

public class MainActivity extends ActionBarActivity implements MainActivityFragment.Callback {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
            Log.i("mTwoPane", "true");
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.movie_detail_container, new detailActivityFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }

    }

    @Override
    public void onItemSelected(Movie movie) {
        if (mTwoPane) {

            Bundle args = new Bundle();
            args.putParcelable("movie", movie);
            detailActivityFragment fragment = new detailActivityFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
            //one-pane mode
//        } else {
//            Intent intent = new Intent(this, detailActivity.class)
//                    .setData(movie);
//            startActivity(intent);
//        }
        }
    }
}
