package controller;

import model.report.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchConfiguration {
    private final List<Interval> intervals = new ArrayList<>();
    private final int numLongestFiles;

    public SearchConfiguration(int numIntervals, int maxLines) {
        this(numIntervals, maxLines, 10);
    }

    public SearchConfiguration(int numIntervals, int maxLines, int numLongestFiles) {
        int intervalSize = maxLines / (numIntervals - 1);
        int i;
        for (i = 0; i < maxLines; i+=intervalSize) {
            intervals.add(new Interval(i, i + intervalSize));
        }
        intervals.add(new Interval(i, Integer.MAX_VALUE));
        this.numLongestFiles = numLongestFiles;
    }

    public Interval getFileInterval(long lines) {
        return intervals.stream().filter(interval -> interval.accept(lines)).findFirst().get();
    }

    public List<Interval> getIntervals() {
        return Collections.unmodifiableList(intervals);
    }

    public int getNumLongestFiles() {
        return numLongestFiles;
    }
}
