package com.example.Dive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import static com.example.Dive.service.MediaPlayerService.player;

public class NotificationReceiver extends BroadcastReceiver {

    Intent closeIntent = new Intent("close_app");

    @Override
    public void onReceive(Context context, Intent intent) {
        player.stop();
        LocalBroadcastManager.getInstance(context).sendBroadcast(closeIntent);
    }
}
