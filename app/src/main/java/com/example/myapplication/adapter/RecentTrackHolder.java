package com.example.myapplication.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

public class RecentTrackHolder  extends RecyclerView.ViewHolder {

    TextView recent_TrackName, recent_ArtistName;

    public RecentTrackHolder(@NonNull View itemView) {
        super(itemView);
        //recent_TrackName = itemView.findViewById(R.id.recent_track_name);
        //recent_ArtistName = itemView.findViewById(R.id.recent_artist_name);
    }
}
