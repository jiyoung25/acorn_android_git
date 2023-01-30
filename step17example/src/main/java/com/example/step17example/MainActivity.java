package com.example.step17example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Util.RequestListener, AdapterView.OnItemLongClickListener, View.OnClickListener {

    ListView listView;
    TodoAdapter adapter;
    List<TodoDto> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "http://192.168.0.33:9000/boot07/api/todo/list";

        Util.sendPostRequest(1000, url, null, this);

        listView = findViewById(R.id.listView);
        adapter = new TodoAdapter(this, R.layout.listview_cell, list);
        listView.setAdapter(adapter);

        findViewById(R.id.addBtn).setOnClickListener(this);
        listView.setOnItemLongClickListener(this);

    }

    @Override
    public void onSuccess(int requestId, Map<String, Object> result) {
        if(requestId == 1000) {
            String jsonStr = result.get("data").toString();
            try {
                JSONArray jarray = new JSONArray(jsonStr);


                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jobj = jarray.getJSONObject(i);

                    int num = jobj.getInt("num");
                    String content = jobj.getString("content");
                    String regdate = jobj.getString("regdate");

                    TodoDto dto = new TodoDto(num, content, regdate);
                    list.add(dto);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            adapter.list = list;
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFail(int requestId, Map<String, Object> result) {

    }

    @Override
    public void onClick(View v) {
        //insert하고
        String url = "http://192.168.0.33:9000/boot07/api/todo/insert";
        EditText inputText = findViewById(R.id.inputText);
        String content = inputText.getText().toString();
        Util.sendPostRequest(1001, url, Map.of("content", content), this);
        inputText.setText("");

        //list초기화
        list.clear();

        //리스트 정보 다시 불러오기
        String url2 = "http://192.168.0.33:9000/boot07/api/todo/list";
        Util.sendPostRequest(1000, url2, null, this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //삭제 알람
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림")
                .setMessage("삭제하시겠습니까?")
                //삭제하겠다고 하면 삭제해주기
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = "http://192.168.0.33:9000/boot07/api/todo/delete";
                        Util.sendPostRequest(1002, url, Map.of("num", Long.toString(id)), MainActivity.this );

                        //list초기화
                        list.clear();

                        //리스트 정보 다시 불러오기
                        String url2 = "http://192.168.0.33:9000/boot07/api/todo/list";
                        Util.sendPostRequest(1000, url2, null, MainActivity.this);
                    }
                })
                .setNegativeButton("아니오", null)
                .create()
                .show();


        return false;
    }
}