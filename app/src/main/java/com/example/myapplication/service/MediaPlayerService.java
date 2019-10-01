package com.example.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.annotation.Nullable;

public class MediaPlayerService extends Service implements
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

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

    /*private void getFirstTrack(final int id, final ArrayList<Track> array, final Context context) {
        if(!array.isEmpty()){
            try {
                play.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                playBottom.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                Track track;
                // Retrieve id from somewhere
                track = array.get(id);
                long l = track.getId();
                songName.setText(track.getTrackName());
                songName_bottom.setText(track.getTrackName());
                artistName_bottom.setText(track.getArtistName());
                trackDuration_bottom.setText(track.getTrackDuration());
                // Get track info

                editor = sharedPreferences.edit();
                editor.putString("trackName", track.getTrackName());
                editor.putString("trackArtist", track.getArtistName());
                editor.putString("trackDuration", track.getTrackDuration());
                editor.apply();
                // Return to idle state
                player.reset();
                // Here the player object we earlier created is initialized once we call setDataSource.
                player.setDataSource(context, ContentUris.withAppendedId
                        (android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, l));
                player.prepare();
                start();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(), "Add music to begin", Toast.LENGTH_SHORT).show();
        }
    }*/
}
