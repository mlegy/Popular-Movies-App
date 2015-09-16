package com.melegy.movies.moviesapp.provider.review;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.melegy.movies.moviesapp.provider.base.AbstractSelection;
import com.melegy.movies.moviesapp.provider.movie.MovieColumns;

/**
 * Selection for the {@code review} table.
 */
public class ReviewSelection extends AbstractSelection<ReviewSelection> {
    @Override
    protected Uri baseUri() {
        return ReviewColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ReviewCursor} object, which is positioned before the first entry, or null.
     */
    public ReviewCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ReviewCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public ReviewCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ReviewCursor} object, which is positioned before the first entry, or null.
     */
    public ReviewCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ReviewCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public ReviewCursor query(Context context) {
        return query(context, null);
    }


    public ReviewSelection id(long... value) {
        addEquals("review." + ReviewColumns._ID, toObjectArray(value));
        return this;
    }

    public ReviewSelection idNot(long... value) {
        addNotEquals("review." + ReviewColumns._ID, toObjectArray(value));
        return this;
    }

    public ReviewSelection orderById(boolean desc) {
        orderBy("review." + ReviewColumns._ID, desc);
        return this;
    }

    public ReviewSelection orderById() {
        return orderById(false);
    }

    public ReviewSelection movieId(long... value) {
        addEquals(ReviewColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public ReviewSelection movieIdNot(long... value) {
        addNotEquals(ReviewColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public ReviewSelection movieIdGt(long value) {
        addGreaterThan(ReviewColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewSelection movieIdGtEq(long value) {
        addGreaterThanOrEquals(ReviewColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewSelection movieIdLt(long value) {
        addLessThan(ReviewColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewSelection movieIdLtEq(long value) {
        addLessThanOrEquals(ReviewColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewSelection orderByMovieId(boolean desc) {
        orderBy(ReviewColumns.MOVIE_ID, desc);
        return this;
    }

    public ReviewSelection orderByMovieId() {
        orderBy(ReviewColumns.MOVIE_ID, false);
        return this;
    }

    public ReviewSelection movieTitle(String... value) {
        addEquals(MovieColumns.TITLE, value);
        return this;
    }

    public ReviewSelection movieTitleNot(String... value) {
        addNotEquals(MovieColumns.TITLE, value);
        return this;
    }

    public ReviewSelection movieTitleLike(String... value) {
        addLike(MovieColumns.TITLE, value);
        return this;
    }

    public ReviewSelection movieTitleContains(String... value) {
        addContains(MovieColumns.TITLE, value);
        return this;
    }

    public ReviewSelection movieTitleStartsWith(String... value) {
        addStartsWith(MovieColumns.TITLE, value);
        return this;
    }

    public ReviewSelection movieTitleEndsWith(String... value) {
        addEndsWith(MovieColumns.TITLE, value);
        return this;
    }

    public ReviewSelection orderByMovieTitle(boolean desc) {
        orderBy(MovieColumns.TITLE, desc);
        return this;
    }

    public ReviewSelection orderByMovieTitle() {
        orderBy(MovieColumns.TITLE, false);
        return this;
    }

    public ReviewSelection movieOverview(String... value) {
        addEquals(MovieColumns.OVERVIEW, value);
        return this;
    }

    public ReviewSelection movieOverviewNot(String... value) {
        addNotEquals(MovieColumns.OVERVIEW, value);
        return this;
    }

    public ReviewSelection movieOverviewLike(String... value) {
        addLike(MovieColumns.OVERVIEW, value);
        return this;
    }

    public ReviewSelection movieOverviewContains(String... value) {
        addContains(MovieColumns.OVERVIEW, value);
        return this;
    }

    public ReviewSelection movieOverviewStartsWith(String... value) {
        addStartsWith(MovieColumns.OVERVIEW, value);
        return this;
    }

    public ReviewSelection movieOverviewEndsWith(String... value) {
        addEndsWith(MovieColumns.OVERVIEW, value);
        return this;
    }

    public ReviewSelection orderByMovieOverview(boolean desc) {
        orderBy(MovieColumns.OVERVIEW, desc);
        return this;
    }

    public ReviewSelection orderByMovieOverview() {
        orderBy(MovieColumns.OVERVIEW, false);
        return this;
    }

    public ReviewSelection movieVoteAverage(Float... value) {
        addEquals(MovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public ReviewSelection movieVoteAverageNot(Float... value) {
        addNotEquals(MovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public ReviewSelection movieVoteAverageGt(float value) {
        addGreaterThan(MovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public ReviewSelection movieVoteAverageGtEq(float value) {
        addGreaterThanOrEquals(MovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public ReviewSelection movieVoteAverageLt(float value) {
        addLessThan(MovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public ReviewSelection movieVoteAverageLtEq(float value) {
        addLessThanOrEquals(MovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public ReviewSelection orderByMovieVoteAverage(boolean desc) {
        orderBy(MovieColumns.VOTE_AVERAGE, desc);
        return this;
    }

    public ReviewSelection orderByMovieVoteAverage() {
        orderBy(MovieColumns.VOTE_AVERAGE, false);
        return this;
    }

    public ReviewSelection movieVoteCount(Integer... value) {
        addEquals(MovieColumns.VOTE_COUNT, value);
        return this;
    }

    public ReviewSelection movieVoteCountNot(Integer... value) {
        addNotEquals(MovieColumns.VOTE_COUNT, value);
        return this;
    }

    public ReviewSelection movieVoteCountGt(int value) {
        addGreaterThan(MovieColumns.VOTE_COUNT, value);
        return this;
    }

    public ReviewSelection movieVoteCountGtEq(int value) {
        addGreaterThanOrEquals(MovieColumns.VOTE_COUNT, value);
        return this;
    }

    public ReviewSelection movieVoteCountLt(int value) {
        addLessThan(MovieColumns.VOTE_COUNT, value);
        return this;
    }

    public ReviewSelection movieVoteCountLtEq(int value) {
        addLessThanOrEquals(MovieColumns.VOTE_COUNT, value);
        return this;
    }

    public ReviewSelection orderByMovieVoteCount(boolean desc) {
        orderBy(MovieColumns.VOTE_COUNT, desc);
        return this;
    }

    public ReviewSelection orderByMovieVoteCount() {
        orderBy(MovieColumns.VOTE_COUNT, false);
        return this;
    }

    public ReviewSelection moviePopularity(Float... value) {
        addEquals(MovieColumns.POPULARITY, value);
        return this;
    }

    public ReviewSelection moviePopularityNot(Float... value) {
        addNotEquals(MovieColumns.POPULARITY, value);
        return this;
    }

    public ReviewSelection moviePopularityGt(float value) {
        addGreaterThan(MovieColumns.POPULARITY, value);
        return this;
    }

    public ReviewSelection moviePopularityGtEq(float value) {
        addGreaterThanOrEquals(MovieColumns.POPULARITY, value);
        return this;
    }

    public ReviewSelection moviePopularityLt(float value) {
        addLessThan(MovieColumns.POPULARITY, value);
        return this;
    }

    public ReviewSelection moviePopularityLtEq(float value) {
        addLessThanOrEquals(MovieColumns.POPULARITY, value);
        return this;
    }

    public ReviewSelection orderByMoviePopularity(boolean desc) {
        orderBy(MovieColumns.POPULARITY, desc);
        return this;
    }

    public ReviewSelection orderByMoviePopularity() {
        orderBy(MovieColumns.POPULARITY, false);
        return this;
    }

    public ReviewSelection movieReleaseDate(String... value) {
        addEquals(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewSelection movieReleaseDateNot(String... value) {
        addNotEquals(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewSelection movieReleaseDateLike(String... value) {
        addLike(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewSelection movieReleaseDateContains(String... value) {
        addContains(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewSelection movieReleaseDateStartsWith(String... value) {
        addStartsWith(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewSelection movieReleaseDateEndsWith(String... value) {
        addEndsWith(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewSelection orderByMovieReleaseDate(boolean desc) {
        orderBy(MovieColumns.RELEASE_DATE, desc);
        return this;
    }

    public ReviewSelection orderByMovieReleaseDate() {
        orderBy(MovieColumns.RELEASE_DATE, false);
        return this;
    }

    public ReviewSelection moviePoster(String... value) {
        addEquals(MovieColumns.POSTER, value);
        return this;
    }

    public ReviewSelection moviePosterNot(String... value) {
        addNotEquals(MovieColumns.POSTER, value);
        return this;
    }

    public ReviewSelection moviePosterLike(String... value) {
        addLike(MovieColumns.POSTER, value);
        return this;
    }

    public ReviewSelection moviePosterContains(String... value) {
        addContains(MovieColumns.POSTER, value);
        return this;
    }

    public ReviewSelection moviePosterStartsWith(String... value) {
        addStartsWith(MovieColumns.POSTER, value);
        return this;
    }

    public ReviewSelection moviePosterEndsWith(String... value) {
        addEndsWith(MovieColumns.POSTER, value);
        return this;
    }

    public ReviewSelection orderByMoviePoster(boolean desc) {
        orderBy(MovieColumns.POSTER, desc);
        return this;
    }

    public ReviewSelection orderByMoviePoster() {
        orderBy(MovieColumns.POSTER, false);
        return this;
    }

    public ReviewSelection movieBackdrop(String... value) {
        addEquals(MovieColumns.BACKDROP, value);
        return this;
    }

    public ReviewSelection movieBackdropNot(String... value) {
        addNotEquals(MovieColumns.BACKDROP, value);
        return this;
    }

    public ReviewSelection movieBackdropLike(String... value) {
        addLike(MovieColumns.BACKDROP, value);
        return this;
    }

    public ReviewSelection movieBackdropContains(String... value) {
        addContains(MovieColumns.BACKDROP, value);
        return this;
    }

    public ReviewSelection movieBackdropStartsWith(String... value) {
        addStartsWith(MovieColumns.BACKDROP, value);
        return this;
    }

    public ReviewSelection movieBackdropEndsWith(String... value) {
        addEndsWith(MovieColumns.BACKDROP, value);
        return this;
    }

    public ReviewSelection orderByMovieBackdrop(boolean desc) {
        orderBy(MovieColumns.BACKDROP, desc);
        return this;
    }

    public ReviewSelection orderByMovieBackdrop() {
        orderBy(MovieColumns.BACKDROP, false);
        return this;
    }

    public ReviewSelection movieIsFavourite(boolean value) {
        addEquals(MovieColumns.IS_FAVOURITE, toObjectArray(value));
        return this;
    }

    public ReviewSelection orderByMovieIsFavourite(boolean desc) {
        orderBy(MovieColumns.IS_FAVOURITE, desc);
        return this;
    }

    public ReviewSelection orderByMovieIsFavourite() {
        orderBy(MovieColumns.IS_FAVOURITE, false);
        return this;
    }

    public ReviewSelection reviewAuthor(String... value) {
        addEquals(ReviewColumns.REVIEW_AUTHOR, value);
        return this;
    }

    public ReviewSelection reviewAuthorNot(String... value) {
        addNotEquals(ReviewColumns.REVIEW_AUTHOR, value);
        return this;
    }

    public ReviewSelection reviewAuthorLike(String... value) {
        addLike(ReviewColumns.REVIEW_AUTHOR, value);
        return this;
    }

    public ReviewSelection reviewAuthorContains(String... value) {
        addContains(ReviewColumns.REVIEW_AUTHOR, value);
        return this;
    }

    public ReviewSelection reviewAuthorStartsWith(String... value) {
        addStartsWith(ReviewColumns.REVIEW_AUTHOR, value);
        return this;
    }

    public ReviewSelection reviewAuthorEndsWith(String... value) {
        addEndsWith(ReviewColumns.REVIEW_AUTHOR, value);
        return this;
    }

    public ReviewSelection orderByReviewAuthor(boolean desc) {
        orderBy(ReviewColumns.REVIEW_AUTHOR, desc);
        return this;
    }

    public ReviewSelection orderByReviewAuthor() {
        orderBy(ReviewColumns.REVIEW_AUTHOR, false);
        return this;
    }

    public ReviewSelection reviewContent(String... value) {
        addEquals(ReviewColumns.REVIEW_CONTENT, value);
        return this;
    }

    public ReviewSelection reviewContentNot(String... value) {
        addNotEquals(ReviewColumns.REVIEW_CONTENT, value);
        return this;
    }

    public ReviewSelection reviewContentLike(String... value) {
        addLike(ReviewColumns.REVIEW_CONTENT, value);
        return this;
    }

    public ReviewSelection reviewContentContains(String... value) {
        addContains(ReviewColumns.REVIEW_CONTENT, value);
        return this;
    }

    public ReviewSelection reviewContentStartsWith(String... value) {
        addStartsWith(ReviewColumns.REVIEW_CONTENT, value);
        return this;
    }

    public ReviewSelection reviewContentEndsWith(String... value) {
        addEndsWith(ReviewColumns.REVIEW_CONTENT, value);
        return this;
    }

    public ReviewSelection orderByReviewContent(boolean desc) {
        orderBy(ReviewColumns.REVIEW_CONTENT, desc);
        return this;
    }

    public ReviewSelection orderByReviewContent() {
        orderBy(ReviewColumns.REVIEW_CONTENT, false);
        return this;
    }
}
