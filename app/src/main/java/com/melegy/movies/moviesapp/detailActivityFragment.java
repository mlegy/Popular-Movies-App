package com.melegy.movies.moviesapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class detailActivityFragment extends Fragment {

    public detailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        if(intent != null) {
            Long id = intent.getLongExtra("ID", 0);
            Bundle data = getActivity().getIntent().getExtras();
            Movie movie = data.getParcelable("movie");
            ImageView poster_imageView = (ImageView) view.findViewById(R.id.moviePoster);
            Picasso.with(getActivity()).load(movie.getPosterURI("original")).into(poster_imageView);
            TextView movie_title = (TextView) view.findViewById(R.id.movieTitle);
            movie_title.setText(movie.getTitle());
        }
        return view;
    }
}
