package com.example.cataloguemovie.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.cataloguemovie.LoadMvTvCallback;
import com.example.cataloguemovie.R;
import com.example.cataloguemovie.adapter.FavoriteTvAdapter;
import com.example.cataloguemovie.db.TvShowHelper;
import com.example.cataloguemovie.model.MoviTvFavorite;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.cataloguemovie.view.ShowTvFavorite.REQUEST_UPDATE;

public class TvShowFavorite extends AppCompatActivity implements LoadMvTvCallback {

    private RecyclerView rvMvFavorite;
    private ProgressBar progressBar;
    private static final String EXTRA_STATE = "extra_state";
    private FavoriteTvAdapter adapter;
    private TvShowHelper tvShowHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_favorite);

        rvMvFavorite = findViewById(R.id.rv_tv_fav);
        rvMvFavorite.setLayoutManager(new LinearLayoutManager(this));
        rvMvFavorite.setHasFixedSize(true);

        tvShowHelper = TvShowHelper.getInstance(getApplicationContext());
        tvShowHelper.open();

        progressBar = findViewById(R.id.progressbarfav);
        adapter = new FavoriteTvAdapter(this);
        rvMvFavorite.setAdapter(adapter);

        if (savedInstanceState == null) {
            new LoadTvAsync(tvShowHelper, this).execute();
        } else {
            ArrayList<MoviTvFavorite> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListTvFavorite(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListTvFav());
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
    public void postExecute(ArrayList<MoviTvFavorite> mvTvPojos) {
        progressBar.setVisibility(View.INVISIBLE);
        adapter.setListTvFavorite(mvTvPojos);
    }

    private static class LoadTvAsync extends AsyncTask<Void, Void, ArrayList<MoviTvFavorite>> {
        private final WeakReference<TvShowHelper> weakReference;
        private final WeakReference<LoadMvTvCallback> weakCallback;

        private LoadTvAsync(TvShowHelper tvShowHelper, LoadMvTvCallback callback) {
            weakReference = new WeakReference<>(tvShowHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<MoviTvFavorite> doInBackground(Void... voids) {
            return weakReference.get().getTVData();
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
                if (resultCode == ShowTvFavorite.RESULT_DELETE) {
                    int position = data.getIntExtra(ShowTvFavorite.EXTRA_POSITION, 0);

                    adapter.removeItem(position);
                    showSnackbar("One item Deleted");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvShowHelper.close();
    }

    private void showSnackbar(String message) {
        Snackbar.make(rvMvFavorite, message, Snackbar.LENGTH_SHORT).show();
    }
}
