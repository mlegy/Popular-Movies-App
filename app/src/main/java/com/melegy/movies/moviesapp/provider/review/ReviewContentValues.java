package com.melegy.movies.moviesapp.provider.review;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.melegy.movies.moviesapp.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code review} table.
 */
public class ReviewContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ReviewColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where           The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable ReviewSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param context The context to use.
     * @param where   The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable ReviewSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public ReviewContentValues putMovieId(long value) {
        mContentValues.put(ReviewColumns.MOVIE_ID, value);
        return this;
    }


    public ReviewContentValues putReviewAuthor(@Nullable String value) {
        mContentValues.put(ReviewColumns.REVIEW_AUTHOR, value);
        return this;
    }

    public ReviewContentValues putReviewAuthorNull() {
        mContentValues.putNull(ReviewColumns.REVIEW_AUTHOR);
        return this;
    }

    public ReviewContentValues putReviewContent(@Nullable String value) {
        mContentValues.put(ReviewColumns.REVIEW_CONTENT, value);
        return this;
    }

    public ReviewContentValues putReviewContentNull() {
        mContentValues.putNull(ReviewColumns.REVIEW_CONTENT);
        return this;
    }
}
