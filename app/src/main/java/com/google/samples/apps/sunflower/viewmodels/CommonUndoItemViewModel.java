package com.google.samples.apps.sunflower.viewmodels;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.google.common.base.Preconditions;
import com.google.samples.apps.sunflower.bean.CommonUndoBean;
import com.google.samples.apps.sunflower.bean.MyUndoListBean;
import com.google.samples.apps.sunflower.bean.UndoBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * RecyclerView 的 item 布局文件  需要用到的ViewModel
 */
public class CommonUndoItemViewModel extends ViewModel {

    // ObservableField 感应数据变化   ---->  LiveData 感应数据变化
    // private LiveData

    public ObservableField<String> waterDateString;
    public ObservableInt interval;
    public ObservableField<String> imageUrl;
    public ObservableField<String> undoName;
    public ObservableField<String> lastDoDate;

    public CommonUndoItemViewModel(@NonNull CommonUndoBean commonUndoBean) {
        final UndoBean undoBean = Preconditions.checkNotNull(commonUndoBean.getUndoBean());
        final MyUndoListBean myUndoListBean = commonUndoBean.getMyUndoListBeanList().get(0);
        final DateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.US);

        this.waterDateString = new ObservableField<>(dateFormat.format(myUndoListBean.getLastDoDate().getTime()));
        this.interval = new ObservableInt(undoBean.getInterval());
        this.imageUrl = new ObservableField<>(undoBean.getImageUrl()); // 给它值
        this.undoName = new ObservableField<>(undoBean.getName());
        this.lastDoDate = new ObservableField<>(dateFormat.format(myUndoListBean.getUndoDate().getTime()));
    }
}
