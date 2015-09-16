package com.melegy.movies.moviesapp.provider.trailer;

import android.net.Uri;
import android.provider.BaseColumns;

import com.melegy.movies.moviesapp.provider.MovieProvider;
import com.melegy.movies.moviesapp.provider.movie.MovieColumns;

/**
 * Columns for the {@code trailer} table.
 */
public class TrailerColumns implements BaseColumns {
    public static final String TABLE_NAME = "trailer";
    public static final Uri CONTENT_URI = Uri.parse(MovieProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String MOVIE_ID = "movie_id";

    public static final String TRAILER_NAME = "trailer_name";

    public static final String TRAILER_KEY = "trailer_key";

    public static final String TRAILER_SITE = "trailer_site";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." + _ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[]{
            _ID,
            MOVIE_ID,
            TRAILER_NAME,
            TRAILER_KEY,
            TRAILER_SITE
    };
    // @formatter:on
    public static final String PREFIX_MOVIE = TABLE_NAME + "__" + MovieColumns.TABLE_NAME;

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(MOVIE_ID) || c.contains("." + MOVIE_ID)) return true;
            if (c.equals(TRAILER_NAME) || c.contains("." + TRAILER_NAME)) return true;
            if (c.equals(TRAILER_KEY) || c.contains("." + TRAILER_KEY)) return true;
            if (c.equals(TRAILER_SITE) || c.contains("." + TRAILER_SITE)) return true;
        }
        return false;
    }
}
