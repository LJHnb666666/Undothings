package com.google.samples.apps.sunflower.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.samples.apps.sunflower.repository.UndoRepository;
import com.google.samples.apps.sunflower.viewmodels.AllUndoListViewModel;

public class AllUndoListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    // 仓库数据
    private UndoRepository repository;

    public AllUndoListViewModelFactory(@NonNull UndoRepository repository) {
        super();
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AllUndoListViewModel(repository);
    }

}
