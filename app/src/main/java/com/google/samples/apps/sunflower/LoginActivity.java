package com.google.samples.apps.sunflower;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.google.samples.apps.sunflower.net.bean.UserBean;
import com.google.samples.apps.sunflower.net.interceptor.RxJavaInterceptor;
import com.google.samples.apps.sunflower.net.service.UserService;
import com.google.samples.apps.sunflower.net.serviceconfig.ApiClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        wb.addJavascriptInterface(new UserLoginAndRegister(),"userLoginAndRegister");
        wb.loadUrl("https://todo-beige-one.vercel.app");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, UndoActivity.class);
                startActivity(intent);
            }
        });
        UserLoginAndRegister userLoginAndRegister = new UserLoginAndRegister();
        userLoginAndRegister.register("111","111");
    }


    class UserLoginAndRegister{
        @JavascriptInterface
        public void login(String userName, String password){
            ApiClient.getInstance().createRetrofit(UserService.class)
                    .loginAction(userName,password)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new RxJavaInterceptor<UserBean>() {
                        @Override
                        public void failure(@Nullable String errorMsg) {

                        }

                        @Override
                        public void success(@Nullable UserBean data) {
                            Log.v("ljh","???????????????");
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, UndoActivity.class);
                            startActivity(intent);
                        }
                    });
        }

        @JavascriptInterface
        public void register(String userName,String password){
            ApiClient.getInstance().createRetrofit(UserService.class)
                    .registerAction(userName,password)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new RxJavaInterceptor<UserBean>() {
                        @Override
                        public void failure(@Nullable String errorMsg) {
                            Log.v("ljh","??????????????? errormsg" + errorMsg);
                        }

                        @Override
                        public void success(@Nullable UserBean data) {
                            Log.v("ljh","???????????????");
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, UndoActivity.class);
                            startActivity(intent);
                        }
                    });
        }
    }
    class MyWebviewClient extends WebViewClient{
        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            Log.v("ljh","shouldInterceptRequest1");
            return super.shouldInterceptRequest(view, request);
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            Log.v("ljh","shouldInterceptRequest2");
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.v("ljh","url???" + url);
            //??????url??????????????????????????????
            view.loadUrl(url);
            //?????????WebView???????????????????????????????????????
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.v("ljh","onPageStarted");
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.v("ljh","onPageFinished");
            super.onPageFinished(view, url);
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        //?????????????????? newProgress????????????0-100
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        //??????Activity???????????? ?????????????????????
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            //setTitle(title);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
//            if (message.startsWith("bridge://")) {
//                // ?????? // ????????? action ?????????????????????????????????
//                result.confirm("Yes!");
//            }
            Log.v("ljh","asasa");
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }
    }
}