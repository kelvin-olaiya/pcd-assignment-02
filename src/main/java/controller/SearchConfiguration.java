package controller;

import model.report.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchConfiguration {
    private final List<Interval> intervals = new ArrayList<>();

    public SearchConfiguration(int numIntervals, int maxLines) {
        int intervalSize = maxLines / (numIntervals - 1);
        int i;
        for (i = 0; i < maxLines; i+=intervalSize) {
            intervals.add(new Interval(i, i + intervalSize));
        }
        intervals.add(new Interval(i, Integer.MAX_VALUE));
    }

    public Interval getFileInterval(long lines) {
        return intervals.stream().filter(interval -> interval.accept(lines)).findFirst().get();
    }

    public List<Interval> getIntervals() {
        return Collections.unmodifiableList(intervals);
    }
}
