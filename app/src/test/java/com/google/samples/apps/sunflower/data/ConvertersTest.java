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

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by Shawn Wang on 4/1/19.
 */
public class ConvertersTest {

    private final Calendar cal;

    public ConvertersTest() {
        this.cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1998);
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 4);
    }

    @Test
    public void calendarToDatestamp() {
        assertEquals(cal.getTimeInMillis(), new Converters().calendarToDatestamp(cal));
    }

    @Test
    public void datestampToCalendar() {
        assertEquals(new Converters().datestampToCalendar(cal.getTimeInMillis()), cal);
    }
}
