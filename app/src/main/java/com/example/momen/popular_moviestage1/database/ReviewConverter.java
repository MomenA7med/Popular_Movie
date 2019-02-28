package com.example.momen.popular_moviestage1.database;

import android.arch.persistence.room.TypeConverter;

import com.example.momen.popular_moviestage1.Model.Review;
import com.example.momen.popular_moviestage1.Model.Video;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Created by Momen on 1/24/2019.
 */

public class ReviewConverter {
    final static Gson gson = new Gson();

    @TypeConverter
    public static List<Review> stringToReviewList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Review>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ReviewListToString(List<Review> someObjects) {
        return gson.toJson(someObjects);
    }
}
