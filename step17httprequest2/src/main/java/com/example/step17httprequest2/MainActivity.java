package com.example.step17httprequest2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements Util.RequestListener {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        //요청 버튼을 클릭했을 때 동작할 준비
        EditText inputUrl = findViewById(R.id.inputUrl);
        Button requestBtn = findViewById(R.id.requestBtn);

        requestBtn.setOnClickListener(v -> {
            //입력한 요청 url을 읽어온다.
            String url = inputUrl.getText().toString();
            //http요청은 시간이 오래 걸릴 수 있는 불확실한 작업이다. -> 비동기 task에서 작업을 해야 한다.
            //Util Class의 static메소드를 활용해서 GET방식을 요청하고 결과를 받아오기.
            Util.sendGetRequest(999, url, null, this);
        });
    }

    //onCreate에서 Util.sendGetRequest로 전달한 requestId가 여기 들어온다. (결과로 나온 result인자도 들어옴)
    @Override
    public void onSuccess(int requestId, Map<String, Object> result) {
        if(requestId == 999){
            //Map에 data라는 키값으로 담긴 String type을 읽어온다.
            String data = (String)result.get("data");
            editText.setText("request2"+data);
        }
    }

    @Override
    public void onFail(int requestId, Map<String, Object> result) {
        //에러메시지를 읽어와서 EditText에 출력하기
        String data = (String) result.get("data");
        editText.setText(data);
    }
}