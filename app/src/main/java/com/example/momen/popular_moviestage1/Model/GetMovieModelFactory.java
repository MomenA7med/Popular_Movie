package com.example.momen.popular_moviestage1.Model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.momen.popular_moviestage1.database.DatabaseRoom;

/**
 * Created by Momen on 1/25/2019.
 */

public class GetMovieModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final DatabaseRoom mDb;
    private final int mMovieId;

    public GetMovieModelFactory(DatabaseRoom mDb , int mMovieId){
        this.mDb = mDb;
        this.mMovieId = mMovieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GetMovieModel(mDb,mMovieId);
    }

}
