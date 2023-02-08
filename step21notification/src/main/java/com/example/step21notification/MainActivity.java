package com.example.step21notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //알림 체널의 이름 정하기
    public static final String CHANNEL_NAME = "com.example.step21notification.MY_CHANNEL";
    //필요한 필드
    EditText inputMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputMsg = findViewById(R.id.inputMsg);
        //Auto Cancel 버튼을 눌렀을때 동작
        Button notiBtn = findViewById(R.id.notiBtn);
        notiBtn.setOnClickListener(v -> {
            makeAutoCancelNoti();
        });
    }
    public void makeAutoCancelNoti(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            //권한이 필요한 목록을 배열에 담는다.
            String[] permissions={Manifest.permission.POST_NOTIFICATIONS};
            //배열을 전달해서 해당 권한을 부여하도록 요청한다.
            ActivityCompat.requestPermissions(this,
                    permissions,
                    0); //요청의 아이디
            return;
        }
        //입력한 문자열을 읽어온다.
        String msg = inputMsg.getText().toString();
        createNotificationChannel();
        //알림을 클릭했을때 활성화시킬 액티비티 정보를 담고 있는 Intent 객체
        Intent intent = new Intent(this, DetailActivity.class);
        //Activity를 실행하는데 새로운 TASK에서 실행되도록 한다. (기존에 onStop()에 머물러 있다면 제거하고 새로 시작한다. = 기존의 Activity를 제거하고 새로 시작한다는 뜻)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //intent에 msg라는 key로 String type을 담는다. 새로 시작된 Activity에서 읽어낼 수 있다.
        intent.putExtra("msg", msg);

        //알림의 아이디 얻어내기 (나중에 수동으로 취소하려면 알림의 아이디가 필요하다. => 알림의 ID는 겹치지 않도록 하기)
        int currentId = (int) (System.currentTimeMillis() / 1000); //큰 정수값을 얻어낸다고 생각하면 된다.
        Log.e("currentId", ""+currentId);

        //인텐트 전달자 객체
        PendingIntent pendingIntent = PendingIntent.getActivity(this, currentId, intent, PendingIntent.FLAG_MUTABLE);

        //띄울 알림을 구성하기
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_NAME)
                .setSmallIcon(android.R.drawable.ic_btn_speak_now) //알림의 Icon
                .setContentTitle("얘들아 나야~") //알림의 제목
                .setContentText(msg) //알림의 내용
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) //알림의 우선순위
                .setContentIntent(pendingIntent) //Intent전달자 객체
                .setAutoCancel(true); //자동 취소 되는 알림인지 여부 (true : 읽는 순간 자동으로 사라짐)

        //알림 만들기
        Notification noti = builder.build();

        //알림 매니저를 이용해서 알림을 띄운다.
        NotificationManagerCompat.from(this).notify(currentId, builder.build()); //builder.build()대신에 noti를 넣어도 된다.
    }


    //앱의 사용자가 알림을 직접 관리 할수 있도록 알림 체널을 만들어야한다.
    public void createNotificationChannel(){
        //알림 체널을 지원하는 기기인지 확인해서
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //알림 체널을 만들기

            //셈플 데이터
            String name="김구라";
            String text="테스트!";
            //알림체널 객체를 얻어내서
            NotificationChannel channel=
                    new NotificationChannel(CHANNEL_NAME, name, NotificationManager.IMPORTANCE_DEFAULT);
            //체널의 설명을 적고
            channel.setDescription(text);
            //알림 메니저 객체를 얻어내서
            NotificationManager notiManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            //알림 체널을 만든다.
            notiManager.createNotificationChannel(channel);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:
                //권한을 부여 했다면
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //자동 취소 알림 띄우기
                    makeAutoCancelNoti();
                }else{//권한을 부여 하지 않았다면
                    Toast.makeText(this, "알림을 띄울 권한이 필요합니다.",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}