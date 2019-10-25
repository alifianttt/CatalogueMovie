package com.example.cataloguemovie.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;


public class DatabaseContract {
    public static String TABLE_MOVIE = "FAVORITE_MOVIE";
    public static String TABLE_TVSHOW = "FAVORITE_TVSHOW";
    public static final String AUTHORITY = "com.example.cataloguemovie";
    public static final String SCHEME = "content";

    public static final class FavoriteColumns implements BaseColumns {
        public static final String TITLE = "title";
        public static final String VOTE_COUNT = "vote_count";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String PHOTO = "photo";


        static final String TV_TITLE = "tv_title";
        static final String TV_VOTE_COUNT = "tv_vote_count";
        static final String TV_ORIGINAL_LANGUAGE = "tv_original_language";
        static final String TV_OVERVIEW = "tv_overview";
        static final String TV_RELEASE_DATE = "tv_release_date";
        static final String TV_VOTE_AVERAGE = "tv_vote_average";
        static final String TV_PHOTO = "tv_photo";


    }

    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
