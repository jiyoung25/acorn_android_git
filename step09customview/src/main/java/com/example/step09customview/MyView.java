package com.example.step09customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {
    //색상을 나타내는 상수값을 미리 int[]에 준비하고
    int[] colors = {Color.GREEN, Color.RED, Color.BLUE};
    //인덱스로 사용할 필드
    int index;
    int x =100;
    int y = 100;


    //생성자1
    public MyView(Context context){
        super(context);
    }
    //생성자2
    public MyView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
    }

    //View가 차지하고 있는 화면에 Canvas 객체를 이용해서 그림 그리기(화면 구성하기)
    @Override
    protected void onDraw(Canvas canvas){

        canvas.drawColor(colors[index]);
        
        //BitmapFactory클래스의 static 메소드를 이용해서 이미지를 로딩
        //Bitmap타입으로 만들기 (이 이미지는 pixel 단위이다.)
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.korea);
        //Bitmap의 크기를 변환하기
        Bitmap scaledImage = Bitmap.createScaledBitmap(image, 100, 100, false);
        //Canvas 객체를 이용해서 이미지의 좌상단 좌표를 지정하고 그린다. (좌상단의 xy좌표가 left와 top이다.)
        canvas.drawBitmap(scaledImage, x, y, null);
    }

    //이제 MyView는 나의 Custom View가 되었다!


    //onTouchEvent라는게 있따!
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //index를 1씩 증가시키고
        index++;
        //만일 없는 index라면
        if(index==3){
            index=0; //0으로 초기화
        }

        //left와 top을 10씩 증가시키기
        x+=10;

        //화면을 갱신하는 메소드 호출! (결과적으로 View가 무효화되고 onDraw()가 다시 호출됨)
        invalidate();

        return super.onTouchEvent(event);
    }
}
