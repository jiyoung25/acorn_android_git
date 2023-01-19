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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //gameView객체를 생성해서
        GameView view = new GameView(this);

        //MainActivity의 화면을 GameView로 모두 채운다.
        setContentView(view);
        //보통 setContentView에는 R.layout.activity_main이 들어있지만, 내가 만든 custom view를 화면에 꽉 채울 수도 있다.
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
                
                break;
            case R.id.on: //on버튼을 눌렀을 때 해야할 동작

                break;
        }
        
        return super.onOptionsItemSelected(item);
    }
}