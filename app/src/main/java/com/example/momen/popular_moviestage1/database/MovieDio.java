package com.example.momen.popular_moviestage1.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Momen on 1/22/2019.
 */
@Dao
public interface MovieDio {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieEntry movieEntry );

    @Query("SELECT * FROM movie_table")
    LiveData<List<MovieEntry>> getAllMovies();

    @Delete
    void deleteMovie(MovieEntry movieEntry);

    @Query("SELECT * FROM movie_table WHERE id = :id")
    LiveData<MovieEntry> getMovieById(int id );

}
