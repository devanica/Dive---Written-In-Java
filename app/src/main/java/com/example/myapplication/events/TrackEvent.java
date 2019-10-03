package com.example.myapplication.events;

import com.example.myapplication.model.Track;

public class TrackEvent {

    public Track track;

    public TrackEvent(Track track) {
        this.track = track;
    }
}
