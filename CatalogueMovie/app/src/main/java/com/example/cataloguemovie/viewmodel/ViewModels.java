package com.example.cataloguemovie.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.cataloguemovie.BuildConfig;
import com.example.cataloguemovie.model.PojoTvMovie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ViewModels extends ViewModel {
    private static final String API = BuildConfig.API_KEY;
    private MutableLiveData<ArrayList<PojoTvMovie>> listMovieTv = new MutableLiveData<ArrayList<PojoTvMovie>>();

    public void setAllData(final String type) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<PojoTvMovie> listMovie = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/" + type + "?api_key=" + API + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        PojoTvMovie movieAndTV = new PojoTvMovie(movie, type);
                        listMovie.add(movieAndTV);
                        Log.d("value", list.toString());
                    }
                    listMovieTv.postValue(listMovie);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void setSearch(final String type, String mvname) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<PojoTvMovie> listSearch = new ArrayList<>();
        String utl = "https://api.themoviedb.org/3/search/" + type + "?api_key=" + API + "&language=en-US&query=" + mvname;

        client.get(utl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObj = new JSONObject(result);
                    JSONArray list = responseObj.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movietv = list.getJSONObject(i);
                        PojoTvMovie movie = new PojoTvMovie(movietv, type);
                        listSearch.add(movie);
                    }
                    listMovieTv.postValue(listSearch);
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void GetUpcoming(final String type, String date) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<PojoTvMovie> listUpcoming = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + BuildConfig.API_KEY + "&primary_release_date.gte=" + date + "&primary_release_date.lte=" + date;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObj = new JSONObject(result);
                    JSONArray list = responseObj.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movietv = list.getJSONObject(i);
                        PojoTvMovie movie = new PojoTvMovie(movietv, type);
                        listUpcoming.add(movie);
                    }
                    listMovieTv.postValue(listUpcoming);
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public LiveData<ArrayList<PojoTvMovie>> getData() {
        return listMovieTv;
    }

}
