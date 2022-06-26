package com.google.samples.apps.sunflower.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.samples.apps.sunflower.repository.MyUndoListRepository;
import com.google.samples.apps.sunflower.viewmodels.MyUndoListViewModel;

public class CommonUndoItemViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private MyUndoListRepository repository;

    public CommonUndoItemViewModelFactory(MyUndoListRepository repository) {
        super();
        this.repository = repository;
    }

    // 传教ViewModel
    // 以前是ViewModelProvider反射传教VM ,   现在我们自己控制(主控权) 走我们自己的create函数
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MyUndoListViewModel(repository);
    }
}
