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
import com.example.cataloguemovie.view.ShowTvFavorite;

import java.util.ArrayList;

public class FavoriteTvAdapter extends RecyclerView.Adapter<FavoriteTvAdapter.FavoriteTVHolder> {

    private ArrayList<MoviTvFavorite> listTvFavorite = new ArrayList<>();
    private Activity activity;

    public FavoriteTvAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<MoviTvFavorite> getListTvFav() {
        return listTvFavorite;
    }

    public void setListTvFavorite(ArrayList<MoviTvFavorite> listTvFavorite) {
        if (listTvFavorite.size() < 0) {
            this.listTvFavorite.clear();
        }
        this.listTvFavorite.addAll(listTvFavorite);
        notifyDataSetChanged();
    }

    public void addItem(MoviTvFavorite moviTvFavorite) {
        this.listTvFavorite.add(moviTvFavorite);
        notifyItemInserted(listTvFavorite.size() - 1);
    }

    public void updateitem(int position, MoviTvFavorite moviTvFavorite) {
        this.listTvFavorite.set(position, moviTvFavorite);
        notifyItemChanged(position, moviTvFavorite);
    }

    public void removeItem(int position) {
        this.listTvFavorite.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listTvFavorite.size());
    }

    public void setData(ArrayList<MoviTvFavorite> items) {
        listTvFavorite.clear();
        listTvFavorite.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteTVHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_favorite, viewGroup, false);
        return new FavoriteTVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTVHolder favoriteTVHolder, final int i) {
        final MoviTvFavorite mvTvPojo = listTvFavorite.get(i);
        Glide.with(favoriteTVHolder.itemView.getContext())
                .load(mvTvPojo.getPhoto())
                .apply(new RequestOptions().override(55, 55))
                .into(favoriteTVHolder.imgPhoto);
        favoriteTVHolder.tvTitle.setText(mvTvPojo.getTitle());
        favoriteTVHolder.tvDescription.setText(mvTvPojo.getOverview());
        favoriteTVHolder.tvReleaseDate.setText(mvTvPojo.getRelease_date());
        favoriteTVHolder.tvVoteAverage.setText(mvTvPojo.getVote_average());
        favoriteTVHolder.tvVoteCount.setText(mvTvPojo.getVote_count());
        favoriteTVHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ShowTvFavorite.class);
                intent.putExtra(ShowTvFavorite.EXTRA_POSITION, i);
                intent.putExtra(ShowTvFavorite.EXTRA_LIST_TV_FAVORITE, listTvFavorite.get(i));
                intent.putExtra(ShowTvFavorite.EXTRA_LIST_TV_FAVORITE, mvTvPojo);
                activity.startActivityForResult(intent, ShowTvFavorite.REQUEST_UPDATE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTvFavorite.size();
    }

    public class FavoriteTVHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvReleaseDate, tvVoteAverage, tvVoteCount;
        ImageView imgPhoto;

        public FavoriteTVHolder(@NonNull View itemView) {
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

