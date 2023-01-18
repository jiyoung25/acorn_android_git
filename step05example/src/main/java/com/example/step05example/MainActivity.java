package com.example.step05example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button addBtn;
    EditText editText;
    List<String> names;
    ArrayAdapter<String> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);

        editText = findViewById(R.id.editText);

        names=new ArrayList<>();

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                names);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        String name = editText.getText().toString();

        names.add(name);
        adapter.notifyDataSetChanged();
        editText.setText("");
    }
}