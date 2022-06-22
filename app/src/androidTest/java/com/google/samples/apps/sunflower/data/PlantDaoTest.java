/*
 * Copyright 2019 Shawn Wang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.data;

import android.content.Context;

import com.google.samples.apps.sunflower.utilities.LiveDataTestUtil;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.assertThat;

/**
 * Created by Shawn Wang on 4/1/19.
 */
@RunWith(AndroidJUnit4.class)
public class PlantDaoTest {
    private AppDatabase database;
    private PlantDao plantDao;
    private Plant plantA = new Plant("1", "A", "", 1, 1, "");
    private Plant plantB = new Plant("2", "B", "", 1, 1, "");
    private Plant plantC = new Plant("3", "C", "", 2, 2, "");

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        this.database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        this.plantDao = this.database.getPlantDao();

        // Insert plants in non-alphabetical order to test that results are sorted by name
        this.plantDao.insertAll(Arrays.asList(plantA, plantB, plantC));
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void testGetPlants() throws InterruptedException {
        List<Plant> plantList = LiveDataTestUtil.getValue(this.plantDao.getPlants());

        // Ensure plant list is sorted by name
        assertThat(plantList.get(0), Matchers.equalTo(plantA));
        assertThat(plantList.get(1), Matchers.equalTo(plantB));
        assertThat(plantList.get(2), Matchers.equalTo(plantC));
    }

    @Test
    public void testGetPlantsWithGrowZoneNumber() throws InterruptedException {
        List<Plant> plantList = LiveDataTestUtil.getValue(plantDao.getPlantsWithGrowZoneNumber(1));
        assertThat(plantList.size(), Matchers.equalTo(2));
        assertThat(LiveDataTestUtil.getValue(plantDao.getPlantsWithGrowZoneNumber(2)).size(), Matchers.equalTo(1));
        assertThat(LiveDataTestUtil.getValue(plantDao.getPlantsWithGrowZoneNumber(3)).size(), Matchers.equalTo(0));

        // Ensure plant list is sorted by name
        assertThat(plantList.get(0), Matchers.equalTo(plantA));
        assertThat(plantList.get(1), Matchers.equalTo(plantB));
    }

    @Test
    public void testGetPlant() throws InterruptedException {
        assertThat(LiveDataTestUtil.getValue(plantDao.getPlant(plantA.getPlantId())), Matchers.equalTo(plantA));
    }

}
