package com.example.step04activitymove;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class CanadaActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canada);

        findViewById(R.id.finishBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //이 액티비티를 종료시키기
        finish();
        //종료 버튼을 누르면 CanadaActivity가 닫히고 MainActivity가 재시작된다.
    }
}