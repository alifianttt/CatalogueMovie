package com.example.cataloguemovie;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.cataloguemovie.db.MovieHelper;
import com.example.cataloguemovie.view.MovieFavorite;

import static com.example.cataloguemovie.db.DatabaseContract.AUTHORITY;
import static com.example.cataloguemovie.db.DatabaseContract.CONTENT_URI;
import static com.example.cataloguemovie.db.DatabaseContract.TABLE_MOVIE;

public class MovieProvider extends ContentProvider {

    private static final int MOVIE = 100;
    private static final int MOVIE_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MovieHelper movieHelper;


    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, MOVIE);

        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE + "/#", MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        movieHelper.open();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long add;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                add = movieHelper.insertProvider(values);
                break;
            default:
                add = 0;
                break;
        }
        if (add > 0) {
            getContext().getContentResolver().notifyChange(CONTENT_URI, new MovieFavorite.DataObserver(new Handler(), movieHelper));
        }

        return Uri.parse(CONTENT_URI + "/" + add);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new MovieFavorite.DataObserver(new Handler(), movieHelper));
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        movieHelper.open();
        int update;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                update = movieHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                update = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new MovieFavorite.DataObserver(new Handler(), movieHelper));
        return update;
    }
}
