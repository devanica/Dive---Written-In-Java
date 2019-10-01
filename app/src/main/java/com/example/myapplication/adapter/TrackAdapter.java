package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myapplication.R;
import com.example.myapplication.model.Track;
import java.util.ArrayList;

public class TrackAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Track> listOfTracks;

    public TrackAdapter(Context mContext, ArrayList<Track> listOfTracks) {
        this.mContext = mContext;
        this.listOfTracks = listOfTracks;
    }

    @Override
    public int getCount() {
        return listOfTracks.size();
    }

    @Override
    public Object getItem(int i) {
        return listOfTracks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final TrackViewHolder trackViewHolder;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.track, viewGroup, false);
            trackViewHolder = new TrackViewHolder(view);
            view.setTag(trackViewHolder);
        } else {
            trackViewHolder = (TrackViewHolder) view.getTag();
        }

        final Track track = (Track) getItem(i);
        trackViewHolder.track_name.setText(track.getTrackName());
        trackViewHolder.artist_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        trackViewHolder.artist_name.setText(track.getArtistName());
        trackViewHolder.favourites_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //showPopup(view);
            }
        });
        return view;
    }

    // Send an Intent with an action named "custom-event-name". The Intent sent should
    // be received by the ReceiverActivity.
    private void sendMessage() {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("track");
        // You can also include some extra data.
        intent.putExtra("message", "This is my message!");
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    /*private void showPopup(View view){
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.actions, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.delete_thisTrack:
                        Intent deleteIntent = new Intent(mContext, DeleteTrack.class);
                        mContext.startActivity(deleteIntent);
                        return true;
                    case R.id.add_toFavourites:
                        Intent insertIntent = new Intent(mContext, InsertTrack.class);
                        mContext.startActivity(insertIntent);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }*/
}
