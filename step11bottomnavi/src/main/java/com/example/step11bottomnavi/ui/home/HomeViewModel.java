package com.example.step11bottomnavi.ui.home;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    //라이브데이터를 변경하는 메소드
    //새로운 text가 인자로 들어오면
    public void setmText(String text) {
        //라이브 데이터를 수정한다.
        mText.setValue(text);
    }

    //읽기 전용 라이브 데이터를 리턴해주는 메소드
    public LiveData<String> getText() {
        return mText;
    }
}