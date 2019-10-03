package com.example.myapplication.room;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.myapplication.model.Track;
import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrack(Track track);

    @Update
    void updateTrack(Track track);

    @Delete
    void deleteTrack(Track track);

    // Here we are defining the sql method ourselves by string instead of just annotating it and calling the existing method.
    @Query("DELETE FROM fav_tracks")
    void deleteFavTracks();

    // By using livedata we observe the changes in list of passes, every change in the list will automatically be presented in the ui.
    @Query("SELECT * FROM fav_tracks ORDER BY trackName DESC")
    LiveData<List<Track>> getFavTracks();

}
