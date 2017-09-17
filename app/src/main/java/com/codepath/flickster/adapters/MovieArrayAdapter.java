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

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by yingbwan on 9/11/2017.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    public int getItemViewType(int position) {
        if (getItem(position).isPopular()){
            return 1;
        } else {
            return 0;
        }
    }

    public int getViewTypeCount() {
        return 2;  //only popular and non-popular
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the data item for position
        Movie movie = getItem(position);
        // Get the data item type for this position
        int type = getItemViewType(position);

        switch (type) {
            case 0: //normal movie
                ViewHolderNP viewHolder;

                if (convertView == null) {


                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.item_movie, parent, false);
                    viewHolder = new ViewHolderNP(convertView);
                  //  viewHolder.ivImage = convertView.findViewById(R.id.ivMovieImage);
                  //  viewHolder.tvTitle = convertView.findViewById(tvTitle);
                  //  viewHolder.tvOverview = convertView.findViewById(tvOverview);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolderNP) convertView.getTag();
                }

                viewHolder.ivImage.setImageResource(0);
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

                break;
            case 1:  //popular movie
                ViewHolderP viewHolder1;

                if (convertView == null) {

                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.item_movie_only, parent, false);
                    viewHolder1 = new ViewHolderP(convertView);
                    //viewHolder1.ivImage = convertView.findViewById(R.id.ivMovieImage);

                    convertView.setTag(viewHolder1);
                } else {
                    viewHolder1 = (ViewHolderP) convertView.getTag();
                }

                viewHolder1.ivImage.setImageResource(0);
                Picasso.with(getContext()).load(movie.getBackdropPath())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error)
                        //  .fit().centerInside()
                        .transform(new RoundedCornersTransformation(10, 10))
                        .into(viewHolder1.ivImage);

                break;
            default:
                //error out
        }


    /*    int orientation = getContext().getResources().getConfiguration().orientation;
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
    */
        return convertView;
    }

    static class ViewHolderP {

        @BindView(R.id.ivMovieImage) ImageView ivImage;

        public ViewHolderP(View view){
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderNP {

        @BindView(R.id.ivMovieImage) ImageView ivImage;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvOverview) TextView tvOverview;

        public ViewHolderNP(View view){
            ButterKnife.bind(this, view);
        }

    }

}
