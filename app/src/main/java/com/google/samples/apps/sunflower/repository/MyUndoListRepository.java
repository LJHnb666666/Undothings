package com.google.samples.apps.sunflower.repository;

import androidx.lifecycle.LiveData;

import com.google.samples.apps.sunflower.roombean.CommonUndoBean;
import com.google.samples.apps.sunflower.roombean.MyUndoListBean;
import com.google.samples.apps.sunflower.dao.MyUndoListDao;

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


    public void createMyUndo(int undoId) {
//        AppExecutors.getInstance().diskIO().execute(() ->);
        myUndoListDao.insertOneMyUndo(new MyUndoListBean(undoId, null, null));

    }

    public void removeOneMyUndo(int undoId) {
//        AppExecutors.getInstance().diskIO().execute(() ->);
        myUndoListDao.deleteOneMyUndo(myUndoListDao.getUndoListByIdNoLivedata(undoId));
    }

    public LiveData<MyUndoListBean> getUndoListByUndoId(int undoId) {
        return myUndoListDao.getUndoListByUndoId(undoId);
    }


    public LiveData<List<MyUndoListBean>> getAllMyUndoList() {
        return myUndoListDao.getAllMyUndoList();
    }

    public LiveData<List<CommonUndoBean>> getAllCommonUndoList() {
        return myUndoListDao.getCommonUndoBean();
    }

}
