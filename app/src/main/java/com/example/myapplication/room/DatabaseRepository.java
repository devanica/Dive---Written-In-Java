package com.example.myapplication.room;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.myapplication.events.TrackEvent;
import com.example.myapplication.model.Track;
import org.greenrobot.eventbus.EventBus;
import java.util.List;
import java.util.concurrent.Executors;

public class DatabaseRepository {

    private Dao dao;
    private LiveData<List<Track>> favTracks;

    public DatabaseRepository(Application application){
        Database database = Database.getInstance(application);
        dao = database.dao();
        favTracks = dao.getFavTracks();
    }

    public LiveData<List<Track>> getfavTracks(){
        return favTracks;
    }
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
