package com.google.samples.apps.sunflower.repository;

import androidx.lifecycle.LiveData;

import com.google.samples.apps.sunflower.bean.UndoBean;
import com.google.samples.apps.sunflower.dao.UndoDao;

import java.util.List;

public class UndoRepository {

    // 单例模式用的
    private static UndoRepository instance;

    // 仓库才能对 dao进行增删改查
    private UndoDao undoDao;

    // 构造函数 必须接收一个dao， 仓库才能对 dao进行增删改查
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

    public LiveData<UndoBean> getUndoById(String undoId) {
        return this.undoDao.getUndoById(undoId);
    }

    public LiveData<List<UndoBean>> getUndosByGrowzonenumber(int growZoneNumber) {
        return this.undoDao.getUndosByGrowzonenumber(growZoneNumber);
    }
}
