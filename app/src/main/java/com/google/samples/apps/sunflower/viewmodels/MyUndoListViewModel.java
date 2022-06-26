package com.google.samples.apps.sunflower.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.samples.apps.sunflower.bean.CommonUndoBean;
import com.google.samples.apps.sunflower.bean.MyUndoListBean;
import com.google.samples.apps.sunflower.repository.MyUndoListRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的花园 List 列表的 ViewModel
 */
public class MyUndoListViewModel extends ViewModel {

    // 我的花园 的 是否 展示数据 不展示数据 的标记改变
    public LiveData<List<MyUndoListBean>> myUndoList;

    // 我的花园+植物 的 RecycleView 的 列表数据
    public LiveData<List<CommonUndoBean>> commonUndoList;

    /**
     * ViewModel只负责从仓库里面去获取数据
     * @param repository
     */
    public MyUndoListViewModel(@NonNull MyUndoListRepository repository) { // VM 就有 仓库了
        // Room的dao-->查询 我的花园 数据
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
