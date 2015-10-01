package com.melegy.movies.moviesapp.provider.trailer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.melegy.movies.moviesapp.provider.base.AbstractSelection;
import com.melegy.movies.moviesapp.provider.movie.MovieColumns;

/**
 * Selection for the {@code trailer} table.
 */
public class TrailerSelection extends AbstractSelection<TrailerSelection> {
    @Override
    protected Uri baseUri() {
        return TrailerColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection      A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code TrailerCursor} object, which is positioned before the first entry, or null.
     */
    public TrailerCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new TrailerCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public TrailerCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context    The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code TrailerCursor} object, which is positioned before the first entry, or null.
     */
    public TrailerCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new TrailerCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public TrailerCursor query(Context context) {
        return query(context, null);
    }


    public TrailerSelection id(long... value) {
        addEquals("trailer." + TrailerColumns._ID, toObjectArray(value));
        return this;
    }

    public TrailerSelection idNot(long... value) {
        addNotEquals("trailer." + TrailerColumns._ID, toObjectArray(value));
        return this;
    }

    public TrailerSelection orderById(boolean desc) {
        orderBy("trailer." + TrailerColumns._ID, desc);
        return this;
    }

    public TrailerSelection orderById() {
        return orderById(false);
    }

    public TrailerSelection movieId(long... value) {
        addEquals(TrailerColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public TrailerSelection movieIdNot(long... value) {
        addNotEquals(TrailerColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public TrailerSelection movieIdGt(long value) {
        addGreaterThan(TrailerColumns.MOVIE_ID, value);
        return this;
    }

    public TrailerSelection movieIdGtEq(long value) {
        addGreaterThanOrEquals(TrailerColumns.MOVIE_ID, value);
        return this;
    }

    public TrailerSelection movieIdLt(long value) {
        addLessThan(TrailerColumns.MOVIE_ID, value);
        return this;
    }

    public TrailerSelection movieIdLtEq(long value) {
        addLessThanOrEquals(TrailerColumns.MOVIE_ID, value);
        return this;
    }

    public TrailerSelection orderByMovieId(boolean desc) {
        orderBy(TrailerColumns.MOVIE_ID, desc);
        return this;
    }

    public TrailerSelection orderByMovieId() {
        orderBy(TrailerColumns.MOVIE_ID, false);
        return this;
    }

    public TrailerSelection movieTitle(String... value) {
        addEquals(MovieColumns.TITLE, value);
        return this;
    }

    public TrailerSelection movieTitleNot(String... value) {
        addNotEquals(MovieColumns.TITLE, value);
        return this;
    }

    public TrailerSelection movieTitleLike(String... value) {
        addLike(MovieColumns.TITLE, value);
        return this;
    }

    public TrailerSelection movieTitleContains(String... value) {
        addContains(MovieColumns.TITLE, value);
        return this;
    }

    public TrailerSelection movieTitleStartsWith(String... value) {
        addStartsWith(MovieColumns.TITLE, value);
        return this;
    }

    public TrailerSelection movieTitleEndsWith(String... value) {
        addEndsWith(MovieColumns.TITLE, value);
        return this;
    }

    public TrailerSelection orderByMovieTitle(boolean desc) {
        orderBy(MovieColumns.TITLE, desc);
        return this;
    }

    public TrailerSelection orderByMovieTitle() {
        orderBy(MovieColumns.TITLE, false);
        return this;
    }

    public TrailerSelection movieOverview(String... value) {
        addEquals(MovieColumns.OVERVIEW, value);
        return this;
    }

    public TrailerSelection movieOverviewNot(String... value) {
        addNotEquals(MovieColumns.OVERVIEW, value);
        return this;
    }

    public TrailerSelection movieOverviewLike(String... value) {
        addLike(MovieColumns.OVERVIEW, value);
        return this;
    }

    public TrailerSelection movieOverviewContains(String... value) {
        addContains(MovieColumns.OVERVIEW, value);
        return this;
    }

    public TrailerSelection movieOverviewStartsWith(String... value) {
        addStartsWith(MovieColumns.OVERVIEW, value);
        return this;
    }

    public TrailerSelection movieOverviewEndsWith(String... value) {
        addEndsWith(MovieColumns.OVERVIEW, value);
        return this;
    }

    public TrailerSelection orderByMovieOverview(boolean desc) {
        orderBy(MovieColumns.OVERVIEW, desc);
        return this;
    }

    public TrailerSelection orderByMovieOverview() {
        orderBy(MovieColumns.OVERVIEW, false);
        return this;
    }

    public TrailerSelection movieVoteAverage(Float... value) {
        addEquals(MovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public TrailerSelection movieVoteAverageNot(Float... value) {
        addNotEquals(MovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public TrailerSelection movieVoteAverageGt(float value) {
        addGreaterThan(MovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public TrailerSelection movieVoteAverageGtEq(float value) {
        addGreaterThanOrEquals(MovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public TrailerSelection movieVoteAverageLt(float value) {
        addLessThan(MovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public TrailerSelection movieVoteAverageLtEq(float value) {
        addLessThanOrEquals(MovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public TrailerSelection orderByMovieVoteAverage(boolean desc) {
        orderBy(MovieColumns.VOTE_AVERAGE, desc);
        return this;
    }

    public TrailerSelection orderByMovieVoteAverage() {
        orderBy(MovieColumns.VOTE_AVERAGE, false);
        return this;
    }

    public TrailerSelection movieVoteCount(Integer... value) {
        addEquals(MovieColumns.VOTE_COUNT, value);
        return this;
    }

    public TrailerSelection movieVoteCountNot(Integer... value) {
        addNotEquals(MovieColumns.VOTE_COUNT, value);
        return this;
    }

    public TrailerSelection movieVoteCountGt(int value) {
        addGreaterThan(MovieColumns.VOTE_COUNT, value);
        return this;
    }

    public TrailerSelection movieVoteCountGtEq(int value) {
        addGreaterThanOrEquals(MovieColumns.VOTE_COUNT, value);
        return this;
    }

    public TrailerSelection movieVoteCountLt(int value) {
        addLessThan(MovieColumns.VOTE_COUNT, value);
        return this;
    }

    public TrailerSelection movieVoteCountLtEq(int value) {
        addLessThanOrEquals(MovieColumns.VOTE_COUNT, value);
        return this;
    }

    public TrailerSelection orderByMovieVoteCount(boolean desc) {
        orderBy(MovieColumns.VOTE_COUNT, desc);
        return this;
    }

    public TrailerSelection orderByMovieVoteCount() {
        orderBy(MovieColumns.VOTE_COUNT, false);
        return this;
    }

    public TrailerSelection moviePopularity(Float... value) {
        addEquals(MovieColumns.POPULARITY, value);
        return this;
    }

    public TrailerSelection moviePopularityNot(Float... value) {
        addNotEquals(MovieColumns.POPULARITY, value);
        return this;
    }

    public TrailerSelection moviePopularityGt(float value) {
        addGreaterThan(MovieColumns.POPULARITY, value);
        return this;
    }

    public TrailerSelection moviePopularityGtEq(float value) {
        addGreaterThanOrEquals(MovieColumns.POPULARITY, value);
        return this;
    }

    public TrailerSelection moviePopularityLt(float value) {
        addLessThan(MovieColumns.POPULARITY, value);
        return this;
    }

    public TrailerSelection moviePopularityLtEq(float value) {
        addLessThanOrEquals(MovieColumns.POPULARITY, value);
        return this;
    }

    public TrailerSelection orderByMoviePopularity(boolean desc) {
        orderBy(MovieColumns.POPULARITY, desc);
        return this;
    }

    public TrailerSelection orderByMoviePopularity() {
        orderBy(MovieColumns.POPULARITY, false);
        return this;
    }

    public TrailerSelection movieReleaseDate(String... value) {
        addEquals(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailerSelection movieReleaseDateNot(String... value) {
        addNotEquals(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailerSelection movieReleaseDateLike(String... value) {
        addLike(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailerSelection movieReleaseDateContains(String... value) {
        addContains(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailerSelection movieReleaseDateStartsWith(String... value) {
        addStartsWith(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailerSelection movieReleaseDateEndsWith(String... value) {
        addEndsWith(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailerSelection orderByMovieReleaseDate(boolean desc) {
        orderBy(MovieColumns.RELEASE_DATE, desc);
        return this;
    }

    public TrailerSelection orderByMovieReleaseDate() {
        orderBy(MovieColumns.RELEASE_DATE, false);
        return this;
    }

    public TrailerSelection moviePoster(String... value) {
        addEquals(MovieColumns.POSTER, value);
        return this;
    }

    public TrailerSelection moviePosterNot(String... value) {
        addNotEquals(MovieColumns.POSTER, value);
        return this;
    }

    public TrailerSelection moviePosterLike(String... value) {
        addLike(MovieColumns.POSTER, value);
        return this;
    }

    public TrailerSelection moviePosterContains(String... value) {
        addContains(MovieColumns.POSTER, value);
        return this;
    }

    public TrailerSelection moviePosterStartsWith(String... value) {
        addStartsWith(MovieColumns.POSTER, value);
        return this;
    }

    public TrailerSelection moviePosterEndsWith(String... value) {
        addEndsWith(MovieColumns.POSTER, value);
        return this;
    }

    public TrailerSelection orderByMoviePoster(boolean desc) {
        orderBy(MovieColumns.POSTER, desc);
        return this;
    }

    public TrailerSelection orderByMoviePoster() {
        orderBy(MovieColumns.POSTER, false);
        return this;
    }

    public TrailerSelection movieBackdrop(String... value) {
        addEquals(MovieColumns.BACKDROP, value);
        return this;
    }

    public TrailerSelection movieBackdropNot(String... value) {
        addNotEquals(MovieColumns.BACKDROP, value);
        return this;
    }

    public TrailerSelection movieBackdropLike(String... value) {
        addLike(MovieColumns.BACKDROP, value);
        return this;
    }

    public TrailerSelection movieBackdropContains(String... value) {
        addContains(MovieColumns.BACKDROP, value);
        return this;
    }

    public TrailerSelection movieBackdropStartsWith(String... value) {
        addStartsWith(MovieColumns.BACKDROP, value);
        return this;
    }

    public TrailerSelection movieBackdropEndsWith(String... value) {
        addEndsWith(MovieColumns.BACKDROP, value);
        return this;
    }

    public TrailerSelection orderByMovieBackdrop(boolean desc) {
        orderBy(MovieColumns.BACKDROP, desc);
        return this;
    }

    public TrailerSelection orderByMovieBackdrop() {
        orderBy(MovieColumns.BACKDROP, false);
        return this;
    }

    public TrailerSelection movieIsFavourite(boolean value) {
        addEquals(MovieColumns.IS_FAVOURITE, toObjectArray(value));
        return this;
    }

    public TrailerSelection orderByMovieIsFavourite(boolean desc) {
        orderBy(MovieColumns.IS_FAVOURITE, desc);
        return this;
    }

    public TrailerSelection orderByMovieIsFavourite() {
        orderBy(MovieColumns.IS_FAVOURITE, false);
        return this;
    }

    public TrailerSelection trailerName(String... value) {
        addEquals(TrailerColumns.TRAILER_NAME, value);
        return this;
    }

    public TrailerSelection trailerNameNot(String... value) {
        addNotEquals(TrailerColumns.TRAILER_NAME, value);
        return this;
    }

    public TrailerSelection trailerNameLike(String... value) {
        addLike(TrailerColumns.TRAILER_NAME, value);
        return this;
    }

    public TrailerSelection trailerNameContains(String... value) {
        addContains(TrailerColumns.TRAILER_NAME, value);
        return this;
    }

    public TrailerSelection trailerNameStartsWith(String... value) {
        addStartsWith(TrailerColumns.TRAILER_NAME, value);
        return this;
    }

    public TrailerSelection trailerNameEndsWith(String... value) {
        addEndsWith(TrailerColumns.TRAILER_NAME, value);
        return this;
    }

    public TrailerSelection orderByTrailerName(boolean desc) {
        orderBy(TrailerColumns.TRAILER_NAME, desc);
        return this;
    }

    public TrailerSelection orderByTrailerName() {
        orderBy(TrailerColumns.TRAILER_NAME, false);
        return this;
    }

    public TrailerSelection trailerKey(String... value) {
        addEquals(TrailerColumns.TRAILER_KEY, value);
        return this;
    }

    public TrailerSelection trailerKeyNot(String... value) {
        addNotEquals(TrailerColumns.TRAILER_KEY, value);
        return this;
    }

    public TrailerSelection trailerKeyLike(String... value) {
        addLike(TrailerColumns.TRAILER_KEY, value);
        return this;
    }

    public TrailerSelection trailerKeyContains(String... value) {
        addContains(TrailerColumns.TRAILER_KEY, value);
        return this;
    }

    public TrailerSelection trailerKeyStartsWith(String... value) {
        addStartsWith(TrailerColumns.TRAILER_KEY, value);
        return this;
    }

    public TrailerSelection trailerKeyEndsWith(String... value) {
        addEndsWith(TrailerColumns.TRAILER_KEY, value);
        return this;
    }

    public TrailerSelection orderByTrailerKey(boolean desc) {
        orderBy(TrailerColumns.TRAILER_KEY, desc);
        return this;
    }

    public TrailerSelection orderByTrailerKey() {
        orderBy(TrailerColumns.TRAILER_KEY, false);
        return this;
    }

    public TrailerSelection trailerSite(String... value) {
        addEquals(TrailerColumns.TRAILER_SITE, value);
        return this;
    }

    public TrailerSelection trailerSiteNot(String... value) {
        addNotEquals(TrailerColumns.TRAILER_SITE, value);
        return this;
    }

    public TrailerSelection trailerSiteLike(String... value) {
        addLike(TrailerColumns.TRAILER_SITE, value);
        return this;
    }

    public TrailerSelection trailerSiteContains(String... value) {
        addContains(TrailerColumns.TRAILER_SITE, value);
        return this;
    }

    public TrailerSelection trailerSiteStartsWith(String... value) {
        addStartsWith(TrailerColumns.TRAILER_SITE, value);
        return this;
    }

    public TrailerSelection trailerSiteEndsWith(String... value) {
        addEndsWith(TrailerColumns.TRAILER_SITE, value);
        return this;
    }

    public TrailerSelection orderByTrailerSite(boolean desc) {
        orderBy(TrailerColumns.TRAILER_SITE, desc);
        return this;
    }

    public TrailerSelection orderByTrailerSite() {
        orderBy(TrailerColumns.TRAILER_SITE, false);
        return this;
    }
}
