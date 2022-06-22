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


import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Shawn Wang on 4/1/19.
 */
public class PlantTest {

    private Plant plant;

    @Before
    public void setUp() {
        this.plant = new Plant("1", "Tomato", "A red vegatable", 1, 2, "");
    }

    @Test
    public void testDefaultValues() {
        Plant defaultPlant = new Plant("2", "Apple", "Description", 1, -1, "");
        assert 7 == defaultPlant.getWateringInterval();
        assert "" == defaultPlant.getImageUrl();
    }

    @Test
    public void testShouldBeWatered() {
        Calendar now = Calendar.getInstance();

        // Test for lastWateringDate is today.
        assertFalse(plant.shouldBeWatered(now, getNewCalendar(now, Calendar.DAY_OF_YEAR, -0)));

        // Test for lastWateringDate is yesterday.
        assertFalse(plant.shouldBeWatered(now, getNewCalendar(now, Calendar.DAY_OF_YEAR, -1)));

        // Test for lastWateringDate is the day before yesterday.
        assertFalse(plant.shouldBeWatered(now, getNewCalendar(now, Calendar.DAY_OF_YEAR, -2)));

        // Test for lastWateringDate is some days ago, three days ago, four days ago etc.
        assertTrue(plant.shouldBeWatered(now, getNewCalendar(now, Calendar.DAY_OF_YEAR, -3)));
    }

    private static Calendar getNewCalendar(Calendar cal, int field, int amount) {
        Calendar newCal = Calendar.getInstance();
        newCal.setTime(cal.getTime());
        newCal.add(field, amount);
        return newCal;
    }
}
