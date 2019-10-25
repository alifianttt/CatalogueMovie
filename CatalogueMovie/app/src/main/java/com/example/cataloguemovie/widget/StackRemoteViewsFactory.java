package com.example.cataloguemovie.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.example.cataloguemovie.R;
import com.example.cataloguemovie.db.MovieHelper;
import com.example.cataloguemovie.model.MoviTvFavorite;

import java.util.ArrayList;
import java.util.List;


public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Bitmap> mWidgetItems = new ArrayList<>();
    private ArrayList<MoviTvFavorite> listfav = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;
    private Cursor cursor;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        this.mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.coba));
    }

    @Override
    public void onDataSetChanged() {
        final long token = Binder.clearCallingIdentity();
        getFavorite(mContext);
        Binder.restoreCallingIdentity(token);
        /*if (cursor != null){
            cursor.close();
        }

        cursor = mContext.getContentResolver().query(CONTENT_URI, null, null,null, null);
        */
    }

    private void getFavorite(Context context) {
        MovieHelper helper = new MovieHelper(context);
        helper.open();
        listfav = helper.getAllData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listfav.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        /*if (position == AdapterView.INVALID_POSITION || cursor == null || !cursor.moveToPosition(position)){
            return null;
        }*/
        //MoviTvFavorite moviTvFavorite = getItem(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Log.d("Value item database: ", listfav.toString());
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(listfav.get(position).getPhoto())
                    .into(200, 250)
                    .get();
            Log.v("message", listfav.get(position).getPhoto());
        } catch (Exception e) {

        }

        rv.setImageViewBitmap(R.id.imgwidget, bitmap);
        rv.setTextViewText(R.id.banner_text, listfav.get(position).getTitle());

        Bundle extras = new Bundle();
        extras.putInt(MvFavWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imgwidget, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /*private MoviTvFavorite getItem(int position){
        if (!cursor.moveToPosition(position)){
            throw new IllegalStateException("Position Invalid");
        }
        return new MoviTvFavorite(cursor);
    }*/
}
