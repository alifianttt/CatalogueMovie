package com.example.cataloguemovie.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cataloguemovie.R;
import com.example.cataloguemovie.db.TvShowHelper;
import com.example.cataloguemovie.model.MoviTvFavorite;
import com.example.cataloguemovie.model.PojoTvMovie;

public class DetailTvShow extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_TVSHOW = "Extra_Tv";
    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_TV_FAVORITE = "extra_tv_favorite";

    private boolean isEdit = false;
    public static final int RESULT_ADD = 101;
    private ProgressBar progressBar;

    TextView tvTitle, tvVoteAverage, tvRelease, tvLanguage, tvOverview, tvVoteCount;
    Button btnSaveMovie;
    ImageView imgPhoto;
    private TvShowHelper mvTvHelper;
    public PojoTvMovie movie;
    private MoviTvFavorite moviTvFavorite;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movietv_detail);

        tvTitle = findViewById(R.id.tv_item_title);
        tvOverview = findViewById(R.id.tv_item_overview);
        tvVoteAverage = findViewById(R.id.tv_item_voteAverege);
        imgPhoto = findViewById(R.id.img_item_photo);
        tvVoteCount = findViewById(R.id.tv_item_voteCount);
        tvLanguage = findViewById(R.id.tv_item_language);
        tvRelease = findViewById(R.id.tv_item_dateReleased);
        progressBar = findViewById(R.id.progressMovieDetail);
        btnSaveMovie = findViewById(R.id.btn_submit);
        progressBar.setVisibility(View.VISIBLE);
        btnSaveMovie.setOnClickListener(this);

        mvTvHelper = TvShowHelper.getInstance(getApplicationContext());
        mvTvHelper.open();
        moviTvFavorite = getIntent().getParcelableExtra(EXTRA_TV_FAVORITE);
        if (moviTvFavorite != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
            btnSaveMovie.setVisibility(View.GONE);
        } else {
            moviTvFavorite = new MoviTvFavorite();
        }

        final Handler handlertv = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                }

                handlertv.post(new Runnable() {
                    @Override
                    public void run() {
                        movie = getIntent().getParcelableExtra(EXTRA_TVSHOW);

                        //String vote_average = Double.toString(movie.getVote_average());
                        String url_image = "https://image.tmdb.org/t/p/w185" + movie.getPhoto();

                        switch (movie.getOriginal_language()) {
                            case "en":
                                tvLanguage.setText("English");
                                break;
                            case "in":
                                tvLanguage.setText("Indonesia");
                                break;
                        }
                        tvTitle.setText(movie.getTitle());
                        tvVoteAverage.setText(movie.getVote_average());
                        tvVoteCount.setText(movie.getVote_count());
                        tvOverview.setText(movie.getOverview());
                        tvRelease.setText(movie.getRelease_date());
                        Glide.with(DetailTvShow.this)
                                .load(url_image)
                                .placeholder(R.color.colorAccent)
                                .dontAnimate()
                                .into(imgPhoto);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            String title = tvTitle.getText().toString().trim();
            String vote_count = tvVoteCount.getText().toString().trim();
            String language = tvLanguage.getText().toString().trim();
            String overview = tvOverview.getText().toString().trim();
            String release_date = tvRelease.getText().toString().trim();
            String vote_average = tvVoteAverage.getText().toString().trim();
            String image = "https://image.tmdb.org/t/p/w185" + movie.getPhoto();

            moviTvFavorite.setTitle(title);
            moviTvFavorite.setVote_count(vote_count);
            moviTvFavorite.setOriginal_language(language);
            moviTvFavorite.setOverview(overview);
            moviTvFavorite.setRelease_date(release_date);
            moviTvFavorite.setVote_average(vote_average);
            moviTvFavorite.setPhoto(image);

            Intent intent = new Intent();
            intent.putExtra(EXTRA_TV_FAVORITE, moviTvFavorite);
            intent.putExtra(EXTRA_POSITION, position);

            if (!isEdit) {

                long result = mvTvHelper.insertFavoriteTV(moviTvFavorite);

                if (result > 0) {
                    moviTvFavorite.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    finish();
                    Toast.makeText(DetailTvShow.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailTvShow.this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
