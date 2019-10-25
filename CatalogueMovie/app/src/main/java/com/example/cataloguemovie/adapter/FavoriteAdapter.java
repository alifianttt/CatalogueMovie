package com.example.cataloguemovie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cataloguemovie.R;
import com.example.cataloguemovie.model.MoviTvFavorite;
import com.example.cataloguemovie.view.ShowMovieFavorite;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteviewHolder> {


    private ArrayList<MoviTvFavorite> listFavorite = new ArrayList<>();
    private Activity activity;

    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<MoviTvFavorite> getListFavorite() {
        return listFavorite;
    }

    public void setListFavorite(ArrayList<MoviTvFavorite> listFavorite) {
        if (listFavorite.size() > 0) {
            this.listFavorite.clear();
        }
        this.listFavorite.addAll(listFavorite);
        notifyDataSetChanged();
    }

    public void setData(ArrayList<MoviTvFavorite> items) {
        listFavorite.clear();
        listFavorite.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(MoviTvFavorite moviTvFavorite) {
        this.listFavorite.add(moviTvFavorite);
        notifyItemInserted(listFavorite.size() - 1);
    }

    public void updateitem(int position, MoviTvFavorite moviTvFavorite) {
        this.listFavorite.set(position, moviTvFavorite);
        notifyItemChanged(position, moviTvFavorite);
    }

    public void removeItem(int position) {
        this.listFavorite.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listFavorite.size());
    }

    @NonNull
    @Override
    public FavoriteviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_favorite, viewGroup, false);
        return new FavoriteviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteviewHolder favoriteviewHolder, final int i) {
        final MoviTvFavorite mvTvPojo = listFavorite.get(i);
        Glide.with(favoriteviewHolder.itemView.getContext())
                .load(mvTvPojo.getPhoto())
                .apply(new RequestOptions().override(55, 55))
                .into(favoriteviewHolder.imgPhoto);
        favoriteviewHolder.tvTitle.setText(mvTvPojo.getTitle());
        favoriteviewHolder.tvDescription.setText(mvTvPojo.getOverview());
        favoriteviewHolder.tvReleaseDate.setText(mvTvPojo.getRelease_date());
        favoriteviewHolder.tvVoteAverage.setText(mvTvPojo.getVote_average());
        favoriteviewHolder.tvVoteCount.setText(mvTvPojo.getVote_count());
        favoriteviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ShowMovieFavorite.class);
                intent.putExtra(ShowMovieFavorite.EXTRA_POSITION, i);
                intent.putExtra(ShowMovieFavorite.EXTRA_LIST_FAVORITE, listFavorite.get(i));
                intent.putExtra(ShowMovieFavorite.EXTRA_LIST_FAVORITE, mvTvPojo);
                activity.startActivityForResult(intent, ShowMovieFavorite.REQUEST_UPDATE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listFavorite.size();
    }

    public class FavoriteviewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvReleaseDate, tvVoteAverage, tvVoteCount;
        ImageView imgPhoto;

        public FavoriteviewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.mv_name);
            tvDescription = itemView.findViewById(R.id.txt_description);
            imgPhoto = itemView.findViewById(R.id.img_fav_photo);
            tvReleaseDate = itemView.findViewById(R.id.tv_dateReleased);
            tvVoteAverage = itemView.findViewById(R.id.tv_fav_voteAverege);
            tvVoteCount = itemView.findViewById(R.id.tv_fav_voteCount);
        }
    }
}
