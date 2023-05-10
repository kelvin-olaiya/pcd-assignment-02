package controller.executors;

import model.report.Report;
import model.report.ReportImpl;
import model.resources.SourceFile;

import java.util.concurrent.RecursiveTask;

public class SourceFileAnalyzerTask extends RecursiveTask<Report> {

    private final SourceFile sourceFile;
    private final SearchConfiguration configuration;

    SourceFileAnalyzerTask(SourceFile sourceFile, SearchConfiguration configuration) {
        this.sourceFile = sourceFile;
        this.configuration = configuration;
    }

    @Override
    protected Report compute() {
        return new ReportImpl(configuration, sourceFile.getName(), sourceFile.linesCount());
    }
}
