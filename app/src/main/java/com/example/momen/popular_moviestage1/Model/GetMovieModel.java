package com.example.momen.popular_moviestage1.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.momen.popular_moviestage1.database.DatabaseRoom;
import com.example.momen.popular_moviestage1.database.MovieEntry;

/**
 * Created by Momen on 1/25/2019.
 */

public class GetMovieModel extends ViewModel {
    LiveData<MovieEntry> data;

    public GetMovieModel(DatabaseRoom room, int id){
        data = room.movieDio().getMovieById(id);
    }

    public LiveData<MovieEntry> getMovie(){
        return data;
    }
}
