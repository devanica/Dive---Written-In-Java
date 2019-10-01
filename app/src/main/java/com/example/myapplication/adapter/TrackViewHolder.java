package com.example.myapplication.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class TrackViewHolder {

    TextView track_name, artist_name;
    ImageView favourites_button, track_background;

    public TrackViewHolder(View view) {
        track_name = (TextView)view.findViewById(R.id.track_name);
        artist_name = (TextView)view.findViewById(R.id.artist_name);
        favourites_button = (ImageView)view.findViewById(R.id.favourites_button);
        track_background = (ImageView)view.findViewById(R.id.track_icon);
    }
}
