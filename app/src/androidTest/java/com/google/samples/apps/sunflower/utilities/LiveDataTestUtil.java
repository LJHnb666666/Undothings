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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;

/**
 * Helper method for testing LiveData objects, from
 * https://github.com/googlesamples/android-architecture-components.
 *
 * Get the value from a LiveData object. We're waiting for LiveData to emit, for 2 seconds.
 * Once we got a notification via onChanged, we stop observing.
 */
@SuppressWarnings("unchecked")
public class LiveDataTestUtil {
    public static <T> T getValue(LiveData<T> liveData) throws InterruptedException {
        Object[] data = new Object[]{1};
        CountDownLatch latch = new CountDownLatch(1);
        liveData.observeForever(t -> {
            data[0] = t;
            latch.countDown();
        });
        latch.await(2, TimeUnit.SECONDS);

        return (T) data[0];
    }
}
