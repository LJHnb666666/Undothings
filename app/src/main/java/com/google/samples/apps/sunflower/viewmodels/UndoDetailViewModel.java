package com.google.samples.apps.sunflower.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.samples.apps.sunflower.bean.MyUndoListBean;
import com.google.samples.apps.sunflower.repository.MyUndoListRepository;
import com.google.samples.apps.sunflower.bean.UndoBean;
import com.google.samples.apps.sunflower.repository.UndoRepository;
import com.google.samples.apps.sunflower.utilites.AppExecutors;

public class UndoDetailViewModel extends ViewModel {
    private String undoId;

    public LiveData<UndoBean> undo;

    public UndoDetailViewModel(UndoRepository undoRepository, MyUndoListRepository myUndoListRepository, String undoId) {
        super();
        this.myUndoListRepository = myUndoListRepository;
        this.undoId = undoId;

        LiveData<MyUndoListBean> undoListByUndoId = myUndoListRepository.getUndoListByUndoId(undoId);
        this.isAdd = Transformations.map(undoListByUndoId, it -> it != null); // // + 号 的 显示 和 隐藏
        this.undo = undoRepository.getUndoById(undoId);
    }


    private MyUndoListRepository myUndoListRepository;

    public void addMyUndo() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                myUndoListRepository.createMyUndo(undoId);
            }
        });
    }

    // + 号 的 显示 和 隐藏
    private LiveData<Boolean> isAdd; // 判断是不是有植物信息，如果有 就要显示 （+号按钮）     否则      就不显示（+号按钮）

    public LiveData<Boolean> getIsAdd() {
        return isAdd;
    }
}
