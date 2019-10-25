package com.example.cataloguemovie.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.ORIGINAL_LANGUAGE;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.PHOTO;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.TITLE;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.VOTE_AVERAGE;
import static com.example.cataloguemovie.db.DatabaseContract.FavoriteColumns.VOTE_COUNT;

public class MoviTvFavorite implements Parcelable {
    private int id;
    private String title;
    private String vote_count;
    private String original_language;
    private String overview;
    private String release_date;
    private String vote_average;
    private String photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public MoviTvFavorite() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.vote_count);
        dest.writeString(this.original_language);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.vote_average);
        dest.writeString(this.photo);
    }

    public MoviTvFavorite(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
        this.title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
        this.vote_count = cursor.getString(cursor.getColumnIndexOrThrow(VOTE_COUNT));
        this.original_language = cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANGUAGE));
        this.overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
        this.release_date = cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE));
        this.vote_average = cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE));
        this.photo = cursor.getString(cursor.getColumnIndexOrThrow(PHOTO));
    }

    protected MoviTvFavorite(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.vote_count = in.readString();
        this.original_language = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.vote_average = in.readString();
        this.photo = in.readString();
    }

    public static final Creator<MoviTvFavorite> CREATOR = new Creator<MoviTvFavorite>() {
        @Override
        public MoviTvFavorite createFromParcel(Parcel source) {
            return new MoviTvFavorite(source);
        }

        @Override
        public MoviTvFavorite[] newArray(int size) {
            return new MoviTvFavorite[size];
        }
    };
}
