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

import java.util.List;

public class RecentTrackAdapter extends RecyclerView.Adapter<RecentTrackHolder> {

    private List<Track> albums;
    private Context context;

    public RecentTrackAdapter(List<Track> recentTrackItems, Context context) {
        this.albums = recentTrackItems;
        this.context = context;
    }

    @NonNull
    @Override
    public RecentTrackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_track, null);
        return new RecentTrackHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentTrackHolder holder, int position) {
        holder.recent_TrackName.setText(albums.get(position).getTrackName());
        holder.recent_ArtistName.setText(albums.get(position).getArtistName());
    }

    @Override
    public int getItemCount() {
        return this.albums.size();
    }
}
