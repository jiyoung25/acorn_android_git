package com.example.step19callphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText inputPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //필요한 객체의 참조값 얻어오기
        inputPhone = findViewById(R.id.inputPhone);
        Button dialBtn = findViewById(R.id.dialBtn);
        //click listener 등록
        dialBtn.setOnClickListener(v->{
            //입력한 전화번호를 읽어온다.
            String phoneNumber = inputPhone.getText().toString();
            //전화를 걸겠다는 Intent(의도) 객체 생성
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL); //'전화 어플리케이션을 키고 싶다'는 의도를 Intent객체에 담는다.
            //전화번호를 Uri 객체에 포장을 한다.
            Uri uri = Uri.parse("tel:"+phoneNumber);
            //<a href = "tel:010-1111-2222"> xxx </a>하면 자동으로 전화 어플리케이션 시작
            //<a href = "mailto:aaa@naver.com"> xxx </a>하면 자동으로 email을 보낼 수 있는 Application 시작
            //Intent객체에 담는다.
            intent.setData(uri);
            //'해당 Intent를 받아주는 Activity를 실행해주세요'라고 운영체제에 요청한다.
            startActivity(intent);
        });

        Button callBtn = findViewById(R.id.callBtn);

        //바로 전화를 걸고 싶다면 manifest.xml에서 전화를 걸겠다는 permission을 받아내어야 한다.
        callBtn.setOnClickListener(v->{
            //전화를 걸기 전에 전화걸기를 허용했는지 확인하기
            int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE);
            //만일 권한이 허용되지 않았다면
            if(permissionCheck != PackageManager.PERMISSION_GRANTED){
                //권한을 허용하도록 유도한다.

                //권한이 필요한 목록을 배열에 담는다.
                String[] permissions = {Manifest.permission.CALL_PHONE};
                //배열을 전달해서 해당 권한을 부여하도록 한다.
                ActivityCompat.requestPermissions(MainActivity.this,
                        permissions,
                        0); //요청의 아이디
                return; //메소드는 여기서 종료
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0){
            //만일 권한을 부여받았다면
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //전화 걸기
                call(); //이 클래스 안에 call이라는 메소드 만들었음
            } else{
                Toast.makeText(this, "전화를 거는 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //전화 거는 메소드
    public void call(){
        //입력한 전화번호
        String phoneNumber=inputPhone.getText().toString().trim();
        //전화를 걸겠다는 Intent 객체 생성하기
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_CALL);
        //전화번호를 Uri 로 얻어낸다.
        Uri uri=Uri.parse("tel:"+phoneNumber);
        //Intent 객체에 담는다.
        intent.setData(uri);
        //전화를 걸수 있는 액티비티를 실행 시킨다.
        startActivity(intent);
    }
}