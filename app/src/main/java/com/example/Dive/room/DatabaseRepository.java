package com.example.Dive.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.Dive.events.TrackEvent;
import com.example.Dive.model.Track;
import org.greenrobot.eventbus.EventBus;
import java.util.List;
import java.util.concurrent.Executors;

public class DatabaseRepository {

    private Dao dao;
    private LiveData<List<Track>> favTracks;
    private Track track;

    public DatabaseRepository(Application application){
        Database database = Database.getInstance(application);
        dao = database.dao();
        favTracks = dao.getFavTracks();
    }

    public LiveData<List<Track>> getFavTracks(){
        return favTracks;
    }

    public Track getTrack(long id){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                track = dao.getTrack(id);
            }
        });
        return track;
    }

    //thread issue when adding tracks too fast it goes on the same thread
    public void deleteAllTracks(){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteFavTracks();
            }
        });
    }

    public void deleteTrack(Track track) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteTrack(track);
            }
        });
    }

    public void insertTrack(Track track) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                dao.insertTrack(track);
                EventBus.getDefault().post(new TrackEvent(track));
            }
        });
    }

    public void updateTrack(Track track) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                dao.updateTrack(track);
                EventBus.getDefault().post(new TrackEvent(track));
            }
        });
    }
}
