package com.google.samples.apps.sunflower.data;

import androidx.room.TypeConverter;

import java.util.Calendar;

/**
 * 此转换器，为了解决：
 * Cannot figure out how to save this field into database. You can consider adding a type
 */
public class Converters {

    @TypeConverter
    public long calendarToDatestamp(Calendar calendar) {
        return calendar.getTimeInMillis();
    }

    @TypeConverter
    public Calendar datestampToCalendar(long value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar;
    }
}
