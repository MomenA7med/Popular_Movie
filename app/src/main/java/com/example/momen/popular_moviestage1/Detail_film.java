package com.example.momen.popular_moviestage1;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.momen.popular_moviestage1.Model.GetMovieModel;
import com.example.momen.popular_moviestage1.Model.GetMovieModelFactory;
import com.example.momen.popular_moviestage1.Model.Movie;
import com.example.momen.popular_moviestage1.Model.Review;
import com.example.momen.popular_moviestage1.Model.Video;
import com.example.momen.popular_moviestage1.Utils.NetworkUtils;
import com.example.momen.popular_moviestage1.Utils.jsonUtils;
import com.example.momen.popular_moviestage1.database.DatabaseRoom;
import com.example.momen.popular_moviestage1.database.MovieEntry;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Detail_film extends AppCompatActivity implements VideoAdapter.VideoItemClickListener , LoaderManager.LoaderCallbacks<String>{

    TextView date,vote,overView;
    ImageView imageView;

    RecyclerView videoRV,reviewRV;
    List<Video> videoList;
    List<Review> reviewList;
    String isVideoOrReview;
    String Stitle;
    String Sdate;
    String SoverView;
    double Svote;
    String poster;
    private static final String vidoeBun = "vidoeB";
    private static final String reviewBun = "reviewB";
    int id;
    private static final int VIDEO_LOADER = 22;
    private static final int REVIEW_LOADER = 33;

    String jsonVideo , jsonReview;

    String videoUrlS,reviewUrlS;

    Intent intent;

    private DatabaseRoom db;

    Button btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);


        db = DatabaseRoom.getsInstance(getApplicationContext());


        date = findViewById(R.id.date);
        vote = findViewById(R.id.vote_average);
        overView = findViewById(R.id.overView);
        imageView = findViewById(R.id.imageView);
        btn = findViewById(R.id.save);


        videoList = new ArrayList<>();
        reviewList = new ArrayList<>();

        videoRV = findViewById(R.id.videoRV);
        reviewRV = findViewById(R.id.reviewRV);

        videoRV.setLayoutManager(new LinearLayoutManager(this));
        videoRV.setHasFixedSize(true);

        reviewRV.setLayoutManager(new LinearLayoutManager(this));
        reviewRV.setHasFixedSize(true);

        intent = getIntent();

        if(intent.getStringExtra("type").equals("popOrMost")){
            Stitle    = intent.getStringExtra("title");
            Sdate     =  intent.getStringExtra("date");
            SoverView = intent.getStringExtra("overView");
            Svote     = intent.getDoubleExtra("vote",-1);
            poster    = intent.getStringExtra("poster");
            id = intent.getIntExtra("id",-1);

            populateUi();

            if(savedInstanceState != null){
                videoUrlS = savedInstanceState.getString(vidoeBun);
                reviewUrlS = savedInstanceState.getString(reviewBun);
            }else {
                URL videoUrl = NetworkUtils.buildUrl("videos",id);
                videoUrlS = videoUrl.toString();
                URL reviewUrl = NetworkUtils.buildUrl("reviews",id);
                reviewUrlS = reviewUrl.toString();
            }
            Bundle bundleVideo = new Bundle();
            bundleVideo.putString(vidoeBun,videoUrlS);
            Bundle bundleReview = new Bundle();
            bundleReview.putString(reviewBun,reviewUrlS);


            LoaderManager manager = getSupportLoaderManager();

            manager.initLoader(VIDEO_LOADER,null,this);
            manager.initLoader(REVIEW_LOADER,null,this);

            Loader<String> videoLoader = manager.getLoader(VIDEO_LOADER);
            if (videoLoader == null) {
                manager.initLoader(VIDEO_LOADER, bundleVideo, this);
            } else {
                manager.restartLoader(VIDEO_LOADER, bundleVideo, this);
            }

            Loader<String> reviewLoader = manager.getLoader(REVIEW_LOADER);
            if (reviewLoader == null) {
                manager.initLoader(REVIEW_LOADER, bundleReview, this);
            } else {
                manager.restartLoader(REVIEW_LOADER, bundleReview, this);
            }

        }else if (intent.getStringExtra("type").equals("room")){
            btn.setVisibility(View.INVISIBLE);
            id = intent.getIntExtra("id",-1);
            final GetMovieModelFactory modelFactory = new GetMovieModelFactory(db,id);
            GetMovieModel movieModel = ViewModelProviders.of(this,modelFactory).get(GetMovieModel.class);
            movieModel.getMovie().observe(this, new Observer<MovieEntry>() {
                @Override
                public void onChanged(@Nullable MovieEntry movieEntry) {
                    id = movieEntry.getId();
                    Stitle = movieEntry.getOrignalTitle();
                    Sdate = movieEntry.getReleaseDate();
                    SoverView = movieEntry.getOverView();
                    Svote = movieEntry.getVoteAverage();
                    poster = movieEntry.getMoviePoster();
                    populateUi();
                    videoList = movieEntry.getVideosRoom();
                    reviewList = movieEntry.getReviewsRoom();
                    VideoAdapter videoAdapter = new VideoAdapter(videoList,Detail_film.this,Detail_film.this);
                    videoRV.setAdapter(videoAdapter);
                    ReviewAdapter reviewAdapter = new ReviewAdapter(reviewList,Detail_film.this);
                    reviewRV.setAdapter(reviewAdapter);
                }
            });
        }

    }

    private void populateUi(){
        date.setText(Sdate);
        overView.setText(SoverView);
        vote.setText(String.valueOf(Svote));
        setTitle(Stitle);

        String image = NetworkUtils.buildUrlImage(poster);
        Picasso.with(this)
                .load(image)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(imageView);
    }

    @Override
    public void onVideoItemClick(int clickedItemIndex) {

        String keyV = videoList.get(clickedItemIndex).getKey();
        String youtube = "https://www.youtube.com/watch?v="+keyV;

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(youtube));
        startActivity(i);
    }


    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            String result;

            @Override
            protected void onStartLoading() {
                if(args == null)
                    return;
                if (result != null)
                    deliverResult(result);
                else
                    forceLoad();
            }

            @Nullable
            @Override
            public String loadInBackground() {

                if(args.containsKey(vidoeBun)){

                    String vU = args.getString(vidoeBun);
                    if (vU == null || TextUtils.isEmpty(vU))
                        return null;
                    try {
                        URL video_url = new URL(vU);
                        String vidoeResults = NetworkUtils.getResponseFromHttpUrl(video_url);
                        isVideoOrReview = "video";
                        return vidoeResults;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }

                }else if (args.containsKey(reviewBun)){
                    String rU = args.getString(reviewBun);
                    if (rU == null || TextUtils.isEmpty(rU))
                        return null;
                    try {
                        URL review_url = new URL(rU);
                        String reviewResults = NetworkUtils.getResponseFromHttpUrl(review_url);
                        isVideoOrReview = "review";
                        return reviewResults;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
                return null;
            }

            @Override
            public void deliverResult(@Nullable String data) {

                if(args.containsKey(vidoeBun)) {
                    jsonVideo = result;
                    isVideoOrReview = "video";
                }
                else if(args.containsKey(reviewBun)) {
                    jsonReview = result;
                    isVideoOrReview = "review";
                }
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

        if(isVideoOrReview.equals("video")){
            videoList = jsonUtils.parseMovieVideo(data);

            VideoAdapter videoAdapter = new VideoAdapter(videoList,Detail_film.this,Detail_film.this);
            videoRV.setAdapter(videoAdapter);

        }
        else if (isVideoOrReview.equals("review")){
            reviewList = jsonUtils.parseMovieReview(data);

            ReviewAdapter reviewAdapter = new ReviewAdapter(reviewList,Detail_film.this);
            reviewRV.setAdapter(reviewAdapter);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(vidoeBun,videoUrlS);
        outState.putString(reviewBun,reviewUrlS);
    }

    public void saveOnRoom(View view) {

        final MovieEntry movieEntry = new MovieEntry(id,Stitle,poster,SoverView,Svote,Sdate,videoList,reviewList);
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.movieDio().insertMovie(movieEntry);
            }
        });
    }
}
