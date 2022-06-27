package com.google.samples.apps.sunflower.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.google.samples.apps.sunflower.roombean.UndoBean;

import java.util.List;
@Dao
public interface UndoDao {
    @Query("SELECT * FROM undo_table ORDER BY name")
    LiveData<List<UndoBean>> getAllUndos();

    @Query("SELECT * FROM undo_table ORDER BY name")
    List<UndoBean> getAllUndosNoLivedata();

    @Query("SELECT * FROM undo_table WHERE undo_id = :undoId")
    LiveData<UndoBean> getUndoById(int undoId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UndoBean> undoBeanList);
}
