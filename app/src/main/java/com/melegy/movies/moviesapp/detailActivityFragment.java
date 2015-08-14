package com.melegy.movies.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        if (intent != null) {
            Bundle data = intent.getExtras();
            Movie movie = data.getParcelable("movie");
            setMovieData(movie, view);
        }

        return view;
    }

    private void setMovieData(Movie movie, View view) {
        ImageView poster_imageView = (ImageView) view.findViewById(R.id.moviePoster);
        Picasso.with(getActivity()).load(movie.getPosterURI("w780", "backdrop")).into(poster_imageView);

        TextView movie_title = (TextView) view.findViewById(R.id.movieTitle);
        movie_title.setText(movie.getTitle());

        TextView overview_text = (TextView) view.findViewById(R.id.detail_overview);
        overview_text.setText(movie.getOverview());

    }
}
