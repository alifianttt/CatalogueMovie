package com.example.cataloguemovie.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
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

public class ShowTvFavorite extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_LIST_TV_FAVORITE = "extra_list_favorite";
    public static final String EXTRA_POSITION = "extra_position_tv";

    TextView tvTitle, tvVoteAverage, tvVoteCount, tvLanguage, tvOverview, tvRelease;
    private ProgressBar progressBar;
    ImageView imgFavorite;
    Button btnDelete;

    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;

    private MoviTvFavorite moviTvFavorite;
    private int position;
    private TvShowHelper tvShowHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tv_favorite);

        tvTitle = findViewById(R.id.tv_item_title);
        tvVoteAverage = findViewById(R.id.tv_item_voteAverege);
        tvVoteCount = findViewById(R.id.tv_item_voteCount);
        tvOverview = findViewById(R.id.tv_item_overview);
        tvLanguage = findViewById(R.id.tv_item_language);
        tvRelease = findViewById(R.id.tv_item_dateReleased);
        imgFavorite = findViewById(R.id.img_item_photo);

        btnDelete = findViewById(R.id.btn_delete_tv);
        btnDelete.setOnClickListener(this);

        tvShowHelper = TvShowHelper.getInstance(getApplicationContext());
        moviTvFavorite = getIntent().getParcelableExtra(EXTRA_LIST_TV_FAVORITE);

        progressBar = findViewById(R.id.progressMovieDetail);
        progressBar.setVisibility(View.VISIBLE);

        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {

                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MoviTvFavorite moviTvFavorite = getIntent().getParcelableExtra(EXTRA_LIST_TV_FAVORITE);
                        switch (moviTvFavorite.getOriginal_language()) {
                            case "en":
                                tvLanguage.setText("English");
                                break;
                            case "ja":
                                tvLanguage.setText("Japan");
                                break;
                            case "in":
                                tvLanguage.setText("Indonesia");
                                break;
                        }

                        tvTitle.setText(moviTvFavorite.getTitle());
                        tvVoteAverage.setText(moviTvFavorite.getVote_average());
                        tvVoteCount.setText(moviTvFavorite.getVote_count());
                        tvOverview.setText(moviTvFavorite.getOverview());
                        tvRelease.setText(moviTvFavorite.getRelease_date());

                        Glide.with(ShowTvFavorite.this)
                                .load(moviTvFavorite.getPhoto())
                                .placeholder(R.color.colorAccent)
                                .dontAnimate()
                                .into(imgFavorite);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_delete_tv) {
            showAlertDialog(ALERT_DIALOG_DELETE);
        }
    }

    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        if (!isDialogClose) {

            dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
            dialogTitle = "Hapus Tv Show";

            alertDialogBuilder.setTitle(dialogTitle);
            alertDialogBuilder
                    .setMessage(dialogMessage)
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            long result = tvShowHelper.deleteFavoriteTv(moviTvFavorite.getId());
                            if (result > 0) {
                                Intent intent = new Intent();
                                intent.putExtra(EXTRA_POSITION, position);
                                setResult(RESULT_DELETE, intent);
                                finish();
                            } else {
                                Toast.makeText(ShowTvFavorite.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            finish();
        }

    }
}
