package controller.executors;

import controller.SearchConfiguration;
import model.report.Report;
import model.report.ReportImpl;
import model.resources.SourceFile;

import java.util.concurrent.RecursiveTask;

public class SourceFileAnalyzerTask extends RecursiveTask<Report> {

    private final SourceFile sourceFile;
    private final SearchConfiguration configuration;

    SourceFileAnalyzerTask(SourceFile sourceFile, SearchInstance searchInstance) {
        this.sourceFile = sourceFile;
        this.configuration = searchInstance.getConfiguration();
    }

    @Override
    protected Report compute() {
        return new ReportImpl(configuration, sourceFile.getName(), sourceFile.linesCount());
    }
}
