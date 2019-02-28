package com.example.momen.popular_moviestage1;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.momen.popular_moviestage1.Model.MainViewModel;
import com.example.momen.popular_moviestage1.Model.Movie;
import com.example.momen.popular_moviestage1.Utils.NetworkUtils;
import com.example.momen.popular_moviestage1.Utils.jsonUtils;
import com.example.momen.popular_moviestage1.database.DatabaseRoom;
import com.example.momen.popular_moviestage1.database.MovieEntry;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener{

    private RecyclerView mRecyclerView;

    List<Movie> movies;
    Context context;
    List<String> Posters;

    DatabaseRoom databaseRoom;


    List<Integer> movieEntriesId;

    String state = "popular";

    String cast;

    URL url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseRoom = DatabaseRoom.getsInstance(getApplicationContext());

        context = MainActivity.this;

        mRecyclerView = findViewById(R.id.rv);

        movies = new ArrayList<>();

        movieEntriesId = new ArrayList<>();

        Posters= new ArrayList<>();

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));

        mRecyclerView.setHasFixedSize(true);



        if (savedInstanceState ==  null)
        {

            url = NetworkUtils.buildUrl("popular");
            new MovieTask().execute(url);

        }
        else {
            String type = savedInstanceState.getString("type");
            if(type.equals("most"))
            {
                state = "most";
                url = NetworkUtils.buildUrl("top_rated");
                new MovieTask().execute(url);

            }
            else if (type.equals("room"))
            {
                state = "room";
                getData();
            }
            else if (type.equals("popular")){
                state ="popular";
                url = NetworkUtils.buildUrl("popular");
                new MovieTask().execute(url);
            }
        }

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {


        Intent intent = new Intent(MainActivity.this, Detail_film.class);

        if (state.equals("popular") || state.equals("most")) {
            intent.putExtra("title", movies.get(clickedItemIndex).getOrignalTitle());
            intent.putExtra("poster", movies.get(clickedItemIndex).getMoviePoster());
            intent.putExtra("overView", movies.get(clickedItemIndex).getOverView());
            intent.putExtra("vote", movies.get(clickedItemIndex).getVoteAverage());
            intent.putExtra("date", movies.get(clickedItemIndex).getReleaseDate());
            intent.putExtra("id", movies.get(clickedItemIndex).getId());
            intent.putExtra("type","popOrMost");
        }
        else if (state.equals("room")){
            intent.putExtra("type","room");
            intent.putExtra("id",movieEntriesId.get(clickedItemIndex));
        }
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("type",state);
    }



    public class MovieTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL url = params[0];
            String movieResults = null;
            try {
                movieResults = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieResults;
        }

        @Override
        protected void onPostExecute(String movieResults) {
            if (movieResults != null && !movieResults.equals("")) {
                movies = jsonUtils.parseMovieJson(movieResults);

                for (int i = 0 ; i < movies.size() ; i++) {
                    String pos = movies.get(i).getMoviePoster();
                    Posters.add(i,pos);
                }

                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));

                mRecyclerView.setHasFixedSize(true);

                MovieAdapter adapter = new MovieAdapter(Posters,MainActivity.this, (MovieAdapter.ListItemClickListener) context);

                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        URL url;

        switch (itemId) {
            case R.id.popular:
                Posters.clear();
                state = "popular";
                url = NetworkUtils.buildUrl("popular");
                new MovieTask().execute(url);

                return true;
            case R.id.highest:
                Posters.clear();
                state = "most";
                url = NetworkUtils.buildUrl("top_rated");
                new MovieTask().execute(url);

                return true;
            case R.id.favorite:
                state = "room";
                getData();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getData(){
        Posters.clear();
        movieEntriesId.clear();
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovie().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(@Nullable List<MovieEntry> movieEntries) {

                for (int i=0;i<movieEntries.size();i++){
                    Posters.add(i,movieEntries.get(i).getMoviePoster());
                    movieEntriesId.add(i,movieEntries.get(i).getId());
                }
                MovieAdapter adapter = new MovieAdapter(Posters,MainActivity.this, MainActivity.this);
                mRecyclerView.setAdapter(adapter);
            }
        });
    }
}

