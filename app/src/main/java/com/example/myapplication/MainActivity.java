package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.myapplication.model.Track;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity  implements
        Filterable {

    private ArrayList<Track> trackList;
    private ArrayList<Track> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                filteredList = (ArrayList<Track>)results.values;
                // Send this to new array and into new activity. It's the only way.
                /*Intent iresults = new Intent(MainActivity.this, FilteredResults.class);
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

}
