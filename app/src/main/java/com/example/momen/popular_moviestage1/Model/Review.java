package com.example.momen.popular_moviestage1.Model;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Momen on 1/22/2019.
 */

public class Review {

    private String author;
    private String content;

    public Review(){}

    public Review(String author,String content){
        this.author = author;
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
