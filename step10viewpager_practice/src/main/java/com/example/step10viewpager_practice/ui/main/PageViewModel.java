package com.example.step10viewpager_practice.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {
    //수정이 가능한 Live Data(MutableLiveData 객체)
    private MutableLiveData<String> ownerName = new MutableLiveData<>();

    //읽기 전용 LiveData(LiveData객체) -> 이것이 viewPager에 출력되어 우리 눈에 보이게 되는 것이다,
    private LiveData<String> mText = Transformations.map(ownerName, new Function<String, String>() {
        @Override
        public String apply(String input) {
            return "이 구역의 주인은: " + input;
        }
    });

    public void setOwnerName(String newOwner) {
        ownerName.setValue(newOwner);
    }

    public LiveData<String> getText() {
        return mText;
    }
}