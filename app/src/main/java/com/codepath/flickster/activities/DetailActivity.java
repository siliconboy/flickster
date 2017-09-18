package com.codepath.flickster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.ratingBar) RatingBar rating;
    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.tvDate) TextView tvDate;
    @BindView(R.id.tvPop) TextView tvPop;
    @BindView(R.id.ivMovie) ImageView ivImage;
    @BindString(R.string.date) String releaseDateLabel;
    @BindString(R.string.pop) String popularityLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        final Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        //TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(movie.getOriginalTitle());
        //RatingBar rating = (RatingBar) findViewById(R.id.ratingBar);
        rating.setVisibility(View.VISIBLE);
        rating.setRating((float) movie.getVoteAverage());
        //TextView tvOverview = (TextView) findViewById(tvOverview);
        tvOverview.setText(movie.getOverview());
        //TextView tvDate = (TextView) findViewById(tvDate);

        tvDate.setText(releaseDateLabel + movie.getReleaseDate());
        //TextView tvPop = (TextView) findViewById(tvPop);
        tvPop.setText(popularityLabel + String.valueOf(movie.getPopularity()));

        //ImageView ivImage = (ImageView) findViewById(R.id.ivMovie);

        Picasso.with(this).load(movie.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .fit() //.resize(100,100)
                .transform(new RoundedCornersTransformation(10, 10))
                .into(ivImage);
        // add click listener
        ivImage.setClickable(true);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, PlayActivity.class);
                i.putExtra("movie", movie);
                startActivity(i);
            }
        });
    }
}
