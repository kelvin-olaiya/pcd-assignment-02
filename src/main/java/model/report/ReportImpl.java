package model.report;

import controller.utils.Pair;
import controller.utils.SearchConfiguration;

import java.util.*;

public class ReportImpl implements Report {

    private final Map<Interval, Integer> counter = new HashMap<>();
    private final TreeSet<Pair<String, Integer>> longestFiles = new TreeSet<>((a, b) -> b.getY() - a.getY());
    private final SearchConfiguration configuration;

    public ReportImpl(SearchConfiguration searchConfiguration, String file, Integer lines) {
        this.configuration = searchConfiguration;
        addFile(new Pair<>(file, lines));
    }

    public ReportImpl(SearchConfiguration searchConfiguration) {
        this.configuration = searchConfiguration;
    }

    private void addFile(Pair<String, Integer> statistic) {
        var interval = this.configuration.getFileInterval(statistic.getY());
        counter.putIfAbsent(interval, 0);
        counter.put(interval, counter.get(interval) + 1);
        longestFiles.add(statistic);
    }

    @Override
    public List<Interval> getIntervals() {
        return this.configuration.getIntervals();
    }

    @Override
    public Integer filesCount(Interval interval) {
        return counter.getOrDefault(interval, 0);
    }

    @Override
    public List<Pair<String, Integer>> longestFiles() {
        return longestFiles.stream().limit(this.configuration.getNumLongestFiles()).toList();
    }

    @Override
    public void aggregate(Report report) {
        report.getIntervals()
            .forEach(interval -> {
                counter.putIfAbsent(interval, 0);
                counter.put(interval, (counter.get(interval) + report.filesCount(interval)));
                this.longestFiles.addAll(report.longestFiles());
            });
    }

    @Override
    public String toString() {
        StringBuilder report = new StringBuilder();
        for (var interval : getIntervals()) {
            report.append(interval).append(": ").append(filesCount(interval)).append("\n");
        }
        return report.toString();
    }
}
