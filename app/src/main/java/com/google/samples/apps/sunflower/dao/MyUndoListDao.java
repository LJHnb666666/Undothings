package com.google.samples.apps.sunflower.dao;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.google.samples.apps.sunflower.bean.CommonUndoBean;
import com.google.samples.apps.sunflower.bean.MyUndoListBean;

import java.util.List;


@Dao
public interface MyUndoListDao {
    @Query("SELECT * FROM my_undo_list_table")
    LiveData<List<MyUndoListBean>> getAllMyUndoList();

    @Query("SELECT * FROM my_undo_list_table WHERE my_undo_id = :myUndoId")
    LiveData<MyUndoListBean> getUndoListByUndoId(@NonNull String myUndoId);

    @Transaction
    @Query("SELECT * FROM undo_table")
    LiveData<List<CommonUndoBean>> getCommonUndoBean();

    @Insert
    long insertOneMyUndo(@NonNull MyUndoListBean myUndoListBean);

    @Delete
    void deleteOneMyUndo(@NonNull MyUndoListBean myUndoListBean);
}
