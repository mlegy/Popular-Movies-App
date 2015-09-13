package com.melegy.movies.moviesapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.melegy.movies.moviesapp.Model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ahmad on 12/08/15.
 */
public class ImageAdapter extends BaseAdapter {
    private ArrayList<String> urls;
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
        urls = new ArrayList<>();
    }

    public void addPosters(String[] posters){
        for (String poster : posters){
            urls.add(poster);
        }
        notifyDataSetChanged();

    }

    public Movie[] moviesArray(int size){
        return new Movie[size];
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public String getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SquaredImageView view = (SquaredImageView) convertView;
        if (view == null) {
            view = new SquaredImageView(mContext);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        // Get the image URL for the current position.
        String url = getItem(position);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(mContext) //
                .load(url) //
                .into(view);
        return view;
    }
}
