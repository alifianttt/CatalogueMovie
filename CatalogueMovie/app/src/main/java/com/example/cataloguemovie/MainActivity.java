package com.example.cataloguemovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.cataloguemovie.view.MovieFavorite;
import com.example.cataloguemovie.view.MovieFragment;
import com.example.cataloguemovie.view.SettingFragment;
import com.example.cataloguemovie.view.TvShowFavorite;
import com.example.cataloguemovie.view.TvShowFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.movie_nav:
                    String MovieBar = getString(R.string.movie_title);
                    getSupportActionBar().setTitle(MovieBar);
                    //Fragment Initialization
                    MovieFragment movieFragment = new MovieFragment();
                    //Fragment Transaction
                    FragmentTransaction fragmentMovieTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentMovieTransaction.replace(R.id.fragment_container, movieFragment, movieFragment.getClass().getSimpleName());
                    fragmentMovieTransaction.commit();
                    return true;
                case R.id.tvshow_nav:
                    String TvShow = getString(R.string.tvshow_title);
                    getSupportActionBar().setTitle(TvShow);
                    //Fragment Initialization
                    TvShowFragment tvShowFragment = new TvShowFragment();
                    //Fragment Transaction
                    FragmentTransaction fragmentTvShowTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTvShowTransaction.replace(R.id.fragment_container, tvShowFragment, tvShowFragment.getClass().getSimpleName());
                    fragmentTvShowTransaction.commit();
                    return true;
                case R.id.setting_nav:
                    String setting = getString(R.string.setting_title);
                    getSupportActionBar().setTitle(setting);
                    //Fragment Initialization
                    SettingFragment settingFragment = new SettingFragment();
                    //Fragment Transaction
                    FragmentTransaction fragmentsettingTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentsettingTransaction.replace(R.id.fragment_container, settingFragment);
                    fragmentsettingTransaction.commit();
                    return true;
            }
            return false;

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieFragment movieFragment = new MovieFragment();

        FragmentTransaction fragmentMvTransaction = getSupportFragmentManager().beginTransaction();
        fragmentMvTransaction.replace(R.id.fragment_container, movieFragment, movieFragment.getClass().getSimpleName());
        fragmentMvTransaction.commit();

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState != null) {
            bottomNav.setSelectedItemId(R.id.movie_nav);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setList(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void setList(int SelectList) {
        switch (SelectList) {
            case R.id.action_listmovie:
                Intent intent = new Intent(MainActivity.this, MovieFavorite.class);
                startActivity(intent);
                break;
            case R.id.action_listtv:
                Intent intent_tv = new Intent(MainActivity.this, TvShowFavorite.class);
                startActivity(intent_tv);
        }
    }
}
