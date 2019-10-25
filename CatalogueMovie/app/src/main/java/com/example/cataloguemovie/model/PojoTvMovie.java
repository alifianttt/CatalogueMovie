package com.example.cataloguemovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class PojoTvMovie implements Parcelable {
    private String vote_count;
    private String vote_average;
    private String title;
    private String popularity;
    private String original_language;
    private String overview;
    private String release_date;
    private String photo;

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.vote_count);
        dest.writeString(this.vote_average);
        dest.writeString(this.title);
        dest.writeString(this.popularity);
        dest.writeString(this.original_language);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.photo);
    }

    public PojoTvMovie() {
    }

    public PojoTvMovie(JSONObject object, String get_type) {
        try {

            String vote_count, title, original_language, overview, release_date, poster_path, popularity, vote_average;
            switch (get_type) {
                case "movie":
                    vote_count = object.getString("vote_count");
                    vote_average = object.getString("vote_average");
                    title = object.getString("title");
                    popularity = object.getString("popularity");
                    original_language = object.getString("original_language");
                    overview = object.getString("overview");
                    release_date = object.getString("release_date");
                    poster_path = object.getString("poster_path");

                    this.vote_count = vote_count;
                    this.vote_average = vote_average;
                    this.title = title;
                    this.popularity = popularity;
                    this.original_language = original_language;
                    this.overview = overview;
                    this.release_date = release_date;
                    this.photo = poster_path;
                    break;
                case "tv":
                    vote_count = object.getString("vote_count");
                    vote_average = object.getString("vote_average");
                    title = object.getString("name");
                    popularity = object.getString("popularity");
                    original_language = object.getString("original_language");
                    overview = object.getString("overview");
                    release_date = object.getString("first_air_date");
                    poster_path = object.getString("poster_path");

                    this.vote_count = vote_count;
                    this.vote_average = vote_average;
                    this.title = title;
                    this.popularity = popularity;
                    this.original_language = original_language;
                    this.overview = overview;
                    this.release_date = release_date;
                    this.photo = poster_path;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected PojoTvMovie(Parcel in) {
        this.vote_count = in.readString();
        this.vote_average = in.readString();
        this.title = in.readString();
        this.popularity = in.readString();
        this.original_language = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.photo = in.readString();
    }

    public static final Parcelable.Creator<PojoTvMovie> CREATOR = new Parcelable.Creator<PojoTvMovie>() {
        @Override
        public PojoTvMovie createFromParcel(Parcel source) {
            return new PojoTvMovie(source);
        }

        @Override
        public PojoTvMovie[] newArray(int size) {
            return new PojoTvMovie[size];
        }
    };
}
