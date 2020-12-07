package com.ticketsystem.airlineticketsystem.utils;

import org.joda.time.Interval;

import java.util.Comparator;


public class IntervalStartComparator implements Comparator<Interval> {
    @Override
    public int compare(Interval x, Interval y) {
        return x.getStart().compareTo(y.getStart());
    }
}