package com.melegy.movies.moviesapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.melegy.movies.moviesapp.Model.Review;
import com.melegy.movies.moviesapp.R;

import java.util.ArrayList;

/**
 * Created by ahmad on 14/09/15.
 */
public class ReviewsAdapter extends ArrayAdapter<Review> {

    public ReviewsAdapter(Context context, ArrayList<Review> reviews) {
        super(context, R.layout.list_item_reviews, reviews);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Review review = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_reviews, parent, false);
            viewHolder.review_author = (TextView) convertView.findViewById(R.id.review_author);
            viewHolder.review_content = (TextView) convertView.findViewById(R.id.review_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.review_author.setText(review.getAuthor());
        viewHolder.review_author.setContentDescription(review.getAuthor());

        viewHolder.review_content.setText(review.getContent());
        viewHolder.review_content.setContentDescription(review.getContent());
        return convertView;

    }

    private static class ViewHolder {
        TextView review_author;
        TextView review_content;
    }
}
