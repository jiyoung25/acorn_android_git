package com.example.step08viewbinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.step08viewbinding.databinding.ActivityMainBinding;

/*
    view binding 사용하는 방법
    
    1. build.gradle파일에 설정(아래의 문자열을 추가)
    buildFeatures{
        viewBinding = true
    }

    2. build.gradle 파일의 우상단에 sync now 버튼을 눌러서 실제로 반영되도록 한다.

    3. layout.xml 문서의 이름을 확인해서 자동으로 만들어진 클래스명을 예측한다.
    
    예를 들어 activity_main.xml 문서이면 ActivityMainBinding클래스
    예를 들어 activity_sub.xml 문서이면 ActivitySubBinding클래스
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editText;
    Button button;
    TextView textView;
    //ActivityMainBinding객체의 참조값을 담을 필드
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // binding을 사용할 때에는 SetContentView를 없애고
        // 아래에 bindibg객체의 참조값을 얻어온 후 setContentView(binding.getRoot()); 해주기
        // setContentView(R.layout.activity_main);
        
        //activity_main.xml문서에 전개된 EditText, Button, TextView의 참조값을 얻어오는 방법1
        //이 방법은 느리다고 다른 방법 개발해 냄. 그렇다고 이걸 안쓰는게 아니다!
        //editText = findViewById(R.id.editText);
        //button = findViewById(R.id.button);
        //textView = findViewById(R.id.textView);

        //activity_main.xml문서에 전개된 EditText, Button, TextView의 참조값을 얻어오는 방법2
        //ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        //editText = binding.editText;
        //button = binding.button;
        //textView = binding.textView;

        /*
            예를 들어 EditText에 문자열을 입력하고 버튼을 누르면 입력한 문자열이
            TextView로 이동하도록 Programming한다면 아래와 같은 코딩이 된다.
         */
        //바인딩 객체의 참조값을 얻어와서 필드에 저장
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        //바인딩 객체가 리턴해주는 View를 이용해서 화면을 구성하기
        setContentView(binding.getRoot());

        //버튼에 리스너를 등록하기
        binding.button.setOnClickListener(this);
        //바인딩을 통해 layout 객체에 접근하면 findViewById보다 빠르다!
    }

    @Override
    public void onClick(View v) {
        //EditText객체에 입력한 문자열 읽어오기
        String msg = binding.editText.getText().toString();
        //TextView에 출력하기
        binding.textView.setText(msg);
    }
}