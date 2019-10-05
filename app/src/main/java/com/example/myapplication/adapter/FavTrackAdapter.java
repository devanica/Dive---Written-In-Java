package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.model.Track;

import java.util.ArrayList;
import java.util.List;

public class FavTrackAdapter extends RecyclerView.Adapter<RecentTrackHolder> {

    private List<Track> tracks = new ArrayList<>();

    @NonNull
    @Override
    public RecentTrackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_track, null);
        return new RecentTrackHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentTrackHolder holder, int position) {
        holder.recent_TrackName.setText(tracks.get(position).getTrackName());
        holder.recent_ArtistName.setText(tracks.get(position).getArtistName());
    }

    @Override
    public int getItemCount() {
        return this.tracks.size();
    }

    public void setFavTracks(List<Track> tracks){
        this.tracks = tracks;
        notifyDataSetChanged();
    }

}
