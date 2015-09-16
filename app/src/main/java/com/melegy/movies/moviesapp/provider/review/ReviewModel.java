package com.melegy.movies.moviesapp.provider.review;

import android.support.annotation.Nullable;

import com.melegy.movies.moviesapp.provider.base.BaseModel;

/**
 * Data model for the {@code review} table.
 */
public interface ReviewModel extends BaseModel {

    /**
     * Get the {@code movie_id} value.
     */
    long getMovieId();

    /**
     * Get the {@code review_author} value.
     * Can be {@code null}.
     */
    @Nullable
    String getReviewAuthor();

    /**
     * Get the {@code review_content} value.
     * Can be {@code null}.
     */
    @Nullable
    String getReviewContent();
}
