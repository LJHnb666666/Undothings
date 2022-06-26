package com.google.samples.apps.sunflower.bean;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Objects;

@Entity(tableName = "my_undo_list_table",
        foreignKeys = {@ForeignKey(entity = UndoBean.class, parentColumns = {"undo_id"}, childColumns = {"my_undo_id"})},
        indices = {@Index("my_undo_id")})
public final class MyUndoListBean {
    @ColumnInfo(name = "my_undo_id") private final String undoId;

    @ColumnInfo(name = "undo_date") private final Calendar undoDate;

    @ColumnInfo(name = "last_do_date") private final Calendar lastDoDate;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long myUndoListId = 0L;

    public MyUndoListBean(String undoId, Calendar undoDate, Calendar lastDoDate) {
        this.undoId = undoId;
        this.undoDate = undoDate == null ? Calendar.getInstance() : undoDate;
        this.lastDoDate = lastDoDate == null ? Calendar.getInstance() : lastDoDate;
    }

    public String getUndoId() {
        return undoId;
    }

    public Calendar getUndoDate() {
        return undoDate;
    }

    public Calendar getLastDoDate() {
        return lastDoDate;
    }

    public long getMyUndoListId() {
        return myUndoListId;
    }

    public void setMyUndoListId(long myUndoListId) {
        this.myUndoListId = myUndoListId;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        MyUndoListBean target = (MyUndoListBean) obj;

        return this.undoId.equals(target.undoId)
                && this.undoDate.equals(this.undoDate)
                && this.lastDoDate.equals(this.lastDoDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myUndoListId);
    }

    @Override
    protected Object clone() {
        return new MyUndoListBean(undoId, undoDate, lastDoDate);
    }
}
