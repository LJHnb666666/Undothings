package com.google.samples.apps.sunflower;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity{
    WebView wb;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        wb = findViewById(R.id.loginWebview);
        btn = findViewById(R.id.loginBtn);

        wb.getSettings().setJavaScriptEnabled(true);
        wb.setWebViewClient(new WebViewClient());
        wb.setWebChromeClient(new MyWebChromeClient());
        wb.loadUrl("https://todo-beige-one.vercel.app");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("ljh","进入到点击事件了");
                wb.evaluateJavascript("javascript:sendCallbackRes(true)", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                    }
                });
            }
        });
    }



    class MyWebviewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        //监听网页进度 newProgress进度值在0-100
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        //设置Activity的标题与 网页的标题一致
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            //setTitle(title);
        }
    }
}