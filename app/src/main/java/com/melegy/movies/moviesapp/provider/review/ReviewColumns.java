package com.melegy.movies.moviesapp.provider.review;

import android.net.Uri;
import android.provider.BaseColumns;

import com.melegy.movies.moviesapp.provider.MovieProvider;
import com.melegy.movies.moviesapp.provider.movie.MovieColumns;

/**
 * Columns for the {@code review} table.
 */
public class ReviewColumns implements BaseColumns {
    public static final String TABLE_NAME = "review";
    public static final Uri CONTENT_URI = Uri.parse(MovieProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String MOVIE_ID = "movie_id";

    public static final String REVIEW_AUTHOR = "review_author";

    public static final String REVIEW_CONTENT = "review_content";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." + _ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[]{
            _ID,
            MOVIE_ID,
            REVIEW_AUTHOR,
            REVIEW_CONTENT
    };
    // @formatter:on
    public static final String PREFIX_MOVIE = TABLE_NAME + "__" + MovieColumns.TABLE_NAME;

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(MOVIE_ID) || c.contains("." + MOVIE_ID)) return true;
            if (c.equals(REVIEW_AUTHOR) || c.contains("." + REVIEW_AUTHOR)) return true;
            if (c.equals(REVIEW_CONTENT) || c.contains("." + REVIEW_CONTENT)) return true;
        }
        return false;
    }
}
