package com.example.step09gameview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startBtn = findViewById(R.id.startBtn);
        //게임 시작 버튼을 누르면
        startBtn.setOnClickListener(view ->{
            //game 액티비티로 이동해서 게임이 시작되게 한다.
            Intent i = new Intent(MainActivity.this, GameActivity.class);
            startActivity(i);
        });

        //소리를 재생할 준비를 한다,
        SoundManager sm = new SoundManager(this);
        sm.addSound(1, R.raw.birddie);
        sm.addSound(2, R.raw.laser1);
        sm.addSound(3, R.raw.shoot1);

        Button playBtn = findViewById(R.id.playBtn);
        playBtn.setOnClickListener(view->{
            sm.playSound(3);
        });
    }



}