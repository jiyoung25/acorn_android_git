package com.example.step17example_t

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.step17example_t.databinding.ActivityMainBinding
import com.example.step17example_t.databinding.ListviewCellBinding
import org.json.JSONArray
import org.json.JSONObject

/*
    view binding사용하는 방법
    1. build.gradle 파일의 android에 아래의 설정 추가
    buildFeatures{
        viewBinding = true
    }
    2. 우상단에 sync now 링크를 눌러서 설정이 적용되도록 한다.
    3. layout.xml문서의 이름대로 클래스가 자동으로 만들어진다.
       ex) activity_main.xml문서이면 ActivityMainBinding 클래스
           activity_sub.xml문서이면 ActivitySubBinding 클래스
 */

class MainActivity : AppCompatActivity(), Util.RequestListener, View.OnClickListener, OnItemLongClickListener {

    lateinit var adapter: TodoAdapter
    lateinit var listView: ListView
    lateinit var list:MutableList<TodoDto>

    lateinit var binding: ActivityMainBinding
    val con:AppConstants = AppConstants()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //R.layout.activity_main.xml 문서를 전개해서 View만들기
        binding = ActivityMainBinding.inflate(layoutInflater)
        //전개된 Layout에서 root를 얻어내어 화면을 구성한다.(여기서는 LinearLayout이다.)
        setContentView(binding.root)

        val url = con.BASE_URL +"/api/todo/list"
        Util.sendPostRequest(con.REQUEST_TODO_LIST, url, null, this)
        list = mutableListOf()
        listView = findViewById(R.id.listView)

        val lcBinding:ListviewCellBinding = ListviewCellBinding.inflate(layoutInflater)
        adapter = TodoAdapter(this, R.layout.listview_cell , list)

        listView.adapter = adapter

        val addBtn:Button = binding.addBtn
        addBtn.setOnClickListener(this)
        listView.onItemLongClickListener=this
    }

    override fun onSuccess(requestId: Int, result: Map<String, Any?>?) {
        if(requestId == con.REQUEST_TODO_LIST){
            val jsonStr:String = result?.get("data").toString()

            val jarr = JSONArray(jsonStr)
            lateinit var jobj:JSONObject
            for(i in 0 until jarr.length() ){
                jobj = jarr.getJSONObject(i)
                val dto:TodoDto = TodoDto(jobj.getInt("num"), jobj.getString("content"), jobj.getString("regdate"))
                list.add(dto)
            }
            adapter.list = list
            adapter.notifyDataSetChanged()
        } else if(requestId == con.REQUEST_TODO_INSERT){
            //list초기화
            list.clear()

            //리스트 정보 다시 불러오기
            val url2 = con.BASE_URL+"/api/todo/list"
            Util.sendPostRequest(con.REQUEST_TODO_LIST, url2, null, this)
        } else if(requestId == con.REQUEST_TODO_DELETE){
            //list초기화
            list.clear()

            //리스트 정보 다시 불러오기
            val url2 = con.BASE_URL+"/api/todo/list"
            Util.sendPostRequest(con.REQUEST_TODO_LIST, url2, null, this)
        }
    }
    override fun onFail(requestId: Int, result: Map<String, Any?>?) {
    }

    override fun onClick(v: View?) {
        val inputText:EditText = binding.inputText
        val content = inputText.text.toString()
        val url:String = con.BASE_URL+"/api/todo/insert"
        Util.sendPostRequest(con.REQUEST_TODO_INSERT, url, mapOf("content" to content), this)
        inputText.setText("")
    }

    override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View?,//클릭한 cell의 View
        position: Int, //클릭한 cell의 index
        id: Long //TodoAdapter가 return한 itemId가 이 값이다.
    ): Boolean {
        val builder:AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("알림")
            .setMessage("삭제하시겠습니까?")
            .setPositiveButton("예", DialogInterface.OnClickListener { dialog, which ->
                val url:String = con.BASE_URL+"/api/todo/delete"
                Util.sendPostRequest(con.REQUEST_TODO_DELETE, url, mapOf("num" to "${id}"), this)
            })
            .setNegativeButton("아니오", null)
            .create()
            .show()

        return true
    }
}