package com.google.samples.apps.sunflower.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.samples.apps.sunflower.bean.UndoBean;
import com.google.samples.apps.sunflower.repository.UndoRepository;

import java.util.List;


public class AllUndoListViewModel extends ViewModel {

    private static final int NO_GROW_ZONE = -1; // 默认

    // ViewModel只负责操作仓库，向仓库拿数据
    private UndoRepository undoRepository;

    // 此LiveData是为了 （点击右上角按钮时 随机刷新数据的效果用的）
    private MutableLiveData<Integer> growZoneNumber;

    // 此LiveData 才是真正的 植物目录 的数据
    public LiveData<List<UndoBean>> allUndos;

    public AllUndoListViewModel(@NonNull UndoRepository undoRepository) {
        super();
        this.undoRepository = undoRepository;
        this.growZoneNumber = new MutableLiveData<>(-1);


        allUndos = Transformations.switchMap(growZoneNumber, new Function<Integer, LiveData<List<UndoBean>>>() {
            @Override
            public LiveData<List<UndoBean>> apply(Integer it) {
                if (it == NO_GROW_ZONE) { // -1 默认 正常查询
                    Log.d("item", "apply: 如果是 -1 就全部查询");
                    return AllUndoListViewModel.this.undoRepository.getAllUndos();
                } else {
                    Log.d("item", "apply: 否则就指定查询 并排序   【在 undos.json 里面查询： growZoneNumber\": 9  就明白了】");
                    return AllUndoListViewModel.this.undoRepository.getUndosByGrowzonenumber(it);
                }
            }
        });
    }

    // 是不是等于-1(默认)  true/false
    // （点击右上角按钮时 随机刷新数据的效果用的） 判断标记
    public boolean isFiltered() {
        return this.growZoneNumber.getValue() != NO_GROW_ZONE;  // NO_GROW_ZONE == -1
    }

    // （点击右上角按钮时 随机刷新数据的效果用的）
    public void setGrowZoneNumber(int num) {
        this.growZoneNumber.setValue(num);   // 修改成9  查询为9的数据
    }

    // （点击右上角按钮时 随机刷新数据的效果用的）
    public void cleanGrowZoneNumber() {
        this.growZoneNumber.setValue(NO_GROW_ZONE); // 修改成-1 默认 正常查询
    }
}
