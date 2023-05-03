package model.report;

import controller.executors.SearchConfiguration;
import model.report.Interval;
import model.report.Pair;
import model.report.Report;

import java.util.*;

public class ReportImpl implements Report {

    private final Map<Interval, List<Pair<String, Long>>> counter = new HashMap<>();
    private final SearchConfiguration configuration;

    public ReportImpl(SearchConfiguration searchConfiguration, String file, Long lines) {
        this.configuration = searchConfiguration;
        addFile(new Pair<>(file, lines));
    }

    private void addFile(Pair<String, Long> statistic) {
        var interval = this.configuration.getFileInterval(statistic.getY());
        addFile(interval, statistic);
    }

    private void addFile(Interval interval, Pair<String, Long> statistic) {
        counter.putIfAbsent(interval, new ArrayList<>());
        counter.computeIfPresent(interval, (key, list) -> {
            list.add(statistic);
            return list;
        });
    }

    @Override
    public List<Interval> getIntervals() {
        return counter.keySet().stream().toList();
    }

    @Override
    public Long filesCount(Interval interval) {
        return (long) filesInInterval(interval).size();
    }

    @Override
    public List<Pair<String, Long>> filesInInterval(Interval interval) {
        return Collections.unmodifiableList(counter.get(interval));
    }

    @Override
    public List<Pair<String, Integer>> longestFiles(int n) {
        return null;
    }

    @Override
    public void aggregate(Report report) {
        report.getIntervals()
            .forEach(interval ->
                report.filesInInterval(interval).forEach(stat -> this.addFile(interval, stat))
            );
    }
}
