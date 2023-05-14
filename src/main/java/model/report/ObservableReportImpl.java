package model.report;

import controller.SearchConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ObservableReportImpl implements CompletableReport {

    private final Report report;
    private final List<Consumer<Report>> onUpdateHandlers;
    private final List<Runnable> onAbortHandlers;
    private final List<Runnable> onCompleteHandlers;

    public ObservableReportImpl(SearchConfiguration searchConfiguration) {
        this.report = new ReportImpl(searchConfiguration);
        this.onUpdateHandlers = new ArrayList<>();
        this.onAbortHandlers = new ArrayList<>();
        this.onCompleteHandlers = new ArrayList<>();
    }

    @Override
    public List<Interval> getIntervals() {
        return this.report.getIntervals();
    }

    @Override
    public Long filesCount(Interval interval) {
        return this.report.filesCount(interval);
    }

    @Override
    public List<Pair<String, Long>> filesInInterval(Interval interval) {
        return this.report.filesInInterval(interval);
    }

    @Override
    public List<Pair<String, Integer>> longestFiles(int n) {
        return this.report.longestFiles(n);
    }

    @Override
    public void aggregate(Report report) {
        this.report.aggregate(report);
    }

    @Override
    public void addUpdateHandler(Consumer<Report> onUpdateHandler) {
        this.onUpdateHandlers.add(onUpdateHandler);
    }

    @Override
    public void addOnAbortHandler(Runnable onAbortHandler) {
        this.onAbortHandlers.add(onAbortHandler);
    }

    @Override
    public void addOnCompleteHandler(Runnable onCompleteHandler) {
        this.onCompleteHandlers.add(onCompleteHandler);
    }

    @Override
    public void abort() {
        this.onAbortHandlers.forEach(Runnable::run);
    }

    @Override
    public void notifyUpdate() {
        this.onUpdateHandlers.forEach(handler -> handler.accept(this.report));
    }

    @Override
    public void notifyCompletion() {
        this.onCompleteHandlers.forEach(Runnable::run);
    }
}
