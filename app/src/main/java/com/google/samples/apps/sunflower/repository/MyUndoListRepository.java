package com.google.samples.apps.sunflower.repository;

import androidx.lifecycle.LiveData;

import com.google.samples.apps.sunflower.bean.CommonUndoBean;
import com.google.samples.apps.sunflower.bean.MyUndoListBean;
import com.google.samples.apps.sunflower.dao.MyUndoListDao;
import com.google.samples.apps.sunflower.utilites.AppExecutors;

import java.util.List;


public class MyUndoListRepository {

    // 此类 单例模式 成员
    private static MyUndoListRepository instance;

    private MyUndoListDao myUndoListDao;

    // 构造函数 接收 DAO
    private MyUndoListRepository(MyUndoListDao gardenPlantingDao) {
        this.myUndoListDao = gardenPlantingDao;
    }

    // 单例模式
    public static MyUndoListRepository getInstance(MyUndoListDao gardenPlantingDao) {
        if (instance == null) {
            synchronized (MyUndoListRepository.class) {
                if (instance == null) {
                    instance = new MyUndoListRepository(gardenPlantingDao);
                }
            }
        }
        return instance;
    }


    public void createMyUndo(String plantId) {
        AppExecutors.getInstance().diskIO().execute(() ->
                // 植物 insert 插入进去
                myUndoListDao.insertOneMyUndo(new MyUndoListBean(plantId, null, null)));
    }

    public void removeOneMyUndo(MyUndoListBean gardenPlanting) {
        AppExecutors.getInstance().diskIO().execute(() ->
                myUndoListDao.deleteOneMyUndo(gardenPlanting));
    }

    public LiveData<MyUndoListBean> getUndoListByUndoId(String undoId) {
        return myUndoListDao.getUndoListByUndoId(undoId);
    }


    public LiveData<List<MyUndoListBean>> getAllMyUndoList() {
        return myUndoListDao.getAllMyUndoList();
    }

    public LiveData<List<CommonUndoBean>> getAllCommonUndoList() {
        return myUndoListDao.getCommonUndoBean();
    }

}
