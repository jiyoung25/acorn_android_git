package com.example.step23mp3player;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {
    //서비스가 최초 활성화될 때 한 번 호출되는 메소드

    @Override
    public void onCreate() {
        super.onCreate();
    }

    //최초 활성화 혹은 이미 활성화된 이후 이 서비스를 활성화하는 Intent가 도착하면 호출ㄹ되는 method
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getAction()){
            case AppConstants.ACTION_PLAY:
                Log.d("onStartCommand()", "play!");
                break;
            case AppConstants.ACTION_PAUSE:
                Log.d("onStartCommand()", "pause!");
                break;
            case AppConstants.ACTION_STOP:
                Log.d("onStartCommand()", "stop!");
                break;
        }
        return START_NOT_STICKY;
    }

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}