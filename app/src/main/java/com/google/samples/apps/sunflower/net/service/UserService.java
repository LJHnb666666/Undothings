package com.google.samples.apps.sunflower.net.service;

import com.google.samples.apps.sunflower.net.bean.UserBean;
import com.google.samples.apps.sunflower.net.module.ResponseModel;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {
    @POST("/login")
    @FormUrlEncoded
    public Observable<ResponseModel<UserBean>> loginAction(@Field("username")String username,
                                                           @Field("password")String password);

    @POST("/register")
    @FormUrlEncoded
    public Observable<ResponseModel<UserBean>> registerAction(@Field("username")String username,
                                                           @Field("password")String password);

}
