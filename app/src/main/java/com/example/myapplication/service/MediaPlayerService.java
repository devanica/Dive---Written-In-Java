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

    public static MediaPlayer player;
    private Track track;
    public Messenger mMessenger = new Messenger(new MessageHandler(this));
    public static final String KEY_MESSAGE = "KEY_MESSAGE";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    public static class MessageHandler extends Handler {

        WeakReference<Context> contextReference;

        public MessageHandler(Context context) {
            contextReference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            if (contextReference.get() != null) {
                //Toast.makeText(contextReference.get(), msg.getData().getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                Track track = msg.getData().getParcelable("track");
                assert track != null;
                Toast.makeText(contextReference.get(), String.valueOf(track.getTrackName()), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("track"));
    }

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);
            Toast.makeText(getApplicationContext(), "mmm", Toast.LENGTH_SHORT).show();

            getFirstTrack(track.getId(), getApplicationContext());
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
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    private void getFirstTrack(long l, Context context) {
            try {
                // Retrieve id from somewhere
                l = track.getId();
                // Return to idle state
                player.reset();
                // Here the player object we earlier created is initialized once we call setDataSource.
                player.setDataSource(context, ContentUris.withAppendedId
                        (android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, l));
                player.prepare();

            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
    }

}
