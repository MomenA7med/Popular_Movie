package com.example.momen.popular_moviestage1.Model;

/**
 * Created by Momen on 1/22/2019.
 */

public class Video {
    private String key;
    private String name;

    public Video(){}

    public Video(String key,String name){
        this.key = key;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
