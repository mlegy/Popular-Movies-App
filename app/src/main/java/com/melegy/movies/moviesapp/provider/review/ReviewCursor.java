package com.melegy.movies.moviesapp.provider.review;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.melegy.movies.moviesapp.provider.base.AbstractCursor;
import com.melegy.movies.moviesapp.provider.movie.MovieColumns;

/**
 * Cursor wrapper for the {@code review} table.
 */
public class ReviewCursor extends AbstractCursor implements ReviewModel {
    public ReviewCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(ReviewColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code movie_id} value.
     */
    public long getMovieId() {
        Long res = getLongOrNull(ReviewColumns.MOVIE_ID);
        if (res == null)
            throw new NullPointerException("The value of 'movie_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code title} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getMovieTitle() {
        String res = getStringOrNull(MovieColumns.TITLE);
        if (res == null)
            throw new NullPointerException("The value of 'title' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code overview} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMovieOverview() {
        String res = getStringOrNull(MovieColumns.OVERVIEW);
        return res;
    }

    /**
     * Get the {@code vote_average} value.
     * Can be {@code null}.
     */
    @Nullable
    public Float getMovieVoteAverage() {
        Float res = getFloatOrNull(MovieColumns.VOTE_AVERAGE);
        return res;
    }

    /**
     * Get the {@code vote_count} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getMovieVoteCount() {
        Integer res = getIntegerOrNull(MovieColumns.VOTE_COUNT);
        return res;
    }

    /**
     * Get the {@code popularity} value.
     * Can be {@code null}.
     */
    @Nullable
    public Float getMoviePopularity() {
        Float res = getFloatOrNull(MovieColumns.POPULARITY);
        return res;
    }

    /**
     * Get the {@code release_date} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMovieReleaseDate() {
        String res = getStringOrNull(MovieColumns.RELEASE_DATE);
        return res;
    }

    /**
     * Get the {@code poster} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMoviePoster() {
        String res = getStringOrNull(MovieColumns.POSTER);
        return res;
    }

    /**
     * Get the {@code backdrop} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMovieBackdrop() {
        String res = getStringOrNull(MovieColumns.BACKDROP);
        return res;
    }

    /**
     * Get the {@code is_favourite} value.
     */
    public boolean getMovieIsFavourite() {
        Boolean res = getBooleanOrNull(MovieColumns.IS_FAVOURITE);
        if (res == null)
            throw new NullPointerException("The value of 'is_favourite' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code review_author} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getReviewAuthor() {
        String res = getStringOrNull(ReviewColumns.REVIEW_AUTHOR);
        return res;
    }

    /**
     * Get the {@code review_content} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getReviewContent() {
        String res = getStringOrNull(ReviewColumns.REVIEW_CONTENT);
        return res;
    }
}
