package com.example.myapplication.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myapplication.MainActivity;
import com.example.myapplication.NotificationReceiver;
import com.example.myapplication.R;
import com.example.myapplication.model.Track;
import java.io.IOException;

import static com.example.myapplication.App.NOTIF_CHANNEL_ID;

public class MediaPlayerService extends Service implements
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener {

    private static MediaPlayer player;
    private Track track;

    NotificationManagerCompat notificationManagerCompat;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        notificationManagerCompat = NotificationManagerCompat.from(this);
        // Register to receive messages.
        // We are registering an observer (onTrackSelect) to receive Intents
        // with actions named "sent_track".
        player = new MediaPlayer();
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(onTrackSelect, new IntentFilter("sent_track"));
        Log.v("track_registered","broadcast registered");
    }

    private void triggerNotificationChannelOne(){
        // here find strings from track info to be set in notification instead of title and content

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("track", track);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, NOTIF_CHANNEL_ID)
                .setContentTitle("Dive")
                .setContentText(track.getTrackName())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.drawable.ic_launcher_foreground, "something", actionIntent)
                .build();
        notificationManagerCompat.notify(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        track = intent.getParcelableExtra("track");
        Intent notifIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, NOTIF_CHANNEL_ID)
                .setContentTitle("Dive")
                .setContentText(track.getTrackName())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        return START_STICKY;
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
        triggerNotificationChannelOne();
        Log.v("player","start");
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
        if(player!=null){
            try {
                // Return to idle state
                player.reset();
                Log.v("player","reset");
                // Here the player object we earlier created is initialized once we call setDataSource.
                player.setDataSource(context, ContentUris.withAppendedId
                        (android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, l));
                Log.v("player","setdatasource");
                player.prepare();
                Log.v("player","prepare");
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
