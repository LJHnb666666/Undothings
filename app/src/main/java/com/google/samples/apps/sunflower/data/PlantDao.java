package com.google.samples.apps.sunflower.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * 这个是 植物 的增删改查-DAO
 *
 * The Data Access Object for the Plant class.
 */
@Dao
public interface PlantDao {
    @Query("SELECT * FROM plants ORDER BY name")
    LiveData<List<Plant>> getPlants();

    // 在 plants.json 里面查询： growZoneNumber": 9  就明白了
    @Query("SELECT * FROM plants WHERE growZoneNumber = :growZoneNumber ORDER BY name")
    LiveData<List<Plant>> getPlantsWithGrowZoneNumber(int growZoneNumber);

    @Query("SELECT * FROM plants WHERE id = :plantId")
    LiveData<Plant> getPlant(String plantId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Plant> plants);
}
