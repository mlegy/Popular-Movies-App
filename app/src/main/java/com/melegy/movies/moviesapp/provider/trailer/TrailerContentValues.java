package com.melegy.movies.moviesapp.provider.trailer;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.melegy.movies.moviesapp.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code trailer} table.
 */
public class TrailerContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return TrailerColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where           The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable TrailerSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param context The context to use.
     * @param where   The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable TrailerSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public TrailerContentValues putMovieId(long value) {
        mContentValues.put(TrailerColumns.MOVIE_ID, value);
        return this;
    }


    public TrailerContentValues putTrailerName(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("trailerName must not be null");
        mContentValues.put(TrailerColumns.TRAILER_NAME, value);
        return this;
    }


    public TrailerContentValues putTrailerKey(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("trailerKey must not be null");
        mContentValues.put(TrailerColumns.TRAILER_KEY, value);
        return this;
    }


    public TrailerContentValues putTrailerSite(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("trailerSite must not be null");
        mContentValues.put(TrailerColumns.TRAILER_SITE, value);
        return this;
    }

}
