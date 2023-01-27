package com.example.step17httprequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
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
            new RequestTask().execute(url);

        });
    }
    //비동기 Task객체를 생성할 Class
    class RequestTask extends AsyncTask<String, Void, String>{
        StringBuilder builder;
        @Override
        protected String doInBackground(String... strings) {
            //strings의 0번방에 요청 url이 들어있다.
            try {
                //요청 url을 생성자의 인자로 전달해서 URL객체를 생성한다.
                URL url = new URL(strings[0]);
                //URLConnention객체를 원래 type (자식type)으로 캐스팅해서 받는다.
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //정상적으로 되었다면 conn!=null
                if(conn!=null){
                    conn.setConnectTimeout(20000);//응답을 기다리는 최대 대기 시간
                    conn.setRequestMethod("GET");//요청 메소드 설정. 생략하면 Default인 GET으로 설정됨. (GET이냐, POST냐)
                    conn.setUseCaches(false);//캐쉬 사용 여부 ㄴ
                    //응답 코드를 읽어온다.
                    int responseCode = conn.getResponseCode();
                    if(responseCode == 200){ //HttpURLConnection.HTTP_OK = 200 //상수값으로도 정의되어있다.
                        //정상 응답이면 responseCode는 200이 들어있다. (잘못된 응답이면 404) (redirect응답은 301, 302..등 300번대 응답)(Error은 500번)
                        //문자열을 읽어들일 수 있는 객체의 참조값 얻어오기
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        //문자열을 누적시킬 객체
                        builder = new StringBuilder();
                        //반복문을 돌면서
                        while(true){
                            //문자열을 한줄씩 읽어들인다.
                            String line = br.readLine();
                            if(line==null) break;
                            //StringBuilder객체에 읽어들인 문자열을 누적시킨다.
                            builder.append(line); //builder.toString();하면 누적된 문자열을 한번에 읽을 수 있다.
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("RequestTask 클래스", e.getMessage());
            }
            //StringBuilder객체에 누적된 문자열을 String type으로 한 번에 얻어내서 리턴해준다.
            return builder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            //doInBackground에서 builder를 사용해 누적시킨 문자열이 인자로 들어온다.
            //그리고 이 누적시킨 문자열은 서버가 응답한 문자열이다.
            super.onPostExecute(s);
            editText.setText(s);
        }
    }
}