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

package com.google.samples.apps.sunflower.roomconfig;

import android.content.Context;

import com.google.samples.apps.sunflower.roombean.CommonUndoBean;
import com.google.samples.apps.sunflower.roombean.MyUndoListBean;
import com.google.samples.apps.sunflower.dao.MyUndoListDao;
import com.google.samples.apps.sunflower.utilities.LiveDataTestUtil;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static com.google.samples.apps.sunflower.utilities.TestUtils.testCalendar;
import static com.google.samples.apps.sunflower.utilities.TestUtils.testGardenPlanting;
import static com.google.samples.apps.sunflower.utilities.TestUtils.testPlant;
import static com.google.samples.apps.sunflower.utilities.TestUtils.testPlants;
import static org.junit.Assert.assertNull;

/**
 * Created by Shawn Wang on 3/22/19.
 */
@RunWith(AndroidJUnit4.class)
public class GardenPlantingDaoTest {
    private AppDatabase database;
    private MyUndoListDao gardenPlantingDao;
    private long testGardenPlantingId = 0;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        this.database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        this.gardenPlantingDao = database.getMyUndoListDao();

        this.database.getUndoDao().insertAll(testPlants);
        this.testGardenPlantingId = this.gardenPlantingDao.insertOneMyUndo(testGardenPlanting);
    }

    @After
    public void closeDb() {
        this.database.close();
    }

    @Test
    public void testGetGardenPlantings() throws InterruptedException {
        MyUndoListBean gardenPlanting2 = new MyUndoListBean(testPlants.get(1).getUndoId(), testCalendar, testCalendar);
        gardenPlanting2.setMyUndoListId(2);
        this.gardenPlantingDao.insertOneMyUndo(gardenPlanting2);
        assertThat(LiveDataTestUtil.getValue(this.gardenPlantingDao.getAllMyUndoList()).size(), Matchers.equalTo(2));
    }

    @Test
    public void testGetGardenPlanting() throws InterruptedException {
        assertThat(LiveDataTestUtil.getValue(this.gardenPlantingDao.getGardenPlanting(this.testGardenPlantingId)),
                Matchers.equalTo(testGardenPlanting));
    }

    @Test
    public void testDeleteGardenPlanting() throws InterruptedException {
        MyUndoListBean gardenPlanting2 = new MyUndoListBean(testPlants.get(1).getUndoId(), testCalendar, testCalendar);
        gardenPlanting2.setMyUndoListId(2);
        this.gardenPlantingDao.insertOneMyUndo(gardenPlanting2);
        assertThat(LiveDataTestUtil.getValue(gardenPlantingDao.getAllMyUndoList()).size(), Matchers.equalTo(2));
        this.gardenPlantingDao.deleteOneMyUndo(gardenPlanting2);
        assertThat(LiveDataTestUtil.getValue(gardenPlantingDao.getAllMyUndoList()).size(), Matchers.equalTo(1));
    }


    @Test
    public void testGetGardenPlantingForPlant_notFound() throws InterruptedException {
        assertNull(LiveDataTestUtil.getValue(gardenPlantingDao.getUndoListByUndoId(testPlants.get(2).getUndoId())));
    }

    @Test
    public void testGetPlantAndGardenPlantings() throws InterruptedException {
        List<CommonUndoBean> plantAndGardenPlantings = LiveDataTestUtil.getValue(gardenPlantingDao.getCommonUndoBean());
        assertThat(plantAndGardenPlantings.size(), Matchers.equalTo(3));

        /**
         * Only the [testPlant] has been planted, and thus has an associated [GardenPlanting]
         */
        assertThat(plantAndGardenPlantings.get(0).getUndoBean(), Matchers.equalTo(testPlant));
        assertThat(plantAndGardenPlantings.get(0).getGardenPlantings().size(), Matchers.equalTo(1));
        assertThat(plantAndGardenPlantings.get(0).getGardenPlantings().get(0), Matchers.equalTo(testGardenPlanting));

        // The other plants in the database have not been planted and thus have no GardenPlantings
        assertThat(plantAndGardenPlantings.get(1).getGardenPlantings().size(), Matchers.equalTo(0));
        assertThat(plantAndGardenPlantings.get(2).getGardenPlantings().size(), Matchers.equalTo(0));
    }
}
