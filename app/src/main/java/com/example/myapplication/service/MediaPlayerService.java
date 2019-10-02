package com.example.myapplication.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myapplication.model.Track;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class MediaPlayerService extends Service implements
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener {

    private static MediaPlayer player;
    private Track track;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Register to receive messages.
        // We are registering an observer (onTrackSelect) to receive Intents
        // with actions named "sent_track".
        //player = new MediaPlayer();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(onTrackSelect, new IntentFilter("sent_track"));
        Toast.makeText(getApplicationContext(), "mps onCreate", Toast.LENGTH_SHORT).show();
        Log.v("track_registered","broadcast registered");
    }

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "sent_track" is broadcasted.
    private BroadcastReceiver onTrackSelect = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // intent can contain any data
            track = intent.getParcelableExtra("track");
            Toast.makeText(getApplicationContext(), String.valueOf(track.getTrackName()), Toast.LENGTH_SHORT).show();
            Log.v("track_received","onReceive called, track received");
            //getFirstTrack(track.getId(), getApplicationContext());
        }
    };

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        player.start();
    }

    private void playTrack(){

    }

    private void pauseTrack(){

    }

    private void nextTrack(){

    }

    private void previousTrack(){

    }

    private void repeatTrack(){

    }

    private void repeatAll(){

    }

    @Override
    public void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(onTrackSelect);
        super.onDestroy();
    }

    public void getFirstTrack(long l, Context context) {
            try {
                // Return to idle state
                player.reset();
                // Here the player object we earlier created is initialized once we call setDataSource.
                player.setDataSource(context, ContentUris.withAppendedId
                        (android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, l));
                player.prepare();
                Log.v("track_prepared","onReceive called");
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
    }

}
