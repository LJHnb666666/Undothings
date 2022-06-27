package com.google.samples.apps.sunflower.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.samples.apps.sunflower.repository.MyUndoListRepository;
import com.google.samples.apps.sunflower.repository.UndoRepository;
import com.google.samples.apps.sunflower.viewmodels.UndoDetailViewModel;

/**
 * (植物详情的ViewModel) 的 创建工厂
 */
public class UndoDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private int undoId; // 植物ID
    private UndoRepository undoRepository; // 植物目录 仓库
    private MyUndoListRepository myUndoListRepository; // 我的花园 仓库

    public UndoDetailViewModelFactory(UndoRepository undoRepository,
                                      MyUndoListRepository myUndoListRepository,
                                      int undoId) {
        this.undoId = undoId;
        this.undoRepository = undoRepository;
        this.myUndoListRepository = myUndoListRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UndoDetailViewModel(undoRepository, myUndoListRepository, undoId);
    }
}
