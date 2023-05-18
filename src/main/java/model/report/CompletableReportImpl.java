package model.report;

import controller.utils.Monitor;
import controller.utils.Pair;
import controller.utils.SearchConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class CompletableReportImpl implements CompletableReport {

    private final Report report;
    private final List<BiConsumer<List<StatLine>, List<String>>> onUpdateHandlers;
    private final List<Runnable> onAbortHandlers;
    private final List<Runnable> onCompleteHandlers;
    private final Monitor monitor = new Monitor();

    public CompletableReportImpl(SearchConfiguration searchConfiguration) {
        this.report = new ReportImpl(searchConfiguration);
        this.onUpdateHandlers = new ArrayList<>();
        this.onAbortHandlers = new ArrayList<>();
        this.onCompleteHandlers = new ArrayList<>();
    }

    @Override
    public List<Interval> getIntervals() {
        return monitor.on(this.report::getIntervals);
    }

    @Override
    public Integer filesCount(Interval interval) {
        return monitor.on(() -> this.report.filesCount(interval));
    }

    @Override
    public List<Pair<String, Integer>> longestFiles() {
        return monitor.on(this.report::longestFiles);
    }

    @Override
    public void aggregate(Report report) {
        monitor.on(() -> this.report.aggregate(report));
    }

    @Override
    public void addUpdateHandler(BiConsumer<List<StatLine>, List<String>> onUpdateHandler) {
        monitor.on(() -> this.onUpdateHandlers.add(onUpdateHandler));
    }

    @Override
    public void addOnAbortHandler(Runnable onAbortHandler) {
        monitor.on(() -> this.onAbortHandlers.add(onAbortHandler));
    }

    @Override
    public void addOnCompleteHandler(Runnable onCompleteHandler) {
        monitor.on(() -> this.onCompleteHandlers.add(onCompleteHandler));
    }

    @Override
    public void abort() {
        monitor.on(() -> this.onAbortHandlers.forEach(Runnable::run));
    }

    @Override
    public void notifyUpdate() {
        monitor.on(() -> {
            var statLines = this.report.getIntervals().stream()
                .map(interval -> new StatLine(interval, this.report.filesCount(interval)))
                .toList();
            this.onUpdateHandlers.forEach(handler ->
                handler.accept(statLines, this.report.longestFiles().stream().map(Pair::getX).toList())
            );
        });
    }

    @Override
    public void notifyCompletion() {
        monitor.on(() -> this.onCompleteHandlers.forEach(Runnable::run));
    }

    @Override
    public String toString() {
        return monitor.on(this.report::toString);
    }
}
