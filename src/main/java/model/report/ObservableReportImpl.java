package model.report;

import controller.SearchConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ObservableReportImpl implements CompletableReport {

    private final Report report;
    private final List<BiConsumer<List<StatLine>, List<String>>> onUpdateHandlers;
    private final List<Runnable> onAbortHandlers;
    private final List<Runnable> onCompleteHandlers;
    private final SearchConfiguration searchConfiguration;

    public ObservableReportImpl(SearchConfiguration searchConfiguration) {
        this.searchConfiguration = searchConfiguration;
        this.report = new ReportImpl(searchConfiguration);
        this.onUpdateHandlers = new ArrayList<>();
        this.onAbortHandlers = new ArrayList<>();
        this.onCompleteHandlers = new ArrayList<>();
    }


    @Override
    synchronized public List<Interval> getIntervals() {
        return this.report.getIntervals();
    }

    @Override
    synchronized public Integer filesCount(Interval interval) {
        return this.report.filesCount(interval);
    }

    @Override
    synchronized public List<Pair<String, Integer>> longestFiles() {
        return this.report.longestFiles();
    }

    @Override
    synchronized public void aggregate(Report report) {
        this.report.aggregate(report);
    }

    @Override
    synchronized public void addUpdateHandler(BiConsumer<List<StatLine>, List<String>> onUpdateHandler) {
        this.onUpdateHandlers.add(onUpdateHandler);
    }

    @Override
    synchronized public void addOnAbortHandler(Runnable onAbortHandler) {
        this.onAbortHandlers.add(onAbortHandler);
    }

    @Override
    synchronized public void addOnCompleteHandler(Runnable onCompleteHandler) {
        this.onCompleteHandlers.add(onCompleteHandler);
    }

    @Override
    synchronized public void abort() {
        this.onAbortHandlers.forEach(Runnable::run);
    }

    @Override
    synchronized public void notifyUpdate() {
        var statLines = this.report.getIntervals().stream()
                .map(interval -> new StatLine(interval, this.report.filesCount(interval)))
                .toList();
        this.onUpdateHandlers.forEach(handler -> {
            handler.accept(statLines, this.report.longestFiles().stream().map(Pair::getX).toList());
        });
    }

    @Override
    synchronized public void notifyCompletion() {
        this.onCompleteHandlers.forEach(Runnable::run);
    }
}
