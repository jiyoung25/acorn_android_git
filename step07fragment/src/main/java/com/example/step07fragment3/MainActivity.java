package com.example.step07fragment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyFragment.MyFragmentListener {
    MyFragment mf1;
    MyFragment mf2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main2);

        //전개된 view에는 MyFragment객체가 2개 있다.
        //만일 해당 객체의 참조값이 activity에서 필요하다면?

        //FragmemtManager객체의 참조값을 얻어내서
        FragmentManager fm = getSupportFragmentManager();
        //해당 객체의 메소드를 활용해서 Fragment의 참조값을 얻어낸다.
        mf1 = (MyFragment)fm.findFragmentById(R.id.fragment1);
        mf2 = (MyFragment)fm.findFragmentById(R.id.fragment2);

        //만일 FragmentContainerView의 참조값이 필요하다면 Activity의 method를 이용해서 얻어낼 수 있다.
        FragmentContainerView containerView1 = findViewById(R.id.fragment1);
        FragmentContainerView containerView2 = findViewById(R.id.fragment2);

    }
    
    @Override
    public void sendMsg(String msg){
        //프래그먼트로 전달된 문자열을 토스트 메시지로 출력하기
        //한마디로 프래그먼트에서 호출할 예정임
        Toast.makeText(this, msg ,Toast.LENGTH_SHORT).show();
    }

    public void resetFragment(View v){
        mf1.reset();
        mf2.reset();
    }
}