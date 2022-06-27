package com.google.samples.apps.sunflower.net.serviceconfig;

import com.google.samples.apps.sunflower.net.interceptor.OkHttpInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static ApiClient apiClient;
    private ApiClient(){}

    public static ApiClient getInstance(){
        if (apiClient == null){
            synchronized (ApiClient.class){
                if(apiClient == null){
                    apiClient = new ApiClient();
                }
            }
        }

        return apiClient;
    }

    public <T> T createRetrofit(Class<T> retrofitInterface){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(10000, TimeUnit.SECONDS)//读取超时时间
                .connectTimeout(10000, TimeUnit.SECONDS)//连接超时时间
                .writeTimeout(10000, TimeUnit.SECONDS)//写出超时时间
                .addInterceptor(new OkHttpInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.marsyr.top:8888/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(retrofitInterface);
    }
}
