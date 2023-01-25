package com.example.step13sharedpref

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

/*
    App에서 문자열을 영구적으로 저장하는 방법 (영구저장이란, 앱을 종료하고 다시 시작해도 불러올 수 있는 문자열 )
    
    1. 파일 입출력을 이용해서 저장
    2. 내장 data base를 이용하여 저장 (SqlList DATABASE)
    3. SharedPreference를 이용해서 저장 (느리지만 간단히 저장하고 불러올 수 있다. )
        내부적으로 xml문서를 만들어서 문자열을 저장하고 불러온다.
        저장된 문자열을 boolean, int, double, String type으로 변환하여 불러올 수 있는 것임
 */

class MainActivity : AppCompatActivity(), View.OnClickListener { //extends AppCompatActivity implements View.OnClickListener
    /*
        java에서는 field를 선언만 하면 자동으로 null로 초기화된다.
        kotlin에서는 null이 가능한 field를 만들어서 명시적으로 넣어주어야 한다.
     */
    var editText:EditText?=null
    
    //onCreate() 메소드 재정의
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //res/layout/activity_main.xml 문서를 전개해서 화면 구성하기
        setContentView(R.layout.activity_main)
        
        //EditText객체의 참조값 얻어오기
        editText = findViewById<EditText>(R.id.editText)
        //saveBtn객체의 참조값 얻어오기
        val saveBtn:Button = findViewById(R.id.saveBtn)
        saveBtn.setOnClickListener(this)
        val readBtn = findViewById<Button>(R.id.readBtn)
        /*
        readBtn.setOnClickListener (object:View.OnClickListener{
            override fun onClick(v: View?) {

            }
        })
        */
        //위의 코드를 간략히 표현하면 아래와 같다.
        readBtn.setOnClickListener {
            val pref:SharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE)
            //"msg"라는 키값으로 저장된 문자열 읽어오기, 없다면 defValue값이 읽어와진다.
            val msg = pref.getString("msg","")

            /*
                여기서 this는 MainActivity를 가리킨다.
                java에서는 익명class에서 안에서 바깥 클래스 객체의 참조값을 가리키려면
                MainActivity.this와 같이 클래스명을 명시했어야 한다.

                kotlin에서도 익명 class 안에서 바깥 클래스 객체의 참조값을 가리키려면
                this@MainClass와 같이 클래스명을 명시하면 된다.

                단. 간략히 표현한 블럭 안에서는 this만 써도 바깥클래스 객체를 가리킬 수 있다.
                //val a:View.OnClickListener = this
                //val b:Context = this

             */
            Toast.makeText(this, msg, Toast.LENGTH_SHORT ).show()
        }
    }
    
    //저장 버튼을 누르면 호출되는 메소드
    override fun onClick(v: View?) {
        //EditText에 입력한 문자열 읽어오기
        val msg = editText?.text.toString() //null이 가능한 변수나 필드의 값을 참조할 때에는 ?가 필요하다.
        //SharedPrefence는 경제
        val pref = getSharedPreferences("info", Context.MODE_PRIVATE)
        //에디터 객체의 참조값 얻어오기
        val editor = pref.edit()
        //에디터 객체를 이용해서 문자열을 key:value형태로 영구 저장할 수 있다.
        editor.putString("msg", msg)
        editor.commit()

        AlertDialog.Builder(this)
                .setMessage("저장했습니다")
                .setNeutralButton("확인", null)
                .create()
                .show()
    }
}