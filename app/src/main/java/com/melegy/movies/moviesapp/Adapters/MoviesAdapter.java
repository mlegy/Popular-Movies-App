package com.melegy.movies.moviesapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.picassopalette.PicassoPalette;
import com.melegy.movies.moviesapp.Model.Movie;
import com.melegy.movies.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.Collection;


/**
 * Created by ahmad on 12/08/15.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    OnItemClickListener mItemClickListener;
    private Collection<Movie> movies;
    private Context context;

    public MoviesAdapter(Context context, Collection<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_row, null);
        MoviesViewHolder moviesViewHolder = new MoviesViewHolder(view);
        return moviesViewHolder;
    }

    @Override
    public void onBindViewHolder(final MoviesViewHolder moviesViewHolder, int i) {
        Movie movie = (Movie) movies.toArray()[i];

        //Download image using picasso library
        Picasso.with(context)
                .load(movie.getPosterURI("w185", "poster"))
                .into(moviesViewHolder.imageView);

        //Setting text view title
        moviesViewHolder.titleView.setText(movie.getTitle());

        Picasso.with(context).
                load(movie.getPosterURI("w185", "poster"))
                .fit()
                .into(moviesViewHolder.imageView,
                        PicassoPalette
                                .with(movie.getPosterURI("w185", "poster").toString(), moviesViewHolder.imageView)
                                .use(PicassoPalette.Profile.VIBRANT)
                                .intoBackground(moviesViewHolder.movieTitleHolder)
                                .intoTextColor(moviesViewHolder.titleView, PicassoPalette.Swatch.BODY_TEXT_COLOR));
    }

    @Override
    public int getItemCount() {
        return (null != movies ? movies.size() : 0);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected LinearLayout movieHolder;
        protected LinearLayout movieTitleHolder;
        protected ImageView imageView;
        protected TextView titleView;

        public MoviesViewHolder(View view) {
            super(view);
            this.movieHolder = (LinearLayout) view.findViewById(R.id.mainHolder);
            this.movieTitleHolder = (LinearLayout) view.findViewById(R.id.placeNameHolder);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.titleView = (TextView) view.findViewById(R.id.title);
            this.movieHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }

//        public int getImageResourceId(Context context) {
//            return context.getResources().getIdentifier(this.imageView, "drawable", context.getPackageName());
//        }
    }
}
