package com.example.step16asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //전송버튼
        Button sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(v->{
            /*
                시간이 오래 걸리거나 혹은 실행 시간이 불확실한 작업은
                Main Thread(UI thread)에서 하면 안된다.
             */
            SendTask task = new SendTask();

            //execute()메소드를 호출해서 작업을 시작한다.
            //string은 여러개 전달할 수 있다.
            //여기서 담은 string배열은 doInBackground의 인자로 전달된다.
            task.execute("hello","...","dddd","bye!");
        });
    }

    /*
        비동기 작업을 도와줄 클래스 설계하기
        1. AsyncTask 추상 클래스를 상속받는다.
        2. AsyncTask<파라미터type, 진행중 type, 결과 type>
          에 맞게끔 Generic class를 잘 정의한다.
        3. doInBackground()메소드를 오버라이드한다.
        4. 추가로 필요한 메소드가 있으면 추가로 오버라이드한다.
     */
    public class SendTask extends AsyncTask<String, Void, Void>{
        //백그라운드에서 작업할 내용을 여기에서 해준다.(새로운 Thread에서 할 작업)
        @Override
        protected Void doInBackground(String... strings) { //String...은  String[]으로 간주해서 사용하면 된다.
            //여기는 UI Thread가 아니다!! 즉 UI를 업데이트할 수 없는 공간이다.
            for(int i=0; i<strings.length; i++){
                Messenger.sendMessage(strings[i]);
            }
            //작업의 결과가 있다면 return해주면 되고
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            //여기는 UI Thread이기때문에 UI에 관련된 작업을 마음대로 할 수 있다.
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("작업 성공")
                    .create()
                    .show();
        }
    }
}