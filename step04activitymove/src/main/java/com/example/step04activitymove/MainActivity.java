package com.example.step04activitymove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button toCanada;
    Button toGerman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.toCanada = findViewById(R.id.toCanada); //여기서 this는 field를 가리킨다.
        this.toGerman = findViewById(R.id.toGerman);

        toCanada.setOnClickListener(this); //여기서 this는 MainActivity class를 뜻한다.
        toGerman.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //View view에는 눌러진 Button 객체의 참조값이 들어있다.
        /*
        if(view == toCanada) {
            //Button의 부모타입은 View이므로 view와 toCanada의 참조값을 비교하는 것이 가능하다.
            //CanadaActivity를 활성화할 수 있는 의도를 가진 객체를 생성한다.
            Intent intent = new Intent(this, CanadaActivity.class);
            startActivity(intent);

        } else if(view == toGerman){
            Intent intent = new Intent(this, GermanActivity.class);
            startActivity(intent);
        }
        */
        
        //눌러진 버튼의 아이디값(자동으로 부여된 정수값)을 읽어와서 R클래스에 등록된 아이디값과 비교해서 분기하기
        switch(view.getId()){
            case R.id.toCanada:
                Intent intent = new Intent(this, CanadaActivity.class);
                startActivity(intent);
                break;

            case R.id.toGerman:
                Intent intent2 = new Intent(this, GermanActivity.class);
                startActivity(intent2);
                break;
        }
    }
}