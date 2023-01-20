package com.example.step10;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.step10.ui.main.SectionsPagerAdapter;
import com.example.step10.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //view binding을 애용해서 화면 구성하기
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //ViewPage에 연결할 Adapter 객체 생성
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        //ViewPager의 참조값을 얻어와서
        ViewPager viewPager = binding.viewPager;
        //Adapter연결하기
        viewPager.setAdapter(sectionsPagerAdapter);
        
        //상단 tab의 참조값을 얻어와서
        TabLayout tabs = binding.tabs;
        //viewPage와 연동이 되도록 연결하기
        tabs.setupWithViewPager(viewPager);
        //떠있는 Action Button의 참ㅁ조값을 얻어와서
        FloatingActionButton fab = binding.fab;
        //해당 버튼을 눌렀는지 검사할 리스너 등록
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //하단에서 잠시 올라왔다가 사라지는 Snakebar띄우기
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}