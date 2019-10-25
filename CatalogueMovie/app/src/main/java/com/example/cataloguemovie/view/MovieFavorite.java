package com.example.cataloguemovie.view;

import android.content.Intent;
import android.database.ContentObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.cataloguemovie.LoadMvTvCallback;
import com.example.cataloguemovie.R;
import com.example.cataloguemovie.adapter.FavoriteAdapter;
import com.example.cataloguemovie.db.MovieHelper;
import com.example.cataloguemovie.model.MoviTvFavorite;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.cataloguemovie.db.DatabaseContract.CONTENT_URI;
import static com.example.cataloguemovie.view.ShowMovieFavorite.REQUEST_UPDATE;

public class MovieFavorite extends AppCompatActivity implements LoadMvTvCallback {

    private RecyclerView rvMvFavorite;
    private ProgressBar progressBar;
    private static final String EXTRA_STATE = "extra_state";
    private FavoriteAdapter adapter;
    private MovieHelper mvTvHelper;
    private DataObserver myObserver;
    private static HandlerThread handlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_favorite);

        rvMvFavorite = findViewById(R.id.rv_movie_fav);
        rvMvFavorite.setLayoutManager(new LinearLayoutManager(this));
        rvMvFavorite.setHasFixedSize(true);

        mvTvHelper = MovieHelper.getInstance(getApplicationContext());
        mvTvHelper.open();

        progressBar = findViewById(R.id.progressbarfav);
        adapter = new FavoriteAdapter(this);
        rvMvFavorite.setAdapter(adapter);

        if (savedInstanceState == null) {
            new LoadAsync(mvTvHelper, this).execute();
        } else {
            ArrayList<MoviTvFavorite> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListFavorite(list);
            }
        }

        handlerThread = new HandlerThread("DataObserver ");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new DataObserver(handler, mvTvHelper);
        getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListFavorite());
    }

    @Override
    public void preExecute() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<MoviTvFavorite> listFavorite) {

        progressBar.setVisibility(View.INVISIBLE);
        adapter.setListFavorite(listFavorite);
    }

    private static class LoadAsync extends AsyncTask<Void, Void, ArrayList<MoviTvFavorite>> {
        private final WeakReference<MovieHelper> weakReference;
        private final WeakReference<LoadMvTvCallback> weakCallback;

        private LoadAsync(MovieHelper mvTvHelper, LoadMvTvCallback callback) {
            weakReference = new WeakReference<>(mvTvHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<MoviTvFavorite> doInBackground(Void... voids) {
            return weakReference.get().getAllData();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<MoviTvFavorite> moviTvFavorites) {
            super.onPostExecute(moviTvFavorites);
            weakCallback.get().postExecute(moviTvFavorites);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == REQUEST_UPDATE) {
                if (resultCode == ShowMovieFavorite.RESULT_DELETE) {
                    int position = data.getIntExtra(ShowMovieFavorite.EXTRA_POSITION, 0);

                    adapter.removeItem(position);
                    showSnackbar("One item Deleted");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvTvHelper.close();
    }

    private void showSnackbar(String message) {
        Snackbar.make(rvMvFavorite, message, Snackbar.LENGTH_SHORT).show();
    }

    public static class DataObserver extends ContentObserver {
        private MovieHelper movieHelper;

        public DataObserver(Handler handler, MovieHelper mvhelper) {
            super(handler);
            this.movieHelper = mvhelper;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadAsync(movieHelper, (LoadMvTvCallback) movieHelper).execute();
        }
    }
}

