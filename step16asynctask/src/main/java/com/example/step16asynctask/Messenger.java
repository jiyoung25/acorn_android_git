package com.example.step16asynctask;

import android.util.Log;

public class Messenger { //가상으로 메시지를 보내는 static method
    public static void sendMessage(String msg){
        Log.e("Messenger sendMessage()", "메시지 전송중...");
        //메시지를 전송하는 데 20초가 걸린다고 가정해보자.
        try{
            Thread.sleep((20000));
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        Log.e("Messenger sendMessage()", "메시지 전송 완료");
    }
}
