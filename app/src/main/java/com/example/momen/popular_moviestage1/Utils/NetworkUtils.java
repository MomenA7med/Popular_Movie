package com.example.momen.popular_moviestage1.Utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Momen on 11/30/2018.
 */

public class NetworkUtils {

    final static String MOVIE_BASE_URL = "https://api.themoviedb.org";

    final static String IMAGE_BASE_URL = "http://image.tmdb.org";

    final static String API_KEY = "api_key";

    final static String ApiKey = "6d89f684b057e46ab7d2d819ecd24529";

    public static URL buildUrl (String type)
    {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .path("3")
                .appendPath("movie")
                .appendPath(type)
                .appendQueryParameter(API_KEY, ApiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String buildUrlImage (String poster){
        String newPoster = "";
        for(int i =0 ;i<poster.length();i++){
            if(i==0)
                continue;
            newPoster += poster.charAt(i);
        }

        Uri builtUri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .path("t")
                .appendPath("p")
                .appendPath("w185")
                .appendPath(newPoster)
                .build();
        return builtUri.toString();
    }

    public static URL buildUrl (String type,int id){
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .path("3")
                .appendPath("movie")
                .appendPath(String.valueOf(id))
                .appendPath(type)
                .appendQueryParameter(API_KEY, ApiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
