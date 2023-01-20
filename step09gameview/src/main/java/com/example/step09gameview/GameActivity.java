package com.example.step09gameview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity{
    //사운드 매니저 객체
    SoundManager sManager;
    //sound의 종류별로 상수 정의하기
    public static final int SOUND_LAZER = 1;
    public static final int SOUND_SHOOT = 2;
    public static final int SOUND_BIRDDIE = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //gameView객체를 생성해서
        GameView view = new GameView(this);

        //MainActivity의 화면을 GameView로 모두 채운다.
        setContentView(view);
        //보통 setContentView에는 R.layout.activity_main이 들어있지만, 내가 만든 custom view를 화면에 꽉 채울 수도 있다.
        //SoundManager객체를 생성해서
        sManager = new SoundManager(this); //Sound Manager가 필요한 곳은 GameView이므로 GameView에서 할 작업이 더 있다.
        //GameView의 setter메소드를 이용해서 참조값을 전달해준다.
        view.setSoundManager(sManager);
    }

    @Override
    protected void onStart(){
        super.onStart();
        //효과음 미리 로딩하기
        sManager.addSound(SOUND_LAZER, R.raw.laser1);
        sManager.addSound(SOUND_SHOOT, R.raw.shoot1);
        sManager.addSound(SOUND_BIRDDIE, R.raw.birddie);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //옵션 메뉴를 만드는 메소드
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //메뉴 전개자 객체를 얻어와서
        MenuInflater inflater = getMenuInflater();
        //res/menu/menu_option.xml문서를 전개해서 메뉴로 구성한다.
        inflater.inflate(R.menu.menu_option,menu);
        return true;
    }

    //옵션 메뉴를 선택했을 때 호출되는 메소드
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.off: //off버튼을 눌렀을 때 해야할 동작
                sManager.setMute(true);
                break;
            case R.id.on: //on버튼을 눌렀을 때 해야할 동작
                sManager.setMute(false);
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }
}