package com.google.samples.apps.sunflower.repository;

import androidx.lifecycle.LiveData;

//import com.google.samples.apps.sunflower.net.service.UserUndoService;
import com.google.samples.apps.sunflower.net.interceptor.RxJavaInterceptor;
import com.google.samples.apps.sunflower.net.service.UserUndoService;
import com.google.samples.apps.sunflower.net.serviceconfig.ApiClient;
import com.google.samples.apps.sunflower.dao.UndoDao;
import com.google.samples.apps.sunflower.roombean.UndoBean;
import com.google.samples.apps.sunflower.utilites.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class UndoRepository {
    private static UndoRepository instance;

    private UndoDao undoDao;
    private LiveData<ArrayList<UndoBean>> undoBeans;

    private UndoRepository(UndoDao undoDao) {
        this.undoDao = undoDao;
    }

    // 单例模式
    public static UndoRepository getInstance(UndoDao undoDao) {
        if (instance == null) {
            synchronized (UndoRepository.class) {
                if (instance == null) {
                    instance = new UndoRepository(undoDao);
                }
            }
        }
        return instance;
    }

    public LiveData<List<UndoBean>> getAllUndos() {
        return this.undoDao.getAllUndos();
    }
    public void getAllUserUndosFromNet(int uid,RxJavaInterceptor<ArrayList<com.google.samples.apps.sunflower.net.bean.UndoBean>> observer){

        ApiClient.getInstance().createRetrofit(UserUndoService.class)
                .getAllUndosByUid(uid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public LiveData<UndoBean> getUndoById(int undoId) {
        return this.undoDao.getUndoById(undoId);
    }

    public void insertAllUndosToLocal(List<UndoBean> undoBeans){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                undoDao.insertAll(undoBeans);
            }
        });

    }
}
