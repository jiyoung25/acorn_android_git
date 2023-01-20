package com.example.step10.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {
    //수정이 가능한 Live Data (MutableLiveData객체)
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    
    //읽기 전용 Live Data(LiveData객체) -> 이것이 viewPager에 출력이 되어 우리 눈에 보이는 것이다.
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });
    
    //수정이 가능한 LiveData를 update하는 메소드
    public void setIndex(int index) {
        mIndex.setValue(index);
    }
    //읽기 전용 라이브 데이터를 리턴해주는 메소드
    public LiveData<String> getText() {
        return mText;
    }
}