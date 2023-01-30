package com.example.step10viewpager_practice.ui.main;

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

import com.example.step10viewpager_practice.R;
import com.example.step10viewpager_practice.databinding.FragmentMainBinding;

/**
 * A placeholder fragment containing a simple view.
 */
//Fragment를 상속받아 만듦
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "ownerName";

    private PageViewModel pageViewModel;
    private FragmentMainBinding binding;

    //Fragment 하나를 리턴해놓은 메소드
    //인자로 전달하는 index에 해당되는 새 Fragment(PlaceholderFragment) 객체를 리턴하는 메소드
    public static PlaceholderFragment newInstance(String ownerName) {
        //Fragment 객체 생성
        PlaceholderFragment fragment = new PlaceholderFragment();
        //Bundle객체 생성
        Bundle bundle = new Bundle();
        //ARG_SECTION_NUMBER엔 "ownerName"이라는 값이 담겨있음.
        //"ownerName"이라는 key값으로 인자로 들어오는 String ownerName을 Bundle로 감싸기
        bundle.putString(ARG_SECTION_NUMBER, ownerName);
        //Fragment에 Bundle전달
        fragment.setArguments(bundle);
        //Fragment 리턴
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //PageViewModel을 사용할 준비하기
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);

        //Fragment에 전달받은 인자(Bundle)를 얻어낸다.
        Bundle bundle = getArguments();
        //Bundle객체에 "ownerName"이라는 키값으로 담겨있는 이름을 얻어낸다.
        String ownerName = bundle.getString("ownerName");

        pageViewModel.setOwnerName(ownerName);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        //fragment_main.xml문서를 전개해서 만든 View의 참조값
        View root = binding.getRoot();

        //TextView의 참조값을 얻어온다.
        final TextView textView = binding.sectionLabel;
        //PageViewModel이 가지고 있는 Data를 관찰하고있다가 혹시 변경이 되면 UI를 업데이트할 옵저버 등록하기
        //단, 이 view의 주인(Fragment or Activity)이 활성화된 상태에서만 동작하겠다는 의미도 있음

        //view모델 출력
        //가공된 문자열이 들어온다.
        pageViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //Button을 눌렀을 때 동작할 listener등록
        binding.changeBtn.setOnClickListener(v->{
            String newName = binding.inputName.getText().toString();
            //pageViewModel에 업데이트 한다.
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