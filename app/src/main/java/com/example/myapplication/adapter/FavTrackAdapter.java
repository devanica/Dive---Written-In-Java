package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.model.Track;
import java.util.ArrayList;
import java.util.List;

public class FavTrackAdapter extends RecyclerView.Adapter<FavTrackAdapter.FavTrackHolder> {

    private Context context;
    private List<Track> tracks = new ArrayList<>();
    private OnFavTrackSelectListener onFavTrackSelectListener;

    public interface OnFavTrackSelectListener{
        void deleteTrack(View view, int position, Track track);
    }

    public void setOnTrackSelectListener(OnFavTrackSelectListener onFavTrackSelectListener) {
        this.onFavTrackSelectListener = onFavTrackSelectListener;
    }

    @NonNull
    @Override
    public FavTrackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_track, null);
        return new FavTrackHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavTrackHolder holder, int position) {
        holder.recentTrackName.setText(tracks.get(position).getTrackName());
        holder.recentArtistName.setText(tracks.get(position).getArtistName());
    }

    @Override
    public int getItemCount() {
        return this.tracks.size();
    }

    public void setFavTracks(List<Track> tracks){
        this.tracks = tracks;
        notifyDataSetChanged();
    }

    class FavTrackHolder extends RecyclerView.ViewHolder {

        TextView recentTrackName, recentArtistName;
        ImageView deleteTrack;

        public FavTrackHolder(@NonNull View itemView) {
            super(itemView);
            recentTrackName = itemView.findViewById(R.id.recent_track_name);
            recentArtistName = itemView.findViewById(R.id.recent_artist_name);
            deleteTrack = itemView.findViewById(R.id.btn_delete);

            deleteTrack();
        }

        private void deleteTrack(){
            deleteTrack.setOnClickListener(view -> {
                if(onFavTrackSelectListener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                    onFavTrackSelectListener.deleteTrack(itemView, getAdapterPosition(), tracks.get(getAdapterPosition()));
                }
            });
        }
    }

}
