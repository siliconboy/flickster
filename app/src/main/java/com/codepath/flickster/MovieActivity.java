package com.codepath.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.codepath.flickster.adapters.MovieArrayAdapter;
import com.codepath.flickster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.loopj.android.http.AsyncHttpClient.log;

public class MovieActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    @BindView(R.id.lvMovies) ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        //lvItems = (ListView) findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        //AsyncHttp calls
    /*    AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults;
                try {
                    movieJsonResults = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));
                    movieAdapter.notifyDataSetChanged();
                    log.d("DEBUG", movieJsonResults.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
      */
        // OkHttp calls
        // should be a singleton
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                JSONArray movieJsonResults;
                try {
                    String responseData = response.body().string();
                    JSONObject results = new JSONObject(responseData);
                    movieJsonResults = results.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));

                    // Run view-related code back on the main thread
                    MovieActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            movieAdapter.notifyDataSetChanged();

                        }
                    });
                    log.d("DEBUG", movieJsonResults.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });

        //setupListViewListener();

    }

    @OnItemClick(R.id.lvMovies)
    void itemClick(int position) {
        Movie movie = movies.get(position);
        if (movie.isPopular()) {
            Intent i = new Intent(MovieActivity.this, PlayActivity.class);
            i.putExtra("movie", movie);
            startActivity(i);
        } else {
            Intent i = new Intent(MovieActivity.this, DetailActivity.class);
            i.putExtra("movie", movie);
            startActivity(i);
        }
    }
  /*  private void setupListViewListener() {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movies.get(position);
                if (movie.isPopular()) {
                    Intent i = new Intent(MovieActivity.this, PlayActivity.class);
                    i.putExtra("movie", movie);
                    startActivity(i);
                } else {
                    Intent i = new Intent(MovieActivity.this, DetailActivity.class);
                    i.putExtra("movie", movie);
                    startActivity(i);
                }
            }
        });
    }
*/
}

