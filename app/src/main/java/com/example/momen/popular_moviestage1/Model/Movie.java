package com.example.momen.popular_moviestage1.Model;

/**
 * Created by Momen on 11/30/2018.
 */

public class Movie {

    private int id;
    private String orignalTitle;
    private String moviePoster;
    private String overView;
    private double voteAverage;
    private String releaseDate;

    public Movie(){}

    public Movie(int id,String orignalTitle,String moviePoster,String overView,double voteAverage,String releaseDate){

        this.id = id;
        this.orignalTitle = orignalTitle;
        this.moviePoster = moviePoster;
        this.overView = overView;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
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
