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
        //transformations.map(ownerName, new Function<String, String>()은
        //ownerName을 new Function<String, String>()으로 바꾼다는 의미이다.
        @Override
        public String apply(String input) {
            // new Function<String, String>()을 implements한 익명의 class가 override한 메소드이다.
            // 이 메소드가 인자로 받아들이는 input은 ownerName이며 new Function<String, String>의 첫 번째 General type인 String이다. (만일 int type을 인자로 받는다면 new Function<Integer, String>()이 된다.)
            // return하는 String값은 new Function<String, String>의 두 번째 General type인 String이다. (만일 return type이 Integer가 된다면 new Function<String, Integer>()이 될 것이다.)
            return "이 구역은"+input+"의 것~";
        }
    });
    
    //위 식을 람다식으로 줄이면
    //private LiveData<String> mText = Transformations.map(ownerName, input -> "이 구역은"+input+"의 것~");

    public void setOwnerName(String newOwner) {
        ownerName.setValue(newOwner);
    }

    public LiveData<String> getText() {
        return mText;
    }
}