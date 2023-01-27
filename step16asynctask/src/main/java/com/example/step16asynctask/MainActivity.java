package com.example.step16asynctask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
    UI에 관련된 작업이 가능한 Thread는 오직 Main Thread(UI Thread)에서만 가능하다.
 */
public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
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

        editText = findViewById(R.id.editText);
        Button testBtn = findViewById(R.id.testBtn);
        testBtn.setOnClickListener(v -> {
            //새로운 Thread에서 어떤 작업을 하고 작업이 끝나면 그 Thread 안에서 EditText에 문자열을 출력하려고 한다. 가능할까 ?
            //참고로 맨 위에 써있듯 UI의 작업은 MainThread에서만 가능하다.
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //3초가 소요되는 어떤 작업이라고 가정
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //EditText에 문자열을 출력해보자.
                    //editText.setText("작업이 종료되었습니다.");
                    //이렇게 하면 오류가 난다. Why? MainThread에서만 UI의 작업이 가능하니까~~ UI는 업데이트 할 수 없다.
                    //UI를 조작하고 싶다면, Handler객체에서 Message를 보내서 UI를 업데이트 하도록 하면 된다.
                    handler.sendEmptyMessage(0);
                }
            }).start();
        });
        //TextView의 참조값을 field에 저장하기
        textView = findViewById(R.id.textView);
        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(v -> {
            new CountTask().execute("김구라", "해골", "원숭이");
        });
    }
    class CountTask extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... strings) {
            //strings는 String[]이다. 전달된 파라미터는 배열에 순서대로 저장되어있다.
            String p = null;
            for(String tmp:strings){
                p = tmp;
            }

            int count = 0;
            //반복문을 10번 돌면서 count를 센다.
            for(int i=0; i<10; i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count++; //카운트를 증가시키고
                //카운트 값을 발행한다.
                publishProgress(count); //이 값은 아래 override한 onProgressUpdate메소드에 전달된다.
            }
            //작업의 결과라고 가정하자.
            String result = "Success";
            //리턴해주면 onPostExecute()메소드가 호출되면서 리턴된 값이 전달된다.
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //values는 Integer[]이다. 0번방에 카운트된 값이 들어있다.
            //여기는 UI Thread이기 때문에 UI업데이트가 가능하다.
            //textView.setText(1); -> 이렇게하면 resource ID가 나온다. (ex. R.id.sendBtn)
            //textView.setText(values[0].toString());
            textView.setText(Integer.toString(values[0])); //이렇게도 가능하다.
        }

        @Override
        protected void onPostExecute(String s) {
            // 여기는 UI 스레이이기 때문에 UI 업데이트 가능
            textView.setText(s);
        }
    }


    //필드에 Handler객체를 생성해서 참조값을 넣어둔다. 단, handleMessage()메소드를 재정의한다. (아래는 익명class이용)
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //여기는 Main Thread에 메시지를 보낼 수 있는 공간이다.
            //그러므로 UI update가 가능하다.
            editText.setText("작업이 종료되었습니다.");
        }
    };



    /*
        비동기 작업을 도와줄 클래스 설계하기
        1. AsyncTask 추상 클래스를 상속받는다.
        2. AsyncTask<파라미터type, 진행중 type, 결과 type>에 맞게끔 Generic class를 잘 정의한다.
          - 파라미터 type: onCreate에서 task.execute(" ", " ", " " )로 넣는 파라미터값
                      이 예제에서는 String들을 parameter로 사용했기 때문에 AsncTask의 첫번째 Generic type은 String인 것이다.
          - 진행중 type: 작업하는 중간중간 보고를 해야할 수도 있다.
                      publishProgress (진행중인 것을 발행함) void라는 것은 발행하지 않겠다는 뜻.
                      진행중인 중간보고(?)를 숫자로 알리겠다 하면 int로 설정하고 숫자값을 발행할 수 있음.
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
                //onProgressUpdate(10); ---> AsncTask의 2번째(진행중 type)을 Integer로 바꾸면 사용하기가 가능해지는 인자
            }
            //작업의 결과가 있다면 return해주면 되고
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //여기도 역시 UI스레드이다.
        }

        //doInBackground()메소드가 리턴하면 자동으로 호출되는 메소드
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