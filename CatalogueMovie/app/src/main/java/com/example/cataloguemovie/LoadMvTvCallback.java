package com.example.cataloguemovie;

import com.example.cataloguemovie.model.MoviTvFavorite;

import java.util.ArrayList;

public interface LoadMvTvCallback {
    void preExecute();

    void postExecute(ArrayList<MoviTvFavorite> mvTvPojos);
}
