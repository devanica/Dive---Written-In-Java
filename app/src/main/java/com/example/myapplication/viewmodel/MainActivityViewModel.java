package com.example.myapplication.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.myapplication.model.Track;
import com.example.myapplication.room.DatabaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
