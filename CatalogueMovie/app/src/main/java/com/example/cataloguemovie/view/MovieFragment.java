package com.example.cataloguemovie.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.cataloguemovie.R;
import com.example.cataloguemovie.adapter.MovieTvAdapter;
import com.example.cataloguemovie.model.PojoTvMovie;
import com.example.cataloguemovie.viewmodel.ViewModels;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements View.OnClickListener {

    ProgressBar progressBar;
    RecyclerView mRecyclerView;
    MovieTvAdapter movieTvAdapter;
    EditText edtMvSearch;
    Button btnSrc;
    private ViewModels mainViewModel;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        edtMvSearch = view.findViewById(R.id.tvSearch);
        btnSrc = view.findViewById(R.id.btnSearch);
        btnSrc.setOnClickListener(this);
        mRecyclerView = view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.progressBar_tv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        movieTvAdapter = new MovieTvAdapter("movie");
        movieTvAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(movieTvAdapter);


        showLoading(true);

        mainViewModel = ViewModelProviders.of(getActivity()).get(ViewModels.class);
        mainViewModel.setAllData("movie");
        mainViewModel.getData().observe(this, getMvData);
    }

    private Observer<ArrayList<PojoTvMovie>> getMvData = new Observer<ArrayList<PojoTvMovie>>() {
        @Override
        public void onChanged(ArrayList<PojoTvMovie> movieTvItems) {
            if (movieTvItems != null) {
                movieTvAdapter.setData(movieTvItems);
                showLoading(false);
            }
            movieTvAdapter.notifyDataSetChanged();
            Log.d("value-ob", movieTvItems.toString());
        }

    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSearch) {
            String mvname = edtMvSearch.getText().toString().trim();
            showLoading(true);
            mainViewModel = ViewModelProviders.of(getActivity()).get(ViewModels.class);
            mainViewModel.setSearch("movie", mvname);
            mainViewModel.getData().observe(this, getMvData);
        }
    }
}
