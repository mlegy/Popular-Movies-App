package com.melegy.movies.moviesapp.Utility;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.melegy.movies.moviesapp.Model.Movie;
import com.melegy.movies.moviesapp.Model.Review;
import com.melegy.movies.moviesapp.Model.Trailer;
import com.melegy.movies.moviesapp.provider.movie.MovieColumns;
import com.melegy.movies.moviesapp.provider.movie.MovieContentValues;
import com.melegy.movies.moviesapp.provider.movie.MovieCursor;
import com.melegy.movies.moviesapp.provider.movie.MovieSelection;
import com.melegy.movies.moviesapp.provider.review.ReviewColumns;
import com.melegy.movies.moviesapp.provider.review.ReviewContentValues;
import com.melegy.movies.moviesapp.provider.trailer.TrailerColumns;
import com.melegy.movies.moviesapp.provider.trailer.TrailerContentValues;

import java.util.ArrayList;

/**
 * Created by ahmad on 14/09/15.
 */
public class Utility {
    public static final String YOUTUBE_PACKAGE_NAME = "com.google.android.youtube";
    public static Context context;

    public Utility(Context context) {
        Utility.context = context;
    }

    public static void preferPackageForIntent(Context context, Intent intent, String packageName) {
        PackageManager pm = context.getPackageManager();
        for (ResolveInfo resolveInfo : pm.queryIntentActivities(intent, 0)) {
            if (resolveInfo.activityInfo.packageName.equals(packageName)) {
                intent.setPackage(packageName);
                break;
            }
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView iListView) {
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
        iListView.setLayoutParams(params);
        iListView.requestLayout();
    }

    public static boolean isFavoured(Long movieID) {
        MovieSelection where = new MovieSelection();
        where.id(movieID);
        MovieCursor cursor = where.query(context);
        if (cursor.moveToFirst()) {
            return cursor.getIsFavourite();
        }
        return false;

    }

    public static void removeFromFavourites(Long movieID) {
        MovieSelection where = new MovieSelection();
        where.id(movieID);
        context.getContentResolver()
                .delete(MovieColumns.CONTENT_URI, where.sel(), where.args());
    }

    public static void addToFavourites(Movie movie, ArrayList<Review> reviews, ArrayList<Trailer> trailers) {
        addMovieToContentProvider(movie);
        if (reviews != null) {
            for (Review review : reviews) {
                addReviewToContentProvider(movie.getId(), review);
            }
        }
        if (trailers != null) {
            for (Trailer trailer : trailers) {
                addTrailerToContentProvider(movie.getId(), trailer);
            }
        }
    }

    public static void addReviewToContentProvider(Long movieID, Review review) {
        if (review != null) {
            ReviewContentValues reviewContentValues = new ReviewContentValues();
            reviewContentValues.putMovieId(movieID);
            reviewContentValues.putReviewAuthor(review.getAuthor());
            reviewContentValues.putReviewContent(review.getContent());
            context.getContentResolver().insert(ReviewColumns.CONTENT_URI, reviewContentValues.values());
        }
    }

    public static void addTrailerToContentProvider(Long movieID, Trailer trailer) {
        if (trailer != null) {
            TrailerContentValues trailerContentValues = new TrailerContentValues();
            trailerContentValues.putMovieId(movieID);
            trailerContentValues.putTrailerName(trailer.getName());
            trailerContentValues.putTrailerSite(trailer.getSite());
            trailerContentValues.putTrailerKey(trailer.getKey());
            context.getContentResolver().insert(TrailerColumns.CONTENT_URI, trailerContentValues.values());
        }
    }

    public static void addMovieToContentProvider(Movie movie) {
        MovieContentValues movieContentValues = new MovieContentValues();
        movieContentValues.putID(movie.getId());
        movieContentValues.putTitle(movie.getTitle());
        movieContentValues.putReleaseDate(movie.getRelease_date());
        movieContentValues.putVoteAverage((float) movie.getVote_average());
        movieContentValues.putOverview(movie.getOverview());
        movieContentValues.putPoster(movie.getPoster_path());
        movieContentValues.putVoteCount((int) movie.getVote_count());
        movieContentValues.putIsFavourite(true);
        movieContentValues.putBackdrop(movie.getBackdrop_path());
        context.getContentResolver().insert(MovieColumns.CONTENT_URI, movieContentValues.values());
    }

}
