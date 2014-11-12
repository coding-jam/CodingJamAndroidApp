package it.cosenonjaviste.utils;

import android.text.format.DateUtils;

import java.util.Date;

public class DateFormatter {
    public static CharSequence formatDate(Date date) {
        return DateUtils.getRelativeTimeSpanString(date.getTime(), System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS, DateUtils.FORMAT_SHOW_YEAR);
    }
}
