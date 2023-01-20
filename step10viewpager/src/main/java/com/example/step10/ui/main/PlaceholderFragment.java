package com.example.step10.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.step10.R;
import com.example.step10.databinding.FragmentMainBinding;

/**
 * A placeholder fragment containing a simple view.
 */
//프래그먼트를 상속받아 만듬
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentMainBinding binding;

    //프래그먼트 하나를 리턴해 놓은 메소드
    //인자로 전달하는 index에 해당되는 새 Fragment(PlaceholderFragment)객체를 리턴하는 메소드
    public static PlaceholderFragment newInstance(String ownerName) {
        //프래그먼트 객체를 생성하고
        PlaceholderFragment fragment = new PlaceholderFragment();
        //bundle객체를 생성해서
        Bundle bundle = new Bundle();
        //ownerName이라는 키값으로 전달된 이름을 담고
        bundle.putString("ownerName",ownerName);
        //Fragment에 전달하고
        fragment.setArguments(bundle);
        //해당 Fragment객체를 리턴한다.
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //PageViewModel을 사용할 준비하기
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);

        //Fragment에 전달받은 인자(Bundle)를 얻어낸다.
        Bundle bundle = getArguments();
        //bundle객체에 "ownerName"이라는 키값으로 담겨있는 이름을 얻어낸다.
        String ownerName = bundle.getString("ownerName");

        pageViewModel.setOwnerName(ownerName);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        //fragment_main.xml문서를 전개해서 만든 View의 참조값
        View root = binding.getRoot();
        
        //TextView의 참조값을 얻어와서
        final TextView textView = binding.sectionLabel;
        //PageViewModel이 가지고 있는 데이터를 관찰하고 있다가 혹시 변경이 되면 UI를 업데이트할 옵저버 등록
        //단, 이 view의 주인(Fragment 혹은 Activity)가 활성화된 상태에서만 동작하겠다는 의미

        //view모델 출력
        //가공된 문자열이 들어온다.
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //TextView의 문자열을 출력하기
                textView.setText(s);
            }

        });

        //버튼을 눌렀을 때 동작할 리스터 등록
        binding.changeBtn.setOnClickListener(view->{
            //임력한 이름을 읽어와서
            String newName = binding.inputName.getText().toString();
            //pageViewModel이 가지고있는 어쩌구에 업데이트한다.
            pageViewModel.setOwnerName(newName);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}