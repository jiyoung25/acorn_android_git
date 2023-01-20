package com.example.step11bottomnavi.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.step11bottomnavi.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        //new Observer을 람다식으로 줄임
        /*  1. 정석
            new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    textView.setText(s);
                }
            }

            2. s->textView.setText(s)

            3. textView::setText
            
            **람다식에서 매개변수를 중복으로 쓰는 불편함을 없애는 이중콜론 ::연산자
            textView::setText인자로 전달받은 값을 textView 객체의 setText메소드를 호출하면서 전달을 해라!라는 의미
         */
        /*
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>(){
            @Override
            public void onChanged(String s){
                textView.setText(s);
            }
        });
         */
        //dashboardViewModel.getText().observe(getViewLifecycleOwner(), s->textView.setText(s));
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        textView.setOnClickListener(v->dashboardViewModel.setmText("clicked!"));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}