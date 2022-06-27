package com.google.samples.apps.sunflower.net.service;

import com.google.samples.apps.sunflower.net.bean.UndoBean;
import com.google.samples.apps.sunflower.net.module.ResponseModel;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserUndoService {
    @GET("itemList/{uid}")
    Observable<ResponseModel<ArrayList<UndoBean>>> getAllUndosByUid(@Path("uid")int uid);
}
