package com.example.momen.popular_moviestage1.Utils;

import com.example.momen.popular_moviestage1.Model.Movie;
import com.example.momen.popular_moviestage1.Model.Review;
import com.example.momen.popular_moviestage1.Model.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Momen on 11/30/2018.
 */

public class jsonUtils {

    public static List<Movie> parseMovieJson(String json){

        List<Movie> movies = new ArrayList<>();

        final String ORIGINAL_TITLE = "original_title";
        final String POSTER = "poster_path";
        final String OVERVIEW = "overview";
        final String VOTE_AVERAGE = "vote_average";
        final String RELEASE_DATE = "release_date";
        final String RESULTS = "results";
        final String ID = "id";

        String orignalTitle;
        String moviePoster;
        String overView;
        double voteAverage;
        String releaseDate;
        int id;

        try {
            JSONObject MovieObject = new JSONObject(json);
            JSONArray Results = MovieObject.optJSONArray(RESULTS);
            JSONObject ResultArray;
            for (int i =0;i<Results.length();i++)
            {
                ResultArray = Results.getJSONObject(i);
                orignalTitle = ResultArray.optString(ORIGINAL_TITLE,"title");
                moviePoster = ResultArray.optString(POSTER,"poster");
                overView = ResultArray.optString(OVERVIEW,"overView");
                voteAverage = ResultArray.optDouble(VOTE_AVERAGE,1.5);
                releaseDate = ResultArray.optString(RELEASE_DATE,"1/1/1999");

                id = ResultArray.optInt(ID ,-1);

                movies.add(new Movie(id,orignalTitle,moviePoster,overView,voteAverage,releaseDate));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
    public static List<Video> parseMovieVideo(String json){
        final String KEY = "key";
        final String NAME = "name";
        final String RESULTS = "results";


        List<Video> videos = new ArrayList<>();
        String key,name ;

        try {
            JSONObject videoObject = new JSONObject(json);
            JSONArray videoResult = videoObject.optJSONArray(RESULTS);
            for (int i =0 ; i<videoResult.length() ; i++){

                JSONObject resObj = videoResult.optJSONObject(i);
                key = resObj.optString(KEY,"key");
                name = resObj.optString(NAME,"name");
                videos.add(new Video(key,name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return videos;
    }
    public static List<Review> parseMovieReview(String json){
        final String AUTHOR = "author";
        final String CONTENT = "content";
        final String RESULTS = "results";


        List<Review> reviews = new ArrayList<>();
        String author,content ;

        try {
            JSONObject reviewObject = new JSONObject(json);
            JSONArray reviewResult = reviewObject.optJSONArray(RESULTS);
            for (int i =0 ; i<reviewResult.length() ; i++){

                JSONObject resObj = reviewResult.optJSONObject(i);
                author = resObj.optString(AUTHOR,"author");
                content = resObj.optString(CONTENT,"content");
                reviews.add(new Review(author,content));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
