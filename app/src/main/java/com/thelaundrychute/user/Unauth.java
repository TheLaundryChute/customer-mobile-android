package com.thelaundrychute.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import com.thelaundrychute.user.test.R;


public class Unauth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_unauth);

        WebView webview = (WebView) findViewById(R.id.webView2);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://tlc.inmotionapptest.net/login");
        //TODO: add hook for login
    }
}