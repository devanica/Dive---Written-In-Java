package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fav_tracks")
public class Track implements Parcelable {

    @ColumnInfo(name = "track_id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String trackName;
    private String artistName;
    private String trackDuration;
    private boolean isAddedIntoFav;

    public Track(long id, String trackName, String artistName, String trackDuration) {
        this.id = id;
        this.trackName = trackName;
        this.artistName = artistName;
        this.trackDuration = trackDuration;
    }

    protected Track(Parcel in) {
        id = in.readLong();
        trackName = in.readString();
        artistName = in.readString();
        trackDuration = in.readString();
        isAddedIntoFav = in.readByte() != 0;
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    public boolean isAddedIntoFav() {
        return isAddedIntoFav;
    }

    public void setAddedIntoFav(boolean addedIntoFav) {
        isAddedIntoFav = addedIntoFav;
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
        parcel.writeLong(id);
        parcel.writeString(trackName);
        parcel.writeString(artistName);
        parcel.writeString(trackDuration);
        parcel.writeByte((byte) (isAddedIntoFav ? 1 : 0));
    }
}
