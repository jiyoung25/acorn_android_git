package com.example.step12broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AirModeReceiver amr = new AirModeReceiver();
        // Intent에 .을 찍으면 ACTION_에 battery low라던지..하는 android의 액션이 많다.
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        //방송수신자 객체를 등록하기
        registerReceiver(amr, filter);
    }
}