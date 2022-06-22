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

import android.os.RemoteException;
import android.view.Gravity;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.action.ViewActions;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.contrib.DrawerMatchers.isOpen;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.google.samples.apps.sunflower.utilities.TestUtils.getToolbarNavigationContentDescription;
import static org.junit.Assert.assertFalse;

/**
 * Created by Shawn Wang on 4/2/19.
 */
public class GardenActivityTest {
    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule<>(GardenActivity.class);

    @Test
    public void clickOnAndroidHomeIcon_OpensAndClosesNavigation() {
        // Check that drawer is closed at startup
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START)));

        clickOnHomeIconToOpenNavigationDrawer();
        checkDrawerIsOpen();
    }

    @Test
    public void onRotate_NavigationStaysOpen() throws RemoteException {
        clickOnAndroidHomeIcon_OpensAndClosesNavigation();

        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        // Rotate device to landscape
        device.setOrientationLeft();
        checkDrawerIsOpen();

        // Rotate device back to portrait
        device.setOrientationRight();
        checkDrawerIsOpen();
    }

    @Test
    public void clickOnPlantListDrawerMenuItem_StartsPlantListActivity() {
        clickOnHomeIconToOpenNavigationDrawer();
        onView(isRoot()).perform(ViewActions.pressBack());
        checkDrawerIsNotOpen();
        assertFalse(this.activityTestRule.getActivity().isFinishing());
        assertFalse(this.activityTestRule.getActivity().isDestroyed());
    }


    private void clickOnHomeIconToOpenNavigationDrawer() {
        onView(withContentDescription(getToolbarNavigationContentDescription(
                this.activityTestRule.getActivity(), R.id.toolbar))).perform(click());
    }

    private void checkDrawerIsOpen() {
        onView(withId(R.id.drawer_layout)).check(matches(isOpen(Gravity.START)));
    }

    private void checkDrawerIsNotOpen() {
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START)));
    }

}
