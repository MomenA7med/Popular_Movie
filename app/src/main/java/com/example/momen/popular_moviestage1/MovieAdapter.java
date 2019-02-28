package com.example.momen.popular_moviestage1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.momen.popular_moviestage1.Model.Movie;
import com.example.momen.popular_moviestage1.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Momen on 11/30/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<String> moviesPoster;
    private Context context;

    final private ListItemClickListener mOnClickListener;


    public MovieAdapter(List<String> moviesPoster, Context context,ListItemClickListener listener){
        this.moviesPoster = moviesPoster;
        this.context = context;
        mOnClickListener = listener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_item,parent,false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        String movie_poster = moviesPoster.get(position);
        String image = NetworkUtils.buildUrlImage(movie_poster);
        Log.i("image",image);
        Picasso.with(context)
                .load(image)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return moviesPoster.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        public MovieViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
