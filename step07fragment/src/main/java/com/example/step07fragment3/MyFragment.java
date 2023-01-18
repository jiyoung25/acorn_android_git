package com.example.step07fragment3;

    /*

        [ Fragment란? ]
        - activity의 제어 하에 존재하는 mini Controller
        - 재활용을 염두에 두고 만드는 경우가 많다.
        - 재활용이란? 여러 개의 activity에서 활용된다는 의미이다.
        - 각각의 fragment는 고유의 UI가 있다.
        - 특정 Activity에 의존성이 있으면 안된다 . (import MainActivity 이런거 안됨)

        [ Fragment 만드는 방법 ]
        1. Fragment class를 상속받는다.
        2. Fragment의 layout .xml 문서를 만든다.
        3. onCreateView() method를 overriding한다.

     */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class MyFragment extends Fragment implements View.OnClickListener {
    //필드
    TextView textView;
    int count=0;

    //레이아웃으로 사용할 View를 만들어서 리턴해주어야 한다.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance){
        //1. fragment_my.xml문서를 전개해서 View를 만든 다음
            //여기서 만든 view를 부모에게 바로 붙일것이냐 -> attachToRoot
            //false를 해서 부모가 준비된 이후에 붙임 -> 안정성 상승
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        //만든 View에서 TextView의 참조값을 얻어낸다.
        textView = view.findViewById(R.id.textView);
        textView.setOnClickListener(this);

        //해당 view를 리턴해 준다.
        return view;
    }

    //TextView를 클릭하면 호출되는 메소드
    @Override
    public void onClick(View view) {
        //Count값을 1 증가시키고
        count++;
        //정수를 문자열로 만들어서 TextView에 출력하기
        textView.setText(Integer.toString(count));

        /*
            만일 count값이 10의 배수이면 이 fragment를 제어하고 있는 activity에 해당 정보를 알려보자.
            
            해당 activity의 특정 method를 호출하면서 문자열 전달하기

            단, 특정 activity의 의존성은 없어야 한다.
         */

        // 이 fragment를 제어하고 있는 activity의 참조값 얻어내기
        FragmentActivity fa=getActivity();

        
        //MainActivity activity = (MainActivity) this.getActivity();
        //혹시 이 activity가 MyFragmentListener interface를 구현하지 않았을 수도 있기 때문에
        if(count % 10 == 0 & fa instanceof MyFragmentListener){
            MyFragmentListener ma = (MyFragmentListener)fa;
            ma.sendMsg(count+"입니다. 액티비티님!");
        }
    }

    //액티비티에서 특정 시점에 호출할 예정인 메소드
    public void reset(){
        count=0;
        textView.setText("0");
    }

    // 이 fragment에서 전달하는 message를 받을 activity에서 구현할 interface를 class안에 정의하기
    public interface MyFragmentListener{
        public void sendMsg(String msg);
    }
}
