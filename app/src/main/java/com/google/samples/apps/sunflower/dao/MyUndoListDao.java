package com.google.samples.apps.sunflower.dao;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.google.samples.apps.sunflower.roombean.CommonUndoBean;
import com.google.samples.apps.sunflower.roombean.MyUndoListBean;

import java.util.List;


@Dao
public interface MyUndoListDao {
    @Query("SELECT * FROM my_undo_list_table")
    LiveData<List<MyUndoListBean>> getAllMyUndoList();

    @Query("SELECT * FROM my_undo_list_table")
    List<MyUndoListBean> getAllMyUndoListNoLivedata();

    @Query("SELECT * FROM my_undo_list_table WHERE my_undo_id = :myUndoId")
    LiveData<MyUndoListBean> getUndoListByUndoId(@NonNull int myUndoId);

    @Query("SELECT * FROM my_undo_list_table WHERE my_undo_id = :myUndoId")
    MyUndoListBean getUndoListByIdNoLivedata(@NonNull int myUndoId);

    @Transaction
    @Query("SELECT * FROM undo_table ORDER BY degree")
    LiveData<List<CommonUndoBean>> getCommonUndoBean();

    @Transaction
    @Query("SELECT * FROM undo_table WHERE undo_id = :undoId")
    LiveData<CommonUndoBean> getCommonUndoBeanById(@NonNull int undoId);

    @Insert
    long insertOneMyUndo(@NonNull MyUndoListBean myUndoListBean);

    @Delete
    void deleteOneMyUndo(@NonNull MyUndoListBean myUndoListBean);
}
