package com.google.samples.apps.sunflower.bean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Objects;

@Entity(tableName = "undo_table") // ROOM
public final class UndoBean {
    private static final int DEFAULT_WATERING_INTERVAL = 7;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "undo_id")
    private final String undoId;

    @NonNull
    private final String name;

    @NonNull
    private final String description; // 描述详情，很长很长的文字信息

    private final int growZoneNumber;

    private final int interval;

    @NonNull
    private final String imageUrl;

    public UndoBean(@NonNull String undoId, @NonNull String name, @NonNull String description,
                    int growZoneNumber, int interval, @NonNull String imageUrl) {
        this.undoId = undoId;
        this.name = name;
        this.description = description;
        this.growZoneNumber = growZoneNumber;
        this.interval = interval > 0 ? interval : DEFAULT_WATERING_INTERVAL;
        this.imageUrl = imageUrl;
    }


    public boolean shouldBeDone(Calendar since, Calendar lastDoDate) {
        lastDoDate.add(Calendar.DAY_OF_YEAR, interval);
        return since.compareTo(lastDoDate) > 0;
    }

    @NonNull
    public String getUndoId() {
        return undoId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public int getGrowZoneNumber() {
        return growZoneNumber;
    }

    public int getInterval() {
        return interval;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return "UndoBean{" +
                "undoId='" + undoId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", growZoneNumber=" + growZoneNumber +
                ", interval=" + interval +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof UndoBean
                && this.undoId.equals(((UndoBean) obj).undoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(undoId);
    }


    @Override
    protected Object clone() {
        return new UndoBean(undoId, name, description, growZoneNumber, interval, imageUrl);
    }
}
