package com.example.step06customadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/*
    ListView에 연결할 Adapter Class 정의하기
     - BaseAdapter 추상 클래스를 상속받아서 만든다.
 */

public class CountryAdapter extends BaseAdapter {
    //Field
    Context context;
    int layoutRes;
    List<CountryDto> list;

    //생성자
    public CountryAdapter(){

    }

    public CountryAdapter(Context context, int layoutRes, List<CountryDto> list) {
        this.context = context;
        this.layoutRes = layoutRes;
        this.list = list;
    }

    /*
        아래 4가지의 메소드는 ListView가 필요할 때 호출하는 메소드이다.
        따라서 적절한 값을 리턴하도록 우리가 프로그래밍 해야 한다.
     */

    //getCount는 모델의 개수를 리턴해주어야 한다.
    @Override
    public int getCount() {
        //모델의 갯수
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        //i번째 인덱스에 해당하는 model을 리턴해야 한다.
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        //i번째 인덱스에 해당하는 모델의 id(primary key값) 리턴하기 (없다면 그냥 index를 아이디 값으로 사용하기)
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Log.e("CountryAdapter", "getView() 호출됨 i"+i);

        //1. res/layout/listview_cell.xml 문서를 전개해서 View 객체를 만든다.
        if(view == null){
            Log.e("CountryAdapter", "view가 null이어서 cell view를 새로 만듭니다.");

            //레이아웃전개자(레이아웃 xml 문서를 이용해서 View 를 만드는객체 ) 객체의 참조값 얻어오기
            LayoutInflater inflater=LayoutInflater.from(context);
            //listview_cell.xml 문서를 전개해서 새로운 View 를 만든다.
            view=inflater.inflate(layoutRes, viewGroup, false);
        }
        //2. i 에 해당하는 CountryDto 객체의 참조값을 얻어온다.
        CountryDto dto=list.get(i);
        //3. 만든 View 객체 안에 있는 ImageView, TextView 의 참조값을 얻어온다.
        ImageView imageView=view.findViewById(R.id.imageView);
        TextView textView=view.findViewById(R.id.textView);
        //4. ImageView, TextView 에 정보를 출력한다.
        imageView.setImageResource(dto.getResId());
        textView.setText(dto.getName());
        // i 번째 인덱스에 해당하는 View 를 리턴해 준다.
        return view;
    }
}
