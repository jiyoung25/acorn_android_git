package com.example.step17httprequest3_kotlin

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class MainActivity : AppCompatActivity(), Util.RequestListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editText = findViewById<EditText>(R.id.inputMsg)

        //Button객체의 참조값을 얻어와서 리스너로 등록하기
        //setOnClickListener은 kotlin에서 function타입이다
        // var a:()->Unit = {} a는 function타입이다. function type은 kotlin에선 () -> Unit으로 표기한다.


        /*
            kotlin에서 익명 클래스 사용하는법 3가지
            1) findViewById<Button>(R.id.sendBtn).setOnClickListener(fun(view){})
            2) findViewById<Button>(R.id.sendBtn).setOnClickListener({})
            3) findViewById<Button>(R.id.sendBtn).setOnClickListener{}
        */
        findViewById<Button>(R.id.sendBtn).setOnClickListener{
            //editText에 입력한 문자열을 읽어와서
            val msg = editText.text.toString()
            //중간 mapOf("msg" to msg): "msg"라는 키값으로 입력한 메시지들 담은 Map
            Util.sendPostRequest(999, "http://192.168.0.33:9000/boot07/api/send", mapOf("msg" to msg), this)
        }
    }

    override fun onSuccess(requestId: Int, result: Map<String, Any?>?) {
        if(requestId == 999) {
            var jsonStr = result?.get("data").toString()
            Log.d("### json문자열 ####", jsonStr)
            //Map으로 받아온 Object형식의 Json 문자열을 사용하는 방법
            //JSONObject 객체를 생성하면서 생성자의 인자로 json문자열을 전달한다.
            val obj:JSONObject = JSONObject(jsonStr)
            val response = obj.getString("response")
            val num = obj.getInt("num")
            val isSuccess = obj.getBoolean("isSuccess")
            Log.e("real?", response+num+isSuccess)
        }
    }

    override fun onFail(requestId: Int, result: Map<String, Any?>?) {

    }
}