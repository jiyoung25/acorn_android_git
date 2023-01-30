package com.example.step17example;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TodoAdapter extends BaseAdapter {

    Context context;
    int layoutRes;
    List<TodoDto> list;

    public TodoAdapter(MainActivity mainActivity, int listview_cell, List<TodoDto> list) {
        this.context = mainActivity;
        this.layoutRes = listview_cell;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getNum();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View resultView= null;

        resultView = convertView == null?
                LayoutInflater.from(context).inflate(layoutRes, parent, false)
                : convertView;
        TodoDto data = list.get(position);

        TextView text_content = resultView.findViewById(R.id.text_content);
        TextView text_regdate = resultView.findViewById(R.id.text_regdate);

        text_content.setText(data.getContent());
        text_regdate.setText(data.getRegdate());

        return resultView;
    }
}
