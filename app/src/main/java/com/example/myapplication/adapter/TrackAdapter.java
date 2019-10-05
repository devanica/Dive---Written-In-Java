package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.model.Track;
import com.example.myapplication.room.DatabaseRepository;
import com.example.myapplication.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackHolder> {

    private Context mContext;
    private DatabaseRepository databaseRepository;
    private ArrayList<Track> listOfTracks;
    private onTrackSelectListener onTrackSelectListener;

    public TrackAdapter(Context mContext, ArrayList<Track> listOfTracks) {
        this.mContext = mContext;
        this.listOfTracks = listOfTracks;
    }

    public interface onTrackSelectListener{
        void onTrackSelect(View view, int position, Track track);
        void addToFavorites(View view, int position, Track track);
    }

    public void setOnTrackSelectListener(onTrackSelectListener onTrackSelectListener) {
        this.onTrackSelectListener = onTrackSelectListener;
    }

    @NonNull
    @Override
    public TrackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.track, parent, false);
        return new TrackHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackHolder holder, int position) {
        Track track = listOfTracks.get(position);

        holder.trackName.setText(track.getTrackName());
        holder.artistName.setText(track.getArtistName());


        if(track.isAddedIntoFav()){
            // set one icon
            holder.addTofavButton.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_remove));
        }else {
            // set another icon
            holder.addTofavButton.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_add));
        }
    }

    @Override
    public int getItemCount() {
        return listOfTracks.size();
    }

    class TrackHolder extends RecyclerView.ViewHolder {

        TextView trackName, artistName;
        LinearLayout trackContainer;
        ImageView addTofavButton;

        public TrackHolder(@NonNull View itemView) {
            super(itemView);
            trackName = itemView.findViewById(R.id.track_name);
            artistName = itemView.findViewById(R.id.artist_name);
            trackContainer = itemView.findViewById(R.id.track_container);
            addTofavButton = itemView.findViewById(R.id.btn_addtofav);

            selectTrack();
            addToFavorites();
        }

        private void selectTrack(){
            trackContainer.setOnClickListener(view -> {
                if(onTrackSelectListener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                    onTrackSelectListener.onTrackSelect(itemView, getAdapterPosition(), listOfTracks.get(getAdapterPosition()));
                }
            });
        }

        private void addToFavorites(){
            addTofavButton.setOnClickListener(view -> {
                if(onTrackSelectListener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                    onTrackSelectListener.addToFavorites(itemView, getAdapterPosition(), listOfTracks.get(getAdapterPosition()));
                }
            });
        }
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
