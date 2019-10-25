package com.example.cataloguemovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns;
import static com.example.cataloguemovie.db.DatabaseContract.TABLE_MOVIE;
import static com.example.cataloguemovie.db.DatabaseContract.TABLE_TVSHOW;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbFavoriteTvMovie";
    public static int DATABASE_VERSION = 3;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %S"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL)",
            TABLE_MOVIE,
            FavoriteColumns._ID,
            FavoriteColumns.TITLE,
            FavoriteColumns.VOTE_COUNT,
            FavoriteColumns.ORIGINAL_LANGUAGE,
            FavoriteColumns.OVERVIEW,
            FavoriteColumns.RELEASE_DATE,
            FavoriteColumns.VOTE_AVERAGE,
            FavoriteColumns.PHOTO
    );

    private static final String SQL_CREATE_TABLE_TVSHOW = String.format("CREATE TABLE %S"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL)",
            TABLE_TVSHOW,
            FavoriteColumns._ID,
            FavoriteColumns.TV_TITLE,
            FavoriteColumns.TV_VOTE_COUNT,
            FavoriteColumns.TV_ORIGINAL_LANGUAGE,
            FavoriteColumns.TV_OVERVIEW,
            FavoriteColumns.TV_RELEASE_DATE,
            FavoriteColumns.TV_VOTE_AVERAGE,
            FavoriteColumns.TV_PHOTO
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TVSHOW);
        onCreate(db);
    }
}
