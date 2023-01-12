package com.example.step03view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //필드
    private EditText inputMsg;
    private Button sendBtn;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // res/layout/activity_main.xml문서를 전개해서 화면 구성하기
        setContentView(R.layout.activity_main);
        /*
        * 위의 xml문서를 전개하면
        * ConstraintLayout, EditText, Button객체가 생성된다.
        * 만일 java code에서 해당 UI를 가지고 어떠한 동작을 하려면 그 객체의 참조값이 필요하다!
        * 객체의 참조값을 얻어오는 연습을 해야 한다.
        */
        //참조값은 보통 onCreate 시점에 얻어오는 것이 기본이다.

        // id를 이용해서 EditText 객체의 참조값 얻어오기
        this.inputMsg = findViewById(R.id.inputMsg);

        // id를 이용해서 Button 객체의 참조값 얻어오기
        this.sendBtn = findViewById(R.id.sendBtn);

        textView = findViewById(R.id.textView);

        //this는 MainActivity이다.
        //OnClickListner은 interpace type이다.
        sendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //EditText에 입력한 문자열을 읽어와서 지역변수에 담기
        //String msg = inputMsg.getText().toString(); //inputMsg.getText()는 Editable type이므로 toString()까지 해주어야 읽어올 수 있다.
        //Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();

        String msg = inputMsg.getText().toString();
        textView.setText(msg);
        inputMsg.setText("");

        //만약 setText로 숫자를 보내고 싶다면
        //textView.setText(1000); 일케 하면 안됨
        //textView.setText(Integer.toString(100)); 으로 해야 함.
    }
}