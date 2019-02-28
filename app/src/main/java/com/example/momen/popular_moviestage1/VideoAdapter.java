package com.example.momen.popular_moviestage1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.momen.popular_moviestage1.Model.Video;

import java.util.List;

/**
 * Created by Momen on 1/22/2019.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<Video> videos;
    private Context context;

    final private VideoItemClickListener mOnClickListener;

    public VideoAdapter (List<Video> videos, Context context, VideoItemClickListener mOnClickListener){
        this.videos = videos;
        this.context = context;
        this.mOnClickListener = mOnClickListener;
    }

    public interface VideoItemClickListener {
        void onVideoItemClick(int clickedItemIndex);
    }


    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.video_item,parent,false);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.name.setText(videos.get(position).getName().toString());
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;

        public VideoViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameVideo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onVideoItemClick(clickedPosition);
        }
    }
}
