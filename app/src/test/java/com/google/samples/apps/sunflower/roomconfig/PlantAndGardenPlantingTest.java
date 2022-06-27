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

import com.google.samples.apps.sunflower.roombean.CommonUndoBean;

import org.junit.Test;

/**
 * Created by Shawn Wang on 4/1/19.
 */
public class PlantAndGardenPlantingTest {

    @Test
    public void testDefaultValues() {
        CommonUndoBean p = new CommonUndoBean();
        assert p.getGardenPlantings().isEmpty();
    }
}