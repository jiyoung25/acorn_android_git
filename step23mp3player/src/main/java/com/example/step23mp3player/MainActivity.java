package com.example.step23mp3player;

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
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    MediaPlayer mp;
    //재생 준비가 되었는지 여부
    boolean isPrepared=false;
    ImageButton playBtn;
    ProgressBar progress;
    TextView time;
    SeekBar seek;
    
    //채널의 아이디
    public static final String CHANNEL_ID = "my_channel";
    //알림의 아이디
    public static final int NOTI_ID = 999;
    //UI 를 주기적으로 업데이트 하기 위한 Handler
    Handler handler=new Handler(){
        /*
            이 Handler message를 한 번만 보내면 아래의 handleMessage() method가 1/10초마다 반복적으로 호출된다.
         */
        @Override
        public void handleMessage(@NonNull Message msg) {
            int currentTime=mp.getCurrentPosition();
            //음악 재생이 시작된 이후에 주기적으로 계속 실행이 되어야 한다.
            progress.setProgress(currentTime);
            seek.setProgress(currentTime);
            //현재 재생 시간을 TextView 에 출력하기
            String info=String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(currentTime),
                    TimeUnit.MILLISECONDS.toSeconds(currentTime)
                            -TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS. toMinutes(currentTime)) );
            time.setText(info);
            //알림 띄우기
            makeManualCancelNoti();
            //자신의 객체에 다시 빈 메세제를 보내서 handleMessage() 가 일정시간 이후에 호출 되도록 한다.
            handler.sendEmptyMessageDelayed(0, 100); // 1/10 초 이후에 // 1초에 10번씩 호출되는 Handler
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TextView 의 참조값 얻어와서 필드에 저장
        time=findViewById(R.id.time);
        // %d 는 숫자, %s 문자
        String info=String.format("%d min, %d sec", 0, 0);
        time.setText(info);
        //ProgressBar 의 참조값 얻어오기
        progress=findViewById(R.id.progress);
        seek=findViewById(R.id.seek);

        //재생 버튼
        playBtn=findViewById(R.id.playBtn);
        //재생 버튼을 사용 불가 상태로 일단 만들어 놓고
        playBtn.setEnabled(false);
        playBtn.setOnClickListener(v->{
            //만일 준비 되지 않았으면
            if(!isPrepared){
                return;//메소드를 여기서 종료
            }
            //음악 재생
            mp.start();
            //알림 띄우기
            makeManualCancelNoti();
            //핸들렁 메시지 보내기
            handler.sendEmptyMessageDelayed(0,100);

        });
        //일시 중지 버튼
        ImageButton pauseBtn=findViewById(R.id.pauseBtn);
        pauseBtn.setOnClickListener(v->{
            mp.pause();
        });

        //알림 채널 만들기
        createNotificationChannel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //음악을 재생할 준비를 한다.
        try {
            mp= new MediaPlayer();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setDataSource("http://192.168.0.31:9000/boot07/resources/upload/mp3piano.mp3");
            mp.setOnPreparedListener(this);
            //로딩하기
            mp.prepareAsync();
        }catch(Exception e){
            Log.e("MainActivity", e.getMessage());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mp.stop();
        mp.release();
    }

    //재생할 준비가 끝나면 호출되는 메소드
    @Override
    public void onPrepared(MediaPlayer mp) {
        Toast.makeText(this, "로딩완료!", Toast.LENGTH_SHORT).show();
        isPrepared=true;
        playBtn.setEnabled(true);
        //전체 재생 시간을 ProgressBar 의 최대값으로 설정한다.
        progress.setMax(mp.getDuration());
        seek.setMax(mp.getDuration());
        Log.e("전체 시간", "duration:"+mp.getDuration());
        //Handler 객체에 메세지를 보내서 Handler 가 동작 되도록 한다.
        handler.sendEmptyMessageDelayed(0, 100);
    }

    //앱의 사용자가 알림을 직접 관리 할수 있도록 알림 체널을 만들어야한다.

    public void createNotificationChannel(){
        //알림 체널을 지원하는 기기인지 확인해서
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //알림 체널을 만들기

            //셈플 데이터
            String name="Music Player";
            String text="Control";
            //알림체널 객체를 얻어내서
            NotificationChannel channel=
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_LOW);
            //체널의 설명을 적고
            channel.setDescription(text);
            //알림 메니저 객체를 얻어내서
            NotificationManager notiManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            //알림 체널을 만든다.
            notiManager.createNotificationChannel(channel);

        }

    }
    //수동으로 취소하는 알림을 띄우는 메소드
    public void makeManualCancelNoti(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            //권한이 필요한 목록을 배열에 담는다.
            String[] permissions = {Manifest.permission.POST_NOTIFICATIONS};
            //배열을 전달해서 해당 권한을 부여하도록 요청한다.
            ActivityCompat.requestPermissions(this,
                    permissions,
                    0); //요청의 아이디
            return;
        }


        //인텐트 전달자 //객체 Activity를 활성화 시키고 싶으면 getActivity, Service를 활성화시키고 싶으면 getService
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTI_ID, intent, PendingIntent.FLAG_MUTABLE);

        Intent iPlay = new Intent(this, MusicService.class);
        iPlay.setAction(AppConstants.ACTION_PLAY);
        PendingIntent pIntentPlay = PendingIntent.getService(this, 1, iPlay, PendingIntent.FLAG_MUTABLE);

        iPlay.setAction(AppConstants.ACTION_PAUSE);
        PendingIntent pIntentPause = PendingIntent.getService(this, 1, iPlay, PendingIntent.FLAG_MUTABLE);

        iPlay.setAction(AppConstants.ACTION_STOP);
        PendingIntent pIntentStop = PendingIntent.getService(this, 1, iPlay, PendingIntent.FLAG_MUTABLE);

        //띄울 알림을 구성하기
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.star_on) //알림의 아이콘
                .setContentTitle("피아노 재생중") //알림의 제목
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) //알림의 우선순위
                .addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play,"Play",pIntentPlay)) // 위에서 만든 pendingIntent
                .addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play,"Pause",pIntentPause))
                .addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play,"Stop",pIntentStop))
                .setProgress(mp.getDuration(), mp.getCurrentPosition(), false)
                //.setContentIntent(pendingIntent)  //인텐트 전달자 객체
                .setAutoCancel(false); //자동 취소 되는 알림인지 여부

        //알림 만들기
        Notification noti=builder.build();

        //알림 메니저를 이용해서 알림을 띄운다.
        NotificationManagerCompat.from(this).notify(NOTI_ID , noti);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:
                //권한을 부여 했다면
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //자동 취소 알림 띄우기
                    makeManualCancelNoti();
                }else{//권한을 부여 하지 않았다면
                    Toast.makeText(this, "알림을 띄울 권한이 필요합니다.",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

