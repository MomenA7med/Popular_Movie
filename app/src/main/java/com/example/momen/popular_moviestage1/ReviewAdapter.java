package com.example.momen.popular_moviestage1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.momen.popular_moviestage1.Model.Review;

import java.util.List;

/**
 * Created by Momen on 1/22/2019.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private List<Review> reviews;
    private Context context;

    public ReviewAdapter(List<Review> reviews , Context context){
        this.reviews = reviews;
        this.context = context;
    }

    public interface ReviewItemClickListener{
        void onReviewItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_item,parent,false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {

        holder.author.setText(reviews.get(position).getAuthor());
        holder.content.setText(reviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder{

        TextView author , content;

        public ReviewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.authorTV);
            content = itemView.findViewById(R.id.contentTV);
        }


    }
}
