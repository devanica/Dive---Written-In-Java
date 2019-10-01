package com.example.myapplication.model;

import java.io.Serializable;

public class Track implements Serializable {

    private long id;
    private String trackName;
    private String artistName;
    private String trackDuration;

    public Track(long id, String trackName, String artistName, String trackDuration) {
        this.id = id;
        this.trackName = trackName;
        this.artistName = artistName;
        this.trackDuration = trackDuration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTrackDuration() {
        return trackDuration;
    }

    public void setTrackDuration(String trackDuration) {
        this.trackDuration = trackDuration;
    }

}
