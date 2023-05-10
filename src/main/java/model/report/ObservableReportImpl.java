package model.report;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ObservableReportImpl implements ObservableReport {

    private final Report report;
    private final List<Consumer<Report>> onUpdateHandlers;

    public ObservableReportImpl(Report report) {
        this.report = report;
        this.onUpdateHandlers = new ArrayList<>();
    }
    @Override
    public void addUpdateHandler(Consumer<Report> onUpdateHandler) {
        this.onUpdateHandlers.add(onUpdateHandler);
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
        this.onUpdateHandlers.forEach(handler -> handler.accept(this.report));
    }
}
