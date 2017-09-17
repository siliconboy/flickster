package com.codepath.flickster;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.codepath.flickster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.loopj.android.http.AsyncHttpClient.log;

public class PlayActivity extends YouTubeBaseActivity {
    @BindView(R.id.vPlay)
    YouTubePlayerView youTubePlayerView;
    @BindView(R.id.tvError)
    TextView tvError;
    JSONObject jObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        String prefix = "https://api.themoviedb.org/3/movie/";
        String suffix = "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        String url = prefix + movie.getId() + suffix;
        // asyncHttp calls
     /*   AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults;
                try {
                    movieJsonResults = response.getJSONArray("results");
                    jObj = movieJsonResults.getJSONObject(0);

                    log.d("DEBUG", movieJsonResults.toString());
                    String videoKey = "5xVh-7ywKpE";
                    try {
                        videoKey = jObj.getString("key");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final String vKey = videoKey;
                    YouTubePlayerView youTubePlayerView =
                            (YouTubePlayerView) findViewById(R.id.vPlay);

                    youTubePlayerView.initialize("a07e22bc18f5cb106bfe4cc1f83ad8ed",
                            new YouTubePlayer.OnInitializedListener() {
                                @Override
                                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                    YouTubePlayer youTubePlayer, boolean b) {

                                    // do any work here to cue video, play video, etc.
                                    youTubePlayer.loadVideo(vKey);
                                    youTubePlayer.play();
                                }

                                @Override
                                public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                    YouTubeInitializationResult youTubeInitializationResult) {
                                    Toast.makeText(getBaseContext(), "Could not load movie trailer!", Toast.LENGTH_LONG).show();
                                }
                            });

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
        // OkHTTP calls
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
                    String videoKey = "";
                    if (movieJsonResults.length() != 0) {
                        JSONObject jObj = movieJsonResults.getJSONObject(0);
                        try {
                            videoKey = jObj.getString("key");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    final String vKey = videoKey;

                    // Run view-related code back on the main thread
                    PlayActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //YouTubePlayerView youTubePlayerView =
                            //        (YouTubePlayerView) findViewById(R.id.vPlay);
                            if(vKey==null || vKey.isEmpty()) {
                                tvError.setVisibility(View.VISIBLE);
                                return;
                            }
                            youTubePlayerView.initialize("a07e22bc18f5cb106bfe4cc1f83ad8ed",
                                    new YouTubePlayer.OnInitializedListener() {
                                        @Override
                                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                            YouTubePlayer youTubePlayer, boolean b) {

                                            // do any work here to cue video, play video, etc.
                                            youTubePlayer.loadVideo(vKey);
                                            youTubePlayer.play();
                                        }

                                        @Override
                                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                            YouTubeInitializationResult youTubeInitializationResult) {
                                            tvError.setVisibility(View.VISIBLE);
                                        }
                                    });

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

    }
}
