package com.example.Dive.viewmodel;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.Dive.model.Track;
import com.example.Dive.room.DatabaseRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private DatabaseRepository databaseRepository;
    private LiveData<List<Track>> favTracks;
    private Track track;
    private Drawable btnPlay;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        databaseRepository = new DatabaseRepository(application);
        favTracks = databaseRepository.getFavTracks();
    }

    public Drawable getBtnPlay() {
        return btnPlay;
    }

    public void setBtnPlay(Drawable btnPlay) {
        this.btnPlay = btnPlay;
    }

    public void insertTrack(Track track){
        databaseRepository.insertTrack(track);
    }

    public void updateTrack(Track track){
        databaseRepository.updateTrack(track);
    }

    public void deleteTrack(Track track){
        databaseRepository.deleteTrack(track);
    }

    public void deleteAllTracks(){
        databaseRepository.deleteAllTracks();
    }

    public LiveData<List<Track>> getFavTracks(){
        return favTracks;
    }

    public Track getTrack(long id){
        return databaseRepository.getTrack(id);
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }
}
