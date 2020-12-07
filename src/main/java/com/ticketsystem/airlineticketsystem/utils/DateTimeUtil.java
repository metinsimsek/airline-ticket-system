package com.ticketsystem.airlineticketsystem.utils;

import org.joda.time.Interval;

import java.util.List;


public class DateTimeUtil {


    public static boolean isOverlapping(List<Interval> sortedIntervals) {
        for (int i = 0, n = sortedIntervals.size(); i < n - 1; i++) {
            if (sortedIntervals.get(i).overlaps(sortedIntervals.get(i + 1))) {
                return true;
            }
        }

        return false;
    }
}