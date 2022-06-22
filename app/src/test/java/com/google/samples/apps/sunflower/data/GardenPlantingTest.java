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

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by Shawn Wang on 4/1/19.
 */
public class GardenPlantingTest {

    @Test
    public void testDefaultValues() {
        GardenPlanting gardenPlanting = new GardenPlanting("1", null, null);
        Calendar cal = Calendar.getInstance();
        assertYMD(cal, gardenPlanting.getPlantDate());
        assertYMD(cal, gardenPlanting.getLastWateringDate());
        assertEquals(0L, gardenPlanting.getGardenPlantingId());
    }

    // Only Year/Month/Day precision is needed for comparing GardenPlanting Calendar entries
    private static void assertYMD(Calendar expectedCal, Calendar actualCal) {
        assertThat(actualCal.get(Calendar.YEAR), CoreMatchers.equalTo(expectedCal.get(Calendar.YEAR)));
        assertThat(actualCal.get(Calendar.MONTH), CoreMatchers.equalTo(expectedCal.get(Calendar.MONTH)));
        assertThat(actualCal.get(Calendar.DAY_OF_MONTH), CoreMatchers.equalTo(expectedCal.get(Calendar.DAY_OF_MONTH)));
    }
}
