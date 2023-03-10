package com.example.step14sqlite

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), OnClickListener, OnItemLongClickListener {
    
    //필요한 필드 정의하기

    //null을 대입했다가 나중에 값을 바꾸려면 번거롭다.
    lateinit var inputText: EditText //lateinit예약어를 활용하면 null을 넣을 필요 없이 값을 나중에 얻을 수 있다.
    lateinit var listView:ListView
    lateinit var adapter:TodoAdapter
    lateinit var helper:DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //필요한 UI의 참조값을 얻어와서 필드에 저장하기
        inputText = findViewById(R.id.inputText)
        listView = findViewById(R.id.listView)

        //버튼 리스너 등록하기
        findViewById<Button>(R.id.addBtn).setOnClickListener(this)
        
        //DBHelper객체의 참조값을 필드에 저장하기
        helper = DBHelper(this, "MyDB.sqlite", null, 1)

        //할 일 목록 얻어오기  // type은 추론이 가능하므로 List<Todo>는 생략 가능
        val list:List<Todo> = TodoDao(helper).getList()

        //listView동작 준비하고, 할 일 목록 출력하기
        adapter = TodoAdapter(this, R.layout.listview_cell, list )
        listView.adapter = adapter

        listView.setOnItemLongClickListener(this)
    }


    override fun onClick(v: View?) {
        //1. 입력한 문자열을 읽어와서
        val msg = inputText.text.toString()
        //2. Todo객체에 담아서
        val data = Todo(0, msg, "")//빈 생성자를 억지로 만들어줌
        //3. TodoDao객체를 이용해서 DB에 저장한다.
        TodoDao(helper).insert(data)
        //4. 목록을 새로 얻어와서
        val list = TodoDao(helper).getList()
        //5. adapter에 넣어주고
        adapter.list = list
        //6. model의 내용이 바뀌었다고 adapter에 달려서 ListView가 업데이트 되도록 한다.
        adapter.notifyDataSetChanged()
        //7. Toast띄우기
        Toast.makeText(this, "저장했습니다", Toast.LENGTH_SHORT).show()
        inputText.setText("")

        //8. ListView의 가장 아래쪽이 화면에 보일 수 있도록 부드럽게 스크롤 시키기
        listView.smoothScrollToPosition(adapter.count)
    }

    override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ): Boolean {
        // 여기서 position 은 클릭한 cell 의 인덱스
        // 여기서 id 는 클릭한 cell 의 아이디 ( Todo 의 primary key )
        // id 에 전달되는 값은 TodoAdapter 의  getItemId() 메소드에서 리턴한 값

        AlertDialog.Builder(this)
            .setTitle("알림")
            .setMessage("삭제 하시겠습니까?")
            .setPositiveButton("네", DialogInterface.OnClickListener { dialog, which ->
                val dao=TodoDao(helper)
                dao.delete(id.toInt())
                //목록을 새로 얻어와서
                val list=TodoDao(helper).getList()
                //아답타에 넣어주고
                adapter.list=list
                //모델의 내용이 바뀌었다고 아답타에 알려서 ListView 가 업데이트 되도록 한다.
                adapter.notifyDataSetChanged()
            })
            .setNegativeButton("아니요", null)
            .create()
            .show()

        return false
    }
}