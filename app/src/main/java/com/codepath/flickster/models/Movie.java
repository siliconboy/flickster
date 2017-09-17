package com.codepath.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yingbwan on 9/11/2017.
 */

public class Movie implements Serializable {
    private String posterPath;
    private String backdropPath;
    private String originalTitle;
    private String overview;
    private double voteAverage;
    private String id;
    private double popularity;
    private String releaseDate;


    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.id = jsonObject.getString("id");
        this.voteAverage = jsonObject.getDouble("vote_average");
        this.popularity = jsonObject.getDouble("popularity");
        this.releaseDate = jsonObject.getString("release_date");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {

            try {
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return results;
    }

    public boolean isPopular() {
        return voteAverage>5.0;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getId() {
        return id;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
