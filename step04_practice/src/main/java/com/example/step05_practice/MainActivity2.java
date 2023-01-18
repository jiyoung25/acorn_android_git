package com.example.step05_practice;

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
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        names = new ArrayList<>();
        names.add("원숭이");
        names.add("주뎅이");
        names.add("김구라");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , names);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String name = names.get(i);
        Toast.makeText(this, name , Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        String name = names.get(i);

        int selectedIndex = i;

        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage(name+"을 삭제하시겠습니까?")
                .setNegativeButton("아니요", null)
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        names.remove(selectedIndex);
                        adapter.notifyDataSetChanged();
                    }
                })
                .create()
                .show();

        return false;
    }
}