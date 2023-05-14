package controller.executors;

import model.report.Report;
import model.report.ReportImpl;
import model.resources.SourceFile;

import java.util.concurrent.RecursiveTask;

public class SourceFileAnalyzerTask extends RecursiveTask<Report> {

    private final SourceFile sourceFile;
    private final SearchInstance searchInstance;

    SourceFileAnalyzerTask(SourceFile sourceFile, SearchInstance searchInstance) {
        this.sourceFile = sourceFile;
        this.searchInstance = searchInstance;
    }

    @Override
    protected Report compute() {
        var report = new ReportImpl(this.searchInstance.getConfiguration(), sourceFile.getName(), sourceFile.linesCount());
        this.searchInstance.getReport().ifPresent(r -> {
            r.aggregate(report);
            r.notifyUpdate();
        });
        return report;
    }
}
