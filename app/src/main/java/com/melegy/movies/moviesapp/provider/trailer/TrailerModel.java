package com.melegy.movies.moviesapp.provider.trailer;

import android.support.annotation.NonNull;

import com.melegy.movies.moviesapp.provider.base.BaseModel;

/**
 * Data model for the {@code trailer} table.
 */
public interface TrailerModel extends BaseModel {

    /**
     * Get the {@code movie_id} value.
     */
    long getMovieId();

    /**
     * Get the {@code trailer_name} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getTrailerName();

    /**
     * Get the {@code trailer_key} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getTrailerKey();

    /**
     * Get the {@code trailer_site} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getTrailerSite();
}
