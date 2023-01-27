package com.example.androidinhome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //webView의 참조값 얻어오기
        WebView webView = findViewById(R.id.webView);
        //WebView의 설정 객체 가져오기
        WebSettings ws = webView.getSettings();
        //javascript해석이 가능하도록 설정하기
        ws.setJavaScriptEnabled(true);
        //WebView클라이언트 객체 넣어주기
        webView.setWebChromeClient(new WebChromeClient());
        /*
            원하는 url로딩시키기
            인터넷 자원을 사용해야 한다. => 비용이 발생할 가능성이 있다. => 사용자의 허가를 얻어내야 한다.
            허가(permission)
            인터넷을 사용하겠다는 permission이 AndroidManifest.xml문서에 있어야 한다.
         */
        webView.loadUrl("https://naver.com");
    }
}