package com.example.step17example_t

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TodoAdapter(var context: Context, var listview_cell: Int, var list:List<TodoDto>): BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return list[position].num.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //처음에는 convertView에 null이 들어있다. 그러므로 resultView를 만들어서 return해주는 것이다.

        //view가 null이면 새로운 view를 만들고, view가 null이 아니면 view를 재활용한다.
        var resultView:View =
            if(convertView == null) LayoutInflater.from(context).inflate(listview_cell, parent, false)
            else convertView

        //TodoDto의 data 가져오기
        val data = list.get(position)

        //listview_cell layout의 TextView 구성하기
        val text_content = resultView.findViewById<TextView>(R.id.text_content)
        val text_regdate = resultView.findViewById<TextView>(R.id.text_regdate)
        text_content.setText(data.content)
        text_regdate.setText(data.regdate)

        return resultView;
    }
}