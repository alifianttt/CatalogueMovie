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
public class TvShowFragment extends Fragment implements View.OnClickListener {

    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    MovieTvAdapter movieTvAdapter;
    EditText edtTvSearch;
    Button btnSearch;
    private ViewModels mainViewModel;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSearch = view.findViewById(R.id.btnTv);
        btnSearch.setOnClickListener(this);
        edtTvSearch = view.findViewById(R.id.tvSearch);
        mRecyclerView = view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.progressBar_tv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        movieTvAdapter = new MovieTvAdapter("tv_show");
        movieTvAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(movieTvAdapter);

        mainViewModel = ViewModelProviders.of(getActivity()).get(ViewModels.class);
        mainViewModel.setAllData("tv");
        mainViewModel.getData().observe(this, getTvData);

        showLoading(true);

    }


    private Observer<ArrayList<PojoTvMovie>> getTvData = new Observer<ArrayList<PojoTvMovie>>() {
        @Override
        public void onChanged(ArrayList<PojoTvMovie> movieTvItems) {
            if (movieTvItems != null) {
                movieTvAdapter.setData(movieTvItems);

                showLoading(false);
            }
            movieTvAdapter.notifyDataSetChanged();
            Log.d("value-otv", movieTvItems.toString());
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
        if (v.getId() == R.id.btnTv) {
            String tvname = edtTvSearch.getText().toString().trim();
            showLoading(true);
            mainViewModel = ViewModelProviders.of(getActivity()).get(ViewModels.class);
            mainViewModel.setSearch("tv", tvname);
            mainViewModel.getData().observe(this, getTvData);
        }
    }
}
