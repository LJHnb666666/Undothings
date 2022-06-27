package com.google.samples.apps.sunflower.viewmodels;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.google.common.base.Preconditions;
import com.google.samples.apps.sunflower.roombean.CommonUndoBean;
import com.google.samples.apps.sunflower.roombean.MyUndoListBean;
import com.google.samples.apps.sunflower.roombean.UndoBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * RecyclerView 的 item 布局文件  需要用到的ViewModel
 */
public class CommonUndoItemViewModel extends ViewModel {

    // ObservableField 感应数据变化   ---->  LiveData 感应数据变化
    // private LiveData

    public ObservableField<String> undoName;
    public ObservableField<String> createTime;
    public ObservableField<String> deadline;
    public ObservableField<Integer> degree;
    public ObservableField<String> imageUrl;

    public CommonUndoItemViewModel(@NonNull CommonUndoBean commonUndoBean) {
        final UndoBean undoBean = Preconditions.checkNotNull(commonUndoBean.getUndoBean());
        final MyUndoListBean myUndoListBean = commonUndoBean.getMyUndoListBeanList().get(0);
        final DateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.US);
        this.imageUrl = new ObservableField<>("https://picsum.photos/200/300"); // 给它值
        this.undoName = new ObservableField<>(undoBean.getName());
        this.createTime = new ObservableField<>(undoBean.getCreateTime());
        this.deadline = new ObservableField<>(undoBean.getDeadline() + " 终止！");
        this.degree = new ObservableField<>(undoBean.getDegree());
    }
}
