package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Track implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
