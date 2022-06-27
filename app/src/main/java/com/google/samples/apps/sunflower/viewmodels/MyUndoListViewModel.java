package com.google.samples.apps.sunflower.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.samples.apps.sunflower.roombean.CommonUndoBean;
import com.google.samples.apps.sunflower.roombean.MyUndoListBean;
import com.google.samples.apps.sunflower.repository.MyUndoListRepository;

import java.util.ArrayList;
import java.util.List;


public class MyUndoListViewModel extends ViewModel {

    public LiveData<List<MyUndoListBean>> myUndoList;

    public LiveData<List<CommonUndoBean>> commonUndoList;

    /**
     * ViewModel只负责从仓库里面去获取数据
     * @param repository
     */
    public MyUndoListViewModel(@NonNull MyUndoListRepository repository) { // VM 就有 仓库了
        this.myUndoList = repository.getAllMyUndoList();

        this.commonUndoList = Transformations.map(repository.getAllCommonUndoList(), items -> {

            // 优化用的
            // 如果数据是空的，就移除掉空的数据
            List<CommonUndoBean> removeItems = new ArrayList<>();
            for (CommonUndoBean item: items) {
                if (item.getMyUndoListBeanList().isEmpty()) {
                    removeItems.add(item);
                }
            }
            items.removeAll(removeItems);
            return items;
        });

    }
}
