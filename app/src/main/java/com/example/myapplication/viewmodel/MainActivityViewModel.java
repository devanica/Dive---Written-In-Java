package com.example.myapplication.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.myapplication.model.Track;
import com.example.myapplication.room.DatabaseRepository;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private DatabaseRepository databaseRepository;
    private LiveData<List<Track>> allTracks;
    private Track track;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        databaseRepository = new DatabaseRepository(application);
        allTracks = databaseRepository.getFavTracks();
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

    public LiveData<List<Track>> getAllTracks(){
        return allTracks;
    }

    public Track getTrack(long id){
        return databaseRepository.getTrack(id);
    }
}
