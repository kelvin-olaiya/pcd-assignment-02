package controller.executors;

import controller.SearchConfiguration;
import controller.SourceAnalyzer;
import model.report.*;
import model.resources.Directory;
import model.resources.Resource;
import model.resources.SourceFile;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class SourceAnalyzerExecutor implements SourceAnalyzer {

    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public static RecursiveTask<Report> fromResource(Resource resource, SearchInstance searchInstance) {
        return resource instanceof Directory ?
                new DirectoryAnalyzerTask((Directory) resource, searchInstance) :
                new SourceFileAnalyzerTask((SourceFile) resource, searchInstance);
    }

    @Override
    public Future<Report> getReport(Directory directory) {
        return forkJoinPool.submit(new DirectoryAnalyzerTask(directory, new SearchInstance()));
    }

    @Override
    public ObservableReport analyzeSources(Directory directory) {
        var configuration = new SearchConfiguration(5, 1000);
        CompletableReport observableReport = new ObservableReportImpl(configuration);
        final SearchInstance searchInstance = new SearchInstance(configuration, observableReport);
        forkJoinPool.submit(new DirectoryAnalyzerTask(directory, searchInstance));
        return observableReport;
    }
}
