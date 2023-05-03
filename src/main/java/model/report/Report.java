package model.report;

import java.util.List;

public interface Report {

    List<Interval> getIntervals();

    Long filesCount(Interval interval);

    List<Pair<String, Long>> filesInInterval(Interval interval);

    List<Pair<String, Integer>> longestFiles(int n);

    void aggregate(Report report);
}
