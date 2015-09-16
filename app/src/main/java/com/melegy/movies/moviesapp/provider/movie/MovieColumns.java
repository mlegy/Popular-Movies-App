package com.melegy.movies.moviesapp.provider.movie;

import android.net.Uri;
import android.provider.BaseColumns;

import com.melegy.movies.moviesapp.provider.MovieProvider;

/**
 * Columns for the {@code movie} table.
 */
public class MovieColumns implements BaseColumns {
    public static final String TABLE_NAME = "movie";
    public static final Uri CONTENT_URI = Uri.parse(MovieProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String TITLE = "title";

    public static final String OVERVIEW = "overview";

    public static final String VOTE_AVERAGE = "vote_average";

    public static final String VOTE_COUNT = "vote_count";

    public static final String POPULARITY = "popularity";

    public static final String RELEASE_DATE = "release_date";

    public static final String POSTER = "poster";

    public static final String BACKDROP = "backdrop";

    public static final String IS_FAVOURITE = "is_favourite";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." + _ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[]{
            _ID,
            TITLE,
            OVERVIEW,
            VOTE_AVERAGE,
            VOTE_COUNT,
            POPULARITY,
            RELEASE_DATE,
            POSTER,
            BACKDROP,
            IS_FAVOURITE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(OVERVIEW) || c.contains("." + OVERVIEW)) return true;
            if (c.equals(VOTE_AVERAGE) || c.contains("." + VOTE_AVERAGE)) return true;
            if (c.equals(VOTE_COUNT) || c.contains("." + VOTE_COUNT)) return true;
            if (c.equals(POPULARITY) || c.contains("." + POPULARITY)) return true;
            if (c.equals(RELEASE_DATE) || c.contains("." + RELEASE_DATE)) return true;
            if (c.equals(POSTER) || c.contains("." + POSTER)) return true;
            if (c.equals(BACKDROP) || c.contains("." + BACKDROP)) return true;
            if (c.equals(IS_FAVOURITE) || c.contains("." + IS_FAVOURITE)) return true;
        }
        return false;
    }

}
