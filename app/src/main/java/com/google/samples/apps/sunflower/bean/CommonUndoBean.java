package com.google.samples.apps.sunflower.bean;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CommonUndoBean {

    @Embedded
    private UndoBean undoBean; // 内嵌对象

    @Relation(parentColumn = "undo_id", entityColumn = "my_undo_id")
    private List<MyUndoListBean> myUndoListBeanList; // 我的花园Bean

    public List<MyUndoListBean> getMyUndoListBeanList() {
        return myUndoListBeanList;
    }

    public void setMyUndoListBeanList(List<MyUndoListBean> myUndoListBeanList) {
        this.myUndoListBeanList = myUndoListBeanList;
    }

    public UndoBean getUndoBean() {
        return undoBean;
    }

    public void setUndoBean(UndoBean undoBean) {
        this.undoBean = undoBean;
    }

    @Override
    public String toString() {
        return "CommonUndoBean{" +
                "undoBean=" + undoBean +
                ", myUndoListBeanList=" + myUndoListBeanList +
                '}';
    }
}
