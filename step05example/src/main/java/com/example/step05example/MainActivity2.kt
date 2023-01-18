package com.example.step05example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

/*
    java >>extends AppCompatActivity 상속 <>  kotlin>> :AppCompatActivity
    implements View.OnClickListener interface 구현 => ,View.OnClickListener
 */

class MainActivity2 : AppCompatActivity(), View.OnClickListener {

    //코틀린에서는 자바와는 달리 변수를 선언하는 것만으로 값이 null로 초기화되지 않는다.
    // type 뒤에 `?`가 필요하다 >> `?=null`
    var editText:EditText?=null //editText에는 null이 들어갈 수 있다는 뜻(null일 가능성이 있다는 뜻)
    var names:MutableList<String>?=null //item을 수정 제거 가능한 리스트는 kotlin에서는 MutableList이다.
    var adapter:ArrayAdapter<String>?=null
    //위의 선언이 불편하다면 아래와 같이 뒤늦은 초기화도 가능하다.
    lateinit var listView:ListView //null말고 다른 값 나중에 넣을 테니까 신경 쓰지 말어~~라는 뜻

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView=findViewById(R.id.listView)
        editText=findViewById(R.id.editText)

        // findViewById<UI의 type>
        val addBtn = findViewById<Button>(R.id.addBtn)

        addBtn.setOnClickListener(this)

        names = mutableListOf()
        adapter=ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                names!!) //null이 들어가면 안된다는 문법 !!
                //names는 null이 아니니까 받아줘~~라는 뜻
        /*
            java에서        ㅇㅇㅇ.setXXX(value)했던 것들이
            kotlin에서는     ㅇㅇㅇ.xxx = value 이런 형태로 쓴다.
         */
        listView.adapter =  adapter
    }

    override fun onClick(p0: View?) {
        // editText는 field에 선언할 때 ?=null을 하여 null일 가능성을 두었다.
        // editText?.text는 editText 안에 값이 null이 아니면 .text를 참조하겠다는 의미이다.
        val inputName:String = editText?.text.toString()
        names?.add(inputName) //names도 null일 가능성이 있는 변수이므로 ?를 넣는다.
        adapter?.notifyDataSetChanged()
        editText?.setText("")
    }
}