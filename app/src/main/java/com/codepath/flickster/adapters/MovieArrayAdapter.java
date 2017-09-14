package com.codepath.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.codepath.flickster.R.id.tvOverview;
import static com.codepath.flickster.R.id.tvTitle;

/**
 * Created by yingbwan on 9/11/2017.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the data item for position
        Movie movie = getItem(position);
        // check existing view being reused
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);

            viewHolder.ivImage =  convertView.findViewById(R.id.ivMovieImage);
            viewHolder.tvTitle =  convertView.findViewById(tvTitle);
            viewHolder.tvOverview =  convertView.findViewById(tvOverview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //ImageView ivImage = (ImageView)convertView.findViewById(R.id.ivMovieImage);
        viewHolder.ivImage.setImageResource(0);

        //TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        //TextView tvOverview = (TextView)convertView.findViewById(R.id.tvOverview);

        //populate data
        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext()).load(movie.getPosterPath())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .fit().centerInside()
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(viewHolder.ivImage);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.with(getContext()).load(movie.getBackdropPath())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .fit().centerInside()
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(viewHolder.ivImage);
        }

        return convertView;
    }

    private static class ViewHolder {

        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;

    }
}
