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
    private String undoId; // 植物ID
    private UndoRepository undoRepository; // 植物目录 仓库
    private MyUndoListRepository myUndoListRepository; // 我的花园 仓库

    public UndoDetailViewModelFactory(UndoRepository undoRepository,
                                      MyUndoListRepository myUndoListRepository,
                                      String undoId) {
        this.undoId = undoId;
        this.undoRepository = undoRepository;
        this.myUndoListRepository = myUndoListRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        // 参数一：植物目录 仓库
        // 参数二：我的花园 仓库
        // 参数三：植物ID
        return (T) new UndoDetailViewModel(undoRepository, myUndoListRepository, undoId);
    }
}
