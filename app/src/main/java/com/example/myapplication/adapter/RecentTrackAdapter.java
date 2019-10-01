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

public class RecentTrackAdapter extends RecyclerView.Adapter<RecentTrackHolder> {

    private ArrayList<Track> tracks;
    private Context context;

    public RecentTrackAdapter(ArrayList<Track> recentTrackItems, Context context) {
        this.tracks = recentTrackItems;
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
        holder.recent_TrackName.setText(tracks.get(position).getTrackName());
        holder.recent_ArtistName.setText(tracks.get(position).getArtistName());
    }

    @Override
    public int getItemCount() {
        return this.tracks.size();
    }
}
