package model.report;

import controller.utils.Pair;

import java.util.List;

public interface Report {

    List<Interval> getIntervals();

    Integer filesCount(Interval interval);

    List<Pair<String, Integer>> longestFiles();

    void aggregate(Report report);
}
