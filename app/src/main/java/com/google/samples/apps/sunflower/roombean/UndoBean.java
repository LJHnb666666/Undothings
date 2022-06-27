package com.google.samples.apps.sunflower.roombean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "undo_table") // ROOM
public final class UndoBean {
    private static final int DEFAULT_WATERING_INTERVAL = 7;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "undo_id")
    private final int undoId;//对应net id

    @NonNull
    private final String name;//对应net name

    @NonNull
    private final String content; //对应net content


    private final String createTime;// 对应net createTime

//    @NonNull
//    private final String imageUrl;
    private final String deadline;

    private final int degree;

    private final int today;

    private final int uid;


    public UndoBean(int undoId, @NonNull String name, @NonNull String content, String createTime, String deadline, int degree, int today, int uid) {
        this.undoId = undoId;
        this.name = name;
        this.content = content;
        this.createTime = createTime;
        this.deadline = deadline;
        this.degree = degree;
        this.today = today;
        this.uid = uid;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof UndoBean
                && this.undoId == ((UndoBean) obj).undoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(undoId);
    }

    @Override
    public String toString() {
        return "UndoBean{" +
                "undoId=" + undoId +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", deadline='" + deadline + '\'' +
                ", degree=" + degree +
                ", today=" + today +
                ", uid=" + uid +
                '}';
    }

    public int getUndoId() {
        return undoId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getDeadline() {
        return deadline;
    }

    public int getDegree() {
        return degree;
    }

    public int getToday() {
        return today;
    }

    public int getUid() {
        return uid;
    }
}
