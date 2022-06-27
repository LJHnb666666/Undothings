package com.google.samples.apps.sunflower.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.samples.apps.sunflower.net.interceptor.RxJavaInterceptor;
import com.google.samples.apps.sunflower.roombean.UndoBean;
import com.google.samples.apps.sunflower.repository.UndoRepository;

import java.util.ArrayList;
import java.util.List;


public class AllUndoListViewModel extends ViewModel {
    private static final int NO_GROW_ZONE = -1; // 默认

    // ViewModel只负责操作仓库，向仓库拿数据
    private UndoRepository undoRepository;

    private MutableLiveData<Integer> growZoneNumber;

    public MutableLiveData<List<UndoBean>> allUndos;

    public AllUndoListViewModel(@NonNull UndoRepository undoRepository) {
        super();
        this.undoRepository = undoRepository;
        this.growZoneNumber = new MutableLiveData<>(-1);

        allUndos = new MutableLiveData<>();
    }

    public void getAllUserUndosFromNet(){
        undoRepository.getAllUserUndosFromNet(1,new RxJavaInterceptor<ArrayList<com.google.samples.apps.sunflower.net.bean.UndoBean>>(){
            @Override
            public void success(@Nullable ArrayList<com.google.samples.apps.sunflower.net.bean.UndoBean> data) {
                Log.v("ljh","请求用户待办数据成功"+ data);
                List<UndoBean> undoBeans = new ArrayList<>();
                for(com.google.samples.apps.sunflower.net.bean.UndoBean oneData: data){
                    UndoBean undoBean = new UndoBean(oneData.getId(), oneData.getName(), oneData.getContent(), oneData.getCreateTime(), oneData.getDeadline(), oneData.getDegree(), oneData.getToday(), oneData.getUid());
                    undoBeans.add(undoBean);
                }

                allUndos.setValue(undoBeans);
                undoRepository.insertAllUndosToLocal(undoBeans);

            }

            @Override
            public void failure(@Nullable String errorMsg) {

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
