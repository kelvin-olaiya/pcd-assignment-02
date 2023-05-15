package model.report;

import controller.SearchConfiguration;

import java.util.*;

public class ReportImpl implements Report {

    private final Map<Interval, List<Pair<String, Long>>> counter = new HashMap<>();
    private final SearchConfiguration configuration;

    public ReportImpl(SearchConfiguration searchConfiguration, String file, Long lines) {
        this.configuration = searchConfiguration;
        addFile(new Pair<>(file, lines));
    }

    public ReportImpl(SearchConfiguration searchConfiguration) {
        this.configuration = searchConfiguration;
    }

    private void addFile(Pair<String, Long> statistic) {
        var interval = this.configuration.getFileInterval(statistic.getY());
        addFile(interval, statistic);
    }

    private void addFile(Interval interval, Pair<String, Long> statistic) {
        counter.putIfAbsent(interval, new ArrayList<>());
        counter.get(interval).add(statistic);
    }

    @Override
    public List<Interval> getIntervals() {
        return this.configuration.getIntervals();
    }

    @Override
    public Long filesCount(Interval interval) {
        return (long) filesInInterval(interval).size();
    }

    @Override
    public List<Pair<String, Long>> filesInInterval(Interval interval) {
        counter.computeIfAbsent(interval, i -> new ArrayList<>());
        return Collections.unmodifiableList(counter.get(interval));
    }

    @Override
    public List<Pair<String, Integer>> longestFiles(int n) { return new ArrayList<>(); }

    @Override
    public void aggregate(Report report) {
        report.getIntervals()
            .forEach(interval ->
                report.filesInInterval(interval).forEach(stat -> this.addFile(interval, stat))
            );
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
