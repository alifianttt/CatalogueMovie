package com.example.cataloguemovie.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cataloguemovie.R;
import com.example.cataloguemovie.model.PojoTvMovie;
import com.example.cataloguemovie.view.DetailMovie;
import com.example.cataloguemovie.view.DetailTvShow;

import java.util.ArrayList;

public class MovieTvAdapter extends RecyclerView.Adapter<MovieTvAdapter.ViewHolder> {
    private ArrayList<PojoTvMovie> mData = new ArrayList<>();

    public void setData(ArrayList<PojoTvMovie> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    private String CekFragment;

    public MovieTvAdapter(String fragment) {
        this.CekFragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder movieViewHolder, final int position) {
        final PojoTvMovie movie = mData.get(position);
        if (CekFragment == "movie") {
            String url_image = "https://image.tmdb.org/t/p/w185" + movie.getPhoto();
            //String vote_average = Double.toString(movie.getVote_average());

            movieViewHolder.textViewTitle.setText(movie.getTitle());
            movieViewHolder.textViewDateReleased.setText(movie.getRelease_date());
            movieViewHolder.textViewVoteAverage.setText(movie.getVote_average());
            movieViewHolder.textViewVoteCount.setText(movie.getVote_count());
            movieViewHolder.textViewOcerview.setText(movie.getOverview());
            Glide.with(movieViewHolder.itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(movieViewHolder.imgPhoto);
            movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailMovie.class);
                    intent.putExtra(DetailMovie.EXTRA_PERSON, mData.get(position));
                    v.getContext().startActivity(intent);
                }
            });
        } else {
            String url_image = "https://image.tmdb.org/t/p/w185" + movie.getPhoto();

            movieViewHolder.textViewTitle.setText(movie.getTitle());
            movieViewHolder.textViewDateReleased.setText(movie.getRelease_date());
            movieViewHolder.textViewVoteAverage.setText(movie.getVote_average());
            movieViewHolder.textViewVoteCount.setText(movie.getVote_count());
            movieViewHolder.textViewOcerview.setText(movie.getOverview());
            Glide.with(movieViewHolder.itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(movieViewHolder.imgPhoto);
            movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailTvShow.class);
                    intent.putExtra(DetailTvShow.EXTRA_TVSHOW, mData.get(position));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView textViewTitle, textViewDateReleased, textViewVoteAverage, textViewVoteCount, textViewOcerview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.mv_name);
            textViewDateReleased = itemView.findViewById(R.id.tv_item_dateReleased);
            textViewVoteAverage = itemView.findViewById(R.id.tv_item_voteAverege);
            textViewVoteCount = itemView.findViewById(R.id.tv_item_voteCount);
            textViewOcerview = itemView.findViewById(R.id.txt_description);
            imgPhoto = itemView.findViewById(R.id.img_photo);


        }

    }
}
