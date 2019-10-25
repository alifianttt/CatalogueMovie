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
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.ORIGINAL_LANGUAGE;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.PHOTO;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.TITLE;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.VOTE_AVERAGE;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.VOTE_COUNT;
import static com.example.cataloguemovie.db.DatabaseContract.TABLE_MOVIE;

public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper databaseHelper;
    private static MovieHelper INSTANCE;

    private static SQLiteDatabase database;

    public MovieHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
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

    public ArrayList<MoviTvFavorite> getAllData() {
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
                mvTvPojo.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                mvTvPojo.setVote_count(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_COUNT)));
                mvTvPojo.setOriginal_language(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANGUAGE)));
                mvTvPojo.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                mvTvPojo.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                mvTvPojo.setVote_average(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
                mvTvPojo.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(PHOTO)));

                arrayList.add(mvTvPojo);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        Log.d("Table", arrayList.toString());
        return arrayList;
    }

    public long insertFavorite(MoviTvFavorite mvTvPojo) {
        ContentValues args = new ContentValues();
        args.put(TITLE, mvTvPojo.getTitle());
        args.put(VOTE_COUNT, mvTvPojo.getVote_count());
        args.put(ORIGINAL_LANGUAGE, mvTvPojo.getOriginal_language());
        args.put(OVERVIEW, mvTvPojo.getOverview());
        args.put(RELEASE_DATE, mvTvPojo.getRelease_date());
        args.put(VOTE_AVERAGE, mvTvPojo.getVote_average());
        args.put(PHOTO, mvTvPojo.getPhoto());
        return database.insert(DATABASE_TABLE, null, args);
    }


    public int deleteFavorite(int id) {
        return database.delete(TABLE_MOVIE, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
