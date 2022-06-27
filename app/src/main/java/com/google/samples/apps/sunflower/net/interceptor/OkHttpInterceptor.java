package com.google.samples.apps.sunflower.net.interceptor;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import kotlin.Pair;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Response;

public class OkHttpInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Headers headers = chain.request().headers();
            Log.v("ljh","okhttp拦截器里面 ");
        for(Pair<? extends String, ? extends String> header: headers){
            Log.v("ljh","okhttp拦截器里面 " + header.toString());

        }
        return null;
    }
}
