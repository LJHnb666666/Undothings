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

package com.google.samples.apps.sunflower.utilities;

import android.app.Activity;
import android.content.Intent;

import com.google.samples.apps.sunflower.data.GardenPlanting;
import com.google.samples.apps.sunflower.data.Plant;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Preconditions;
import androidx.test.espresso.intent.matcher.IntentMatchers;

/**
 * Created by Shawn Wang on 4/1/19.
 */
public class TestUtils {
    /**
     * [Plant] objects used for tests.
     */
    public static final List<Plant> testPlants;

    public static final Plant testPlant;

    /**
     * [Calendar] object used for tests.
     */
    public static final Calendar testCalendar = Calendar.getInstance();

    /**
     * [GardenPlanting] object used for tests.
     */
    public static final GardenPlanting testGardenPlanting;

    static {
        testPlants = Arrays.asList(
                new Plant("1", "Apple", "A red fruit", 1, -1, ""),
                new Plant("2", "B", "Description B", 2, -1, ""),
                new Plant("3", "C", "Description C", 3, -1, ""));

        testPlant = testPlants.get(0);

        testCalendar.set(Calendar.YEAR, 1998);
        testCalendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        testCalendar.set(Calendar.DAY_OF_MONTH, 4);

        testGardenPlanting = new GardenPlanting(testPlant.getPlantId(), testCalendar, testCalendar);
    }

    /**
     * Return the content description for the navigation button view in the toolbar.
     */
    public static String getToolbarNavigationContentDescription(Activity activity, int toolbarId) {
        return Preconditions.checkNotNull(((Toolbar) activity.findViewById(toolbarId))
                .getNavigationContentDescription()).toString();
    }

    public static Matcher<Intent> chooser(Matcher<Intent> matcher) {
        return Matchers.allOf(IntentMatchers.hasAction(Intent.ACTION_CHOOSER),
                IntentMatchers.hasExtra(Matchers.is(Intent.EXTRA_INTENT), matcher));
    }
}
