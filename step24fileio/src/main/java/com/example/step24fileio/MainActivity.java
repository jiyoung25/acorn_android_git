package com.example.step24fileio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText inputMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //필요한 UI 의 참조값 얻어오기
        inputMsg=findViewById(R.id.inputMsg);
        Button saveBtn=findViewById(R.id.saveBtn);
        //버튼에 리스너 등록
        saveBtn.setOnClickListener(this);

        Button saveBtn2=findViewById(R.id.saveBtn2);
        saveBtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveBtn:
                saveToInternal2();
                break;
            case R.id.saveBtn2:
                //외부 저장장치에 접근할 권한이 켜져 있는지여부를 상수값으로 얻어낸다
                int permissionCheck=
                        ContextCompat.checkSelfPermission(this,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(permissionCheck != PackageManager.PERMISSION_GRANTED) { //권한이 없다면
                    String[] permissions = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(this, permissions,
                            0);
                    return;
                }
                saveToExternal();
                break;
        }
    }

    //권한 요청 결과를 받을 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:
                //만일 권한을 승인 했다면
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    saveToExternal();
                }else{//승인하지 않았다면
                    Toast.makeText(this, "퍼미션이 필요합니다.", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    //외부 저장 장치에 저장하기
    public void saveToExternal(){
        //입력한 문자열을 읽어온다.
        String msg = inputMsg.getText().toString();
        
        //외부 저장 장치의 폴더를 가리키는 File객체
        File externalDir = getExternalFilesDir(null);//파일의 type을 가리지 않을 것이기 때문에 null을 넣음
        //해당 폴더의 절대경로를 얻어낸다.
        String absolutePath = externalDir.getAbsolutePath();
        //text파일을 만들기 위한 file객체 생성
        File file = new File(absolutePath+"/memo.txt");
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.append(msg);
            fw.flush();
            fw.close();
        }catch (Exception e){
            Log.e("saveToExternal()", e.getMessage());
        }
    }

    //내부 저장 장치에 저장하기
    public void saveToInternal(){
        //입력한 문자열을 읽어온다.
        String msg=inputMsg.getText().toString();
        try {
            //파일을 저장하기 위한 디렉토리 만들기
            File dir=new File(getFilesDir(), "myDir");
            if(!dir.exists()){
                dir.mkdir();
            }
            //해당 디렉토리에 파일을 만들기 위한 File 객체
            File file=new File(dir, "memo.txt");
            FileWriter fw=new FileWriter(file, true);
            fw.append(msg+"\n");
            fw.flush();
            fw.close();
        }catch(Exception e){
            Log.e("saveToInternal()", e.getMessage());
        }
    }
    //내부 저장 장치에 저장하기
    public void saveToInternal2(){
        //입력한 문자열을 읽어온다.
        String msg=inputMsg.getText().toString();
        try {
            FileOutputStream fos=openFileOutput("memo2.txt", MODE_APPEND);
            PrintWriter pw=new PrintWriter(fos);
            pw.println(msg+"\n");
            pw.flush();
            pw.close();
        }catch(Exception e){
            Log.e("saveToInternal()", e.getMessage());
        }
    }
}




