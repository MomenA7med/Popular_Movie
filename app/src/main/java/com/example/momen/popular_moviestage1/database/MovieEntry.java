package com.example.momen.popular_moviestage1.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;

import com.example.momen.popular_moviestage1.Model.Review;
import com.example.momen.popular_moviestage1.Model.Video;

import java.util.List;

/**
 * Created by Momen on 1/22/2019.
 */
@Entity(tableName = "movie_table")
public class MovieEntry {
    @PrimaryKey
    private int id;

    private String orignalTitle;
    private String moviePoster;
    private String overView;
    private double voteAverage;
    private String releaseDate;
    @ColumnInfo(name = "video")
    private List<Video> videosRoom;
    @ColumnInfo(name = "review")
    private List<Review> reviewsRoom;


    public MovieEntry(int id,String orignalTitle,String moviePoster,String overView,double voteAverage,String releaseDate,List<Video> videosRoom,List<Review> reviewsRoom){

        this.id = id;
        this.orignalTitle = orignalTitle;
        this.moviePoster = moviePoster;
        this.overView = overView;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.videosRoom = videosRoom;
        this.reviewsRoom = reviewsRoom;
    }

    public void setReviewsRoom(List<Review> reviewsRoom) {
        this.reviewsRoom = reviewsRoom;
    }

    public List<Review> getReviewsRoom() {
        return reviewsRoom;
    }

    public void setVideosRoom(List<Video> videosRoom) {
        this.videosRoom = videosRoom;
    }

    public List<Video> getVideosRoom() {
        return videosRoom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setOrignalTitle(String orignalTitle) {
        this.orignalTitle = orignalTitle;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getOrignalTitle() {
        return orignalTitle;
    }

    public String getOverView() {
        return overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

}
