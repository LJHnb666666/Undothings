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

package com.google.samples.apps.sunflower;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.navigation.Navigation;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.google.samples.apps.sunflower.utilities.TestUtils.chooser;
import static com.google.samples.apps.sunflower.utilities.TestUtils.testPlant;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Shawn Wang on 4/2/19.
 */
@RunWith(AndroidJUnit4.class)
public class PlantDetailFragmentTest {
    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule<>(GardenActivity.class);

    @Before
    public void jumpToPlantDetailFragment() {
        Activity act = this.activityTestRule.getActivity();
        act.runOnUiThread(() -> {
            Bundle bundle = new Bundle();
            bundle.putString("plantId", testPlant.getPlantId());
            Navigation.findNavController(act, R.id.garden_nav_fragment).navigate(R.id.plant_detail_fragment, bundle);
        });
    }

    @Test
    public void testShareTextIntent() {
        String shareText = activityTestRule.getActivity().getString(R.string.share_text_plant, testPlant.getName());

        Intents.init();
        onView(withId(R.id.action_share)).perform(click());
        Intents.intended(
                chooser(
                        allOf(
                                hasAction(Intent.ACTION_SEND),
                                hasType("text/plain"),
                                hasExtra(Intent.EXTRA_TEXT, shareText)
                        )
                )
        );
        Intents.release();

        // dismiss the Share Dialog
        InstrumentationRegistry.getInstrumentation().getUiAutomation().performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }
}
