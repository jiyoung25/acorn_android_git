package com.example.step18login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.step18login.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    //activity_main.xml 문서와 대응되는 데이터 type
    ActivityMainBinding binding;
    //session id값을 영구적으로 저장할 SharedPreferences
    SharedPreferences pref;
    //session id값을 임시로 저장할 field
    String sessionId;
    //로그인된 id값을 저장할 field
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //ActivityMainBinding 객체의 참조값을 얻어와서 field에 저장
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        //화면 구성하기
        setContentView(binding.getRoot());

        //로그인 버튼 -> loginActivity로 이동
        binding.login.setOnClickListener(v->{
            //로그인 액티비티로 이동한다.
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.logout.setOnClickListener(v->{
            //로그아웃하기 (MainActivity에서만 로그아웃 할 수 있음)
            //new LogoutTask().execute(AppConstants.BASE_URL+"/logout");

            //로그아웃 액티비티로 이동한다.
            Intent intent = new Intent(this, LogoutActivity.class);
            startActivity(intent);
        });

        binding.gallery.setOnClickListener(v->{
            Intent intent = new Intent(this, GalleryActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //SharedPreferences 객체의 참조값을 얻어와서 필드에 저장하기
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        //저장된 sessionId가 있는지 읽어와본다. (없다면 default값은 빈 문자열)
        sessionId = pref.getString("sessionId","");

        //로그인했는지 여부를 요청해보기
        new LoginCheckTask().execute(AppConstants.BASE_URL+"/logincheck");
    }

    class LogoutTask extends AsyncTask<String,Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            String resultUrl = strings[0];

            StringBuilder builder=new StringBuilder();
            HttpURLConnection conn=null;
            InputStreamReader isr=null;
            BufferedReader br=null;
            Boolean isSuccess = false;

            try {
                URL url = new URL(resultUrl);
                conn = (HttpURLConnection) url.openConnection();
                if(conn!=null){
                    conn.setConnectTimeout(20000);
                    conn.setRequestMethod("POST");
                    conn.setUseCaches(false);

                    int responseCode = conn.getResponseCode();

                    if(responseCode == 200){
                        isr = new InputStreamReader(conn.getInputStream());
                        br = new BufferedReader(isr);
                        while (true){
                            String line = br.readLine();
                            if(line == null) break;
                            builder.append(line);
                        }
                    }
                    JSONObject obj = new JSONObject(builder.toString());
                    isSuccess = obj.getBoolean("isSuccess");
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Log.e("isSuccess", isSuccess.toString());
            return isSuccess;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            binding.userInfo.setText("로그인이 필요 합니다.");
        }
    }

    //로그인 여부를 check하는 작업을 할 비동기 task
    class LoginCheckTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            //로그인 체크 url
            String requestUrl=strings[0];

            //서버가 http 요청에 대해서 응답하는 문자열을 누적할 객체
            StringBuilder builder=new StringBuilder();
            HttpURLConnection conn=null;
            InputStreamReader isr=null;
            BufferedReader br=null;
            boolean isLogin=false;

            try{
                //URL 객체 생성
                URL url=new URL(requestUrl);
                //HttpURLConnection 객체의 참조값 얻어오기
                conn=(HttpURLConnection)url.openConnection();
                if(conn!=null){//연결이 되었다면
                    conn.setConnectTimeout(20000); //응답을 기다리는 최대 대기 시간
                    conn.setRequestMethod("GET");//Default 설정
                    conn.setUseCaches(false);//케쉬 사용 여부

                    //App에 저장된 Session Id가 있다면 요청할 때 쿠키로 같이 보내기
                    if(!sessionId.equals("")) {
                        // JSESSIONID=xxx 형식의 문자열을 cookie로 보내기
                        conn.setRequestProperty("Cookie", sessionId);
                    }

                    //응답 코드를 읽어온다.
                    int responseCode=conn.getResponseCode();

                    if(responseCode==200){//정상 응답이라면...
                        //서버가 출력하는 문자열을 읽어오기 위한 객체
                        isr=new InputStreamReader(conn.getInputStream());
                        br=new BufferedReader(isr);
                        //반복문 돌면서 읽어오기
                        while(true){
                            //한줄씩 읽어들인다.
                            String line=br.readLine();
                            //더이상 읽어올 문자열이 없으면 반복문 탈출
                            if(line==null)break;
                            //읽어온 문자열 누적 시키기
                            builder.append(line);
                        }
                    }
                }

                //서버가 응답한 쿠키 목록을 읽어온다. 위치가 중요한 작업이다. try 부분의 가장 마지막에 진행해야 한다.
                List<String> cookList=conn.getHeaderFields().get("Set-Cookie");
                //만일 쿠키가 존재한다면
                if(cookList != null){
                    //반복문을 돌면서
                    for(String tmp : cookList){
                        //session id가 들어있는 쿠키를 찾아내서
                        if(tmp.contains("JSESSIONID")){
                            //session id만 추출해서
                            String sessionId=tmp.split(";")[0];
                            //SharedPreferences를 편집할 수 있는 객체를 활용해서
                            SharedPreferences.Editor editor=pref.edit();
                            //sessionId라는 키값으로 session id값을 저장한다.
                            editor.putString("sessionId", sessionId);
                            //apply()는 비동기로 저장하기 때문에 실행의 흐름이 잡혀있지 않다. (지연이 없다는 뜻)
                            //commit()은 동기작업이다.(실행이 약간 주춤)
                            editor.apply();
                            //필드에도 담아둔다.
                            MainActivity.this.sessionId=sessionId;
                        }
                    }
                }
                //출력받은 문자열 전체 얻어내기
                JSONObject obj=new JSONObject(builder.toString());
                /*
                    {"isLogin":false} or {isLogin":true, "id":"kimgura"}로 응답이 될 예정이다.
                */
                Log.d("서버가 응답한 문자열: ", builder.toString());
                //로그인 여부를 읽어와서
                isLogin=obj.getBoolean("isLogin");
                //만일 로그인했다면
                if(isLogin) {
                    //필드에 로그인된 아이디를 담아둔다.
                    id = obj.getString("id");
                }
            }catch(Exception e){//예외가 발생하면
                Log.e("LoginCheckTask", e.getMessage());
            }finally {
                try{
                    if(isr!=null)isr.close();
                    if(br!=null)br.close();
                    if(conn!=null)conn.disconnect();
                }catch(Exception e){}
            }
            //로그인 여부를 리턴하면 아래의 onPostExecute()메소드에 전달된다.
            return isLogin;
        }

        @Override
        protected void onPostExecute(Boolean isLogin) {
            super.onPostExecute(isLogin);
            //여기는 UI Thread이기 때문에 UI와 관련된 작업을 할 수 있다.
            //TextView에 로그인 여부를 출력하기
            if(isLogin){
                binding.userInfo.setText(id+" 님 로그인중...");
            }else{
                binding.userInfo.setText("로그인이 필요 합니다.");
            }
        }
    }
}