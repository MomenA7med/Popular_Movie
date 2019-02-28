package com.example.momen.popular_moviestage1.Model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.momen.popular_moviestage1.database.DatabaseRoom;
import com.example.momen.popular_moviestage1.database.MovieEntry;

import java.util.List;

/**
 * Created by Momen on 1/24/2019.
 */

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<MovieEntry>> listLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        DatabaseRoom databaseRoom = DatabaseRoom.getsInstance(this.getApplication());
        listLiveData = databaseRoom.movieDio().getAllMovies();
    }

    public LiveData<List<MovieEntry>> getMovie(){
        return listLiveData;
    }

}
