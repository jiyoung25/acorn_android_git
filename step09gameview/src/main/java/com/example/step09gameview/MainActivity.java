package com.example.step09gameview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //gameView객체를 생성해서
        GameView view = new GameView(this);

        //MainActivity의 화면을 GameView로 모두 채운다.
        setContentView(view);
        //보통 setContentView에는 R.layout.activity_main이 들어있지만, 내가 만든 custom view를 화면에 꽉 채울 수도 있다.
    }



}