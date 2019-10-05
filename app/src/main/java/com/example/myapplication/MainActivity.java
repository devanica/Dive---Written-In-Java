package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.viewmodel.MainActivityViewModel;
import com.example.myapplication.adapter.FavTrackAdapter;
import com.example.myapplication.adapter.RecentTrackAdapter;
import com.example.myapplication.adapter.TrackAdapter;
import com.example.myapplication.model.Track;
import com.example.myapplication.room.DatabaseRepository;
import com.example.myapplication.service.MediaPlayerService;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.myapplication.App.NOTIF_CHANNEL_ID;

public class MainActivity extends AppCompatActivity implements Filterable {

    private ArrayList<Track> favoriteList = new ArrayList<>();
    private ArrayList<Track> recentList = new ArrayList<>();
    private ArrayList<Track> trackList = new ArrayList<>();
    private ArrayList<Track> filteredList = new ArrayList<>();

    private RecentTrackAdapter recentTrackAdapter;
    private TrackAdapter trackAdapter;
    private FavTrackAdapter favTrackAdapter;

    private DatabaseRepository databaseRepository;
    private MainActivityViewModel mainActivityViewModel;
    private RecyclerView trackRecycler, favoriteRecycler, recentRecycler;
    private TextView favTitle, recentTitle;

    private ImageView btnPlay, btnNext, btnPrev;
    private Connection connection;
    public boolean isBound = false;
    public static int currPosition, nextPosition, prevPosition;
    private Intent selectIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection = new Connection(this);
        //selectIntent = new Intent("sent_track");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(onCloseApp, new IntentFilter("close_app"));

        recentRecycler = findViewById(R.id.recycler_recent);
        favoriteRecycler = findViewById(R.id.recycler_favorite);
        trackRecycler = findViewById(R.id.recycler_tracks);

        favTitle = findViewById(R.id.title_favorite);
        recentTitle = findViewById(R.id.title_recent);

        btnPlay = findViewById(R.id.btn_play);
        btnNext = findViewById(R.id.btn_next);
        btnPrev = findViewById(R.id.btn_prev);

        // Adapter for recentList.
        recentRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1,
                                        GridLayoutManager.HORIZONTAL, false));
        recentTrackAdapter = new RecentTrackAdapter(recentList, getApplicationContext());
        recentRecycler.setAdapter(recentTrackAdapter);
        checkIfRecentEmpty();
        // Adapter for favoriteList.
        favoriteRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1,
                                        GridLayoutManager.HORIZONTAL, false));
        favTrackAdapter = new FavTrackAdapter();
        favoriteRecycler.setAdapter(favTrackAdapter);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getAllTracks().observe(this, tracks -> {
            // TODO: Update recyclerview
            favTrackAdapter.setFavTracks(tracks);
            checkIfFavEmpty();
            Toast.makeText(getApplicationContext(), "OnChanged", Toast.LENGTH_SHORT).show();
        });

        // Adapter for trackList/filteredList.
        getTracksFromStorage();
        trackRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        trackAdapter = new TrackAdapter(getApplicationContext(), trackList);
        trackRecycler.setAdapter(trackAdapter);
        trackAdapter.setOnTrackSelectListener(new TrackAdapter.onTrackSelectListener() {
            @Override
            public void onTrackSelect(View view, int position, Track track) {
                // Send an Intent with an action named "track-name". The Intent sent should
                // be received by the MediaPlayerService class.
                // Create intent with action
                /*currPosition = position;
                selectIntent.putExtra("track", track);
                Log.v("track_sent","track sent");*/
                // Send local broadcast
                //LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(selectIntent);
                startForegroundService(track);
            }

            @Override
            public void addToFavorites(View view, int position, Track track) {
                if(!track.getIfAddedIntoFav()){
                    track.addIntofav(true);
                    mainActivityViewModel.insertTrack(track);
                    trackAdapter.notifyItemChanged(position);
                }else {
                    track.addIntofav(false);
                    mainActivityViewModel.deleteTrack(track);
                    trackAdapter.notifyItemChanged(position);
                }
            }
        });

        bindService(new Intent(this, MediaPlayerService.class), connection, BIND_AUTO_CREATE);

        btnPlay.setOnClickListener(view -> {

        });

        btnNext.setOnClickListener(view -> {
            nextPosition = currPosition++;
            if (nextPosition < trackList.size()-1){
                // Send local broadcast
                selectIntent.putExtra("track", trackList.get(nextPosition));
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(selectIntent);
                btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
            }else {
                nextPosition = 0;
                selectIntent.putExtra("track", trackList.get(nextPosition));
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(selectIntent);
                btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
            }
        });

        btnPrev.setOnClickListener(view -> {
            prevPosition = currPosition--;
            if (prevPosition > 0){
                selectIntent.putExtra("track", trackList.get(prevPosition));
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(selectIntent);
                btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
            }else {
                prevPosition = 0;
                selectIntent.putExtra("track", trackList.get(prevPosition));
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(selectIntent);
                btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
            }
        });
    }

    private void checkIfRecentEmpty(){
        if(recentList.isEmpty()){
            recentRecycler.setVisibility(View.GONE);
        }else {
            recentRecycler.setVisibility(View.VISIBLE);
        }
    }

    private void checkIfFavEmpty(){
        if(Objects.requireNonNull(mainActivityViewModel.getAllTracks().getValue()).isEmpty()){
            favoriteRecycler.setVisibility(View.GONE);
        }else {
            favoriteRecycler.setVisibility(View.VISIBLE);
        }
    }

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "sent_track" is broadcasted.
    private BroadcastReceiver onCloseApp = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stopForegroundService();
        }
    };

    private void startForegroundService(Track track){
        Intent serviceIntent = new Intent(this, MediaPlayerService.class);
        serviceIntent.putExtra("track", track);
        startService(serviceIntent);
    }

    private void stopForegroundService(){
        Intent serviceIntent = new Intent(this, MediaPlayerService.class);
        stopService(serviceIntent);
    }

    public static class Connection implements ServiceConnection {
        //IN ORDER TO SEND DATA BETWEEN SERVICE AND ACTIVITIES SERVICE NEEDS TO BE CONNECTED TO THE ACTIVITY
        //AND THIS IS HOW IT'S DONE.
        Context contextReference;

        public Connection(Context contextReference) {
            this.contextReference = contextReference;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (contextReference != null) {
                ((MainActivity) contextReference).isBound = true;
                Toast.makeText(contextReference, "Service Connected", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (contextReference != null) {
                ((MainActivity) contextReference).isBound = false;
                Toast.makeText(contextReference, "Service Disconnected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Filter for search interface
    // TODO: moguce resenje, ima boljih...
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    results.count = trackList.size();
                    results.values = trackList;
                } else {
                    // Do the search
                    filteredList = new ArrayList<Track>();
                    String searchedTrack = constraint.toString().toUpperCase();
                    for (Track s : trackList)
                        if (s.getTrackName().toUpperCase().contains(searchedTrack) ||
                                s.getArtistName().toUpperCase().contains(searchedTrack)) filteredList.add(s);
                    results.count = filteredList.size();
                    results.values = filteredList;
                }
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                /*filteredList = (ArrayList<Track>)results.values;
                // Send this to new array and into new activity. It's the only way.
                Intent iresults = new Intent(MainActivity.this, FilteredResults.class);
                iresults.putExtra("results", filteredList);
                startActivity(iresults);*/
                // TODO: On text changed, primeni u edittext listeneru;
            }
        };
    }

    // Get tracks from storage
    private void getTracksFromStorage(){
        trackList = new ArrayList<Track>();
        ContentResolver contentResolver = Objects.requireNonNull(getApplicationContext()).getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Audio.Media.IS_MUSIC, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        String trackName, artistName, trackDuration;
        int trackId;

        if(cursor!=null && cursor.moveToFirst()){
            do{
                trackId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                trackName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                trackDuration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

                String duration = trackDuration;
                int converted = Integer.parseInt(duration);
                int mns = (converted / 60000) % 60000;
                int scs = converted % 60000 / 1000;

                @SuppressLint("DefaultLocale") String songDuration = String.format("%02d:%02d",  mns, scs);

                trackList.add(new Track(trackId, trackName, artistName, songDuration));
            }while (cursor.moveToNext());
            cursor.close();
        }else {
            Toast.makeText(getApplicationContext(), "You've got 0 tracks", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteTrack(int id){
        Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
        getApplicationContext().getContentResolver().delete(uri, null, null);
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

}
