package com.example.cataloguemovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cataloguemovie.model.MoviTvFavorite;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.TV_ORIGINAL_LANGUAGE;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.TV_OVERVIEW;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.TV_PHOTO;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.TV_RELEASE_DATE;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.TV_TITLE;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.TV_VOTE_AVERAGE;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.TV_VOTE_COUNT;
import static com.example.cataloguemovie.db.DatabaseContract.TABLE_TVSHOW;

public class TvShowHelper {
    private static final String DATABASE_TABLE = TABLE_TVSHOW;
    private static DatabaseHelper databaseHelper;
    private static TvShowHelper INSTANCE;

    private static SQLiteDatabase database;

    public TvShowHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static TvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<MoviTvFavorite> getTVData() {
        ArrayList<MoviTvFavorite> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null, _ID);
        cursor.moveToFirst();
        MoviTvFavorite mvTvPojo;
        if (cursor.getCount() > 0) {
            do {
                mvTvPojo = new MoviTvFavorite();
                mvTvPojo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                mvTvPojo.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TV_TITLE)));
                mvTvPojo.setVote_count(cursor.getString(cursor.getColumnIndexOrThrow(TV_VOTE_COUNT)));
                mvTvPojo.setOriginal_language(cursor.getString(cursor.getColumnIndexOrThrow(TV_ORIGINAL_LANGUAGE)));
                mvTvPojo.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(TV_OVERVIEW)));
                mvTvPojo.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(TV_RELEASE_DATE)));
                mvTvPojo.setVote_average(cursor.getString(cursor.getColumnIndexOrThrow(TV_VOTE_AVERAGE)));
                mvTvPojo.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(TV_PHOTO)));

                arrayList.add(mvTvPojo);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        Log.d("Table", arrayList.toString());
        return arrayList;
    }

    public long insertFavoriteTV(MoviTvFavorite mvTvPojo) {
        ContentValues args = new ContentValues();
        args.put(TV_TITLE, mvTvPojo.getTitle());
        args.put(TV_VOTE_COUNT, mvTvPojo.getVote_count());
        args.put(TV_ORIGINAL_LANGUAGE, mvTvPojo.getOriginal_language());
        args.put(TV_OVERVIEW, mvTvPojo.getOverview());
        args.put(TV_RELEASE_DATE, mvTvPojo.getRelease_date());
        args.put(TV_VOTE_AVERAGE, mvTvPojo.getVote_average());
        args.put(TV_PHOTO, mvTvPojo.getPhoto());

        return database.insert(DATABASE_TABLE, null, args);
    }


    public int deleteFavoriteTv(int id) {
        return database.delete(TABLE_TVSHOW, _ID + " = '" + id + "'", null);
    }
}
