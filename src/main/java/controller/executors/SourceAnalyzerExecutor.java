package controller.executors;

import controller.SourceAnalyzer;
import model.report.ObservableReport;
import model.report.ObservableReportImpl;
import model.report.Report;
import model.report.ReportImpl;
import model.resources.Directory;
import model.resources.Resource;
import model.resources.SourceFile;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class SourceAnalyzerExecutor implements SourceAnalyzer {

    private final ForkJoinPool forkJoinPool = new ForkJoinPool();
    private final SearchConfiguration configuration;

    public SourceAnalyzerExecutor() {
        this.configuration = new SearchConfiguration(5, 1000);
    }

    public SourceAnalyzerExecutor(SearchConfiguration configuration) {
        this.configuration = configuration;
    }

    public static RecursiveTask<Report> fromResource(Resource resource, SearchConfiguration configuration) {
        return resource instanceof Directory ?
                new DirectoryAnalyzerTask((Directory) resource, configuration) :
                new SourceFileAnalyzerTask((SourceFile) resource, configuration);
    }

    @Override
    public Future<Report> getReport(Directory directory) {
        return forkJoinPool.submit(new DirectoryAnalyzerTask(directory, this.configuration));
    }

    @Override
    public ObservableReport analyzeSources(Directory directory) {
        ObservableReport observableReport = new ObservableReportImpl(new ReportImpl(this.configuration));
        forkJoinPool.submit(new DirectoryAnalyzerTask(directory, this.configuration, observableReport));
        return observableReport;
    }
}
