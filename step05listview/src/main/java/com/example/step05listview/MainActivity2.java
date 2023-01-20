package com.example.step05listview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

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
        for(int i=0; i<14; i++){
            names.add("아무개"+i);
        }


        //ListView 에 연결할 아답타 객체
        // new ArrayAdapter<>( Context , layout resource , 모델 )
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

    //DialogInterface.OnClickListener type 필드
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            // 네/아니오 (Negative button인지 Positive button인지 구별할 숫자값이 매개변수 i에 전달된다.)
            //DialogInterface.BUTTON_POSITIVE; 상수값으로 숫자값 구별 가능
            //DialogInterface.BUTTON_NEGATIVE;
            if(i == DialogInterface.BUTTON_POSITIVE){
                //필드에 저장된 값을 활용해서 데이터를 삭제하기
                names.remove(selectedIndex);
                adapter.notifyDataSetChanged();
            }
        }
    };

    //롱 클릭된 인덱스를 저장할 필드
    int selectedIndex;

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        //오랫동안 클릭한 셀에 출력된 이름 읽어오기
        String name = names.get(i);
        //위에 필드의 값을 넣어줄 때 사용한 익명의 클래스에서 참조가 가능하도록
        selectedIndex = i;
        
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage(name+"을 삭제하시겠습니까?")
                .setNegativeButton("아니요", listener)
                .setPositiveButton("네", listener)
                .create()
                .show();

        //이벤트 전파를 여기서 막기

        return false;
    }
}