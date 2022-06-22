/*
 * Copyright 2019 Google LLC
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

package com.google.samples.apps.sunflower.worker;

import android.content.Context;

import com.google.samples.apps.sunflower.workers.SeedDatabaseWorker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.ExecutionException;

import androidx.test.core.app.ApplicationProvider;
import androidx.work.ListenableWorker;
import androidx.work.testing.TestListenableWorkerBuilder;

/**
 * Created by Shawn Wang on 5/6/19.
 */
@RunWith(JUnit4.class)
public class SeedDatabaseWorkerTest {
    private Context context;

    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void testRefreshMainDataWOrk() {
        // Get the ListenableWorker
        SeedDatabaseWorker worker = TestListenableWorkerBuilder.from(context, SeedDatabaseWorker.class).build();

        // Start the work synchronously
        try {
            ListenableWorker.Result result = worker.startWork().get();
            assert(result instanceof ListenableWorker.Result.Success);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
