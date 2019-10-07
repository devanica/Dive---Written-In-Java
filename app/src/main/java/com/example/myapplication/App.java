package com.example.myapplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String NOTIF_CHANNEL_ID = "mediaPlayerServiceNotif";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotifChannel();
    }

    private void createNotifChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceNotifChannel = new NotificationChannel
                            (NOTIF_CHANNEL_ID, "Service Channel",
                            NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceNotifChannel);
        }
    }
}
