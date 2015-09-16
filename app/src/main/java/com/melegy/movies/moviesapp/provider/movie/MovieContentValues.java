package com.melegy.movies.moviesapp.provider.movie;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.melegy.movies.moviesapp.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code movie} table.
 */
public class MovieContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return MovieColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable MovieSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param context The context to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable MovieSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public MovieContentValues putID(@NonNull long value) {
//        if (value == null) throw new IllegalArgumentException("title must not be null");
        mContentValues.put(MovieColumns._ID, value);
        return this;
    }

    public MovieContentValues putTitle(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("title must not be null");
        mContentValues.put(MovieColumns.TITLE, value);
        return this;
    }


    public MovieContentValues putOverview(@Nullable String value) {
        mContentValues.put(MovieColumns.OVERVIEW, value);
        return this;
    }

    public MovieContentValues putOverviewNull() {
        mContentValues.putNull(MovieColumns.OVERVIEW);
        return this;
    }

    public MovieContentValues putVoteAverage(@Nullable Float value) {
        mContentValues.put(MovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MovieContentValues putVoteAverageNull() {
        mContentValues.putNull(MovieColumns.VOTE_AVERAGE);
        return this;
    }

    public MovieContentValues putVoteCount(@Nullable Integer value) {
        mContentValues.put(MovieColumns.VOTE_COUNT, value);
        return this;
    }

    public MovieContentValues putVoteCountNull() {
        mContentValues.putNull(MovieColumns.VOTE_COUNT);
        return this;
    }

    public MovieContentValues putPopularity(@Nullable Float value) {
        mContentValues.put(MovieColumns.POPULARITY, value);
        return this;
    }

    public MovieContentValues putPopularityNull() {
        mContentValues.putNull(MovieColumns.POPULARITY);
        return this;
    }

    public MovieContentValues putReleaseDate(@Nullable String value) {
        mContentValues.put(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public MovieContentValues putReleaseDateNull() {
        mContentValues.putNull(MovieColumns.RELEASE_DATE);
        return this;
    }

    public MovieContentValues putPoster(@Nullable String value) {
        mContentValues.put(MovieColumns.POSTER, value);
        return this;
    }

    public MovieContentValues putPosterNull() {
        mContentValues.putNull(MovieColumns.POSTER);
        return this;
    }

    public MovieContentValues putBackdrop(@Nullable String value) {
        mContentValues.put(MovieColumns.BACKDROP, value);
        return this;
    }

    public MovieContentValues putBackdropNull() {
        mContentValues.putNull(MovieColumns.BACKDROP);
        return this;
    }

    public MovieContentValues putIsFavourite(boolean value) {
        mContentValues.put(MovieColumns.IS_FAVOURITE, value);
        return this;
    }

}
