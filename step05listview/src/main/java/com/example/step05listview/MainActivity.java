package com.example.step05listview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    //필드
    List<String> names;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ListView의 참조값
        ListView listView = findViewById(R.id.listView);

        //list에 호출할 sample data
        List<String> names = new ArrayList<>();
        names.add("김구라");
        names.add("해골");
        names.add("원숭이");
        for(int i=0; i<10; i++){
            names.add("아무개"+i);
        }

        //simple_list_item_1 : list view에 올릴 model들(여기선 List<String>)의 하나하나의 layout을 담당한다.
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                names);

        //ListView에 adapter연결하기
        listView.setAdapter(adapter);
        
        //각각의 아이템을 클릭했는지 리슨하는 객체
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // int i는 클릭한 아이템의 index가 들어있다.
        String name = names.get(i);
        Toast.makeText(this,name,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        //오랫동안 클릭한 셀에 출력된 이름 읽어오기
        String name = names.get(i);
        //아래의 익명 클래스에서 참조가 가능하도록
        int selectedIndex = i;
        
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage(name+"을 삭제하시겠습니까?")
                .setNegativeButton("아니요", null)
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //모델에서 해당 인덱스의 data를 삭제한다.
                        names.remove(selectedIndex);
                        //adapter에 모델에 변화가 생겼다고 알려준다.
                        adapter.notifyDataSetChanged();
                    }
                })
                .create()
                .show();
        //이벤트 전파를 여기서 막기

        return false;
    }
}