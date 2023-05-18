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

public class ExecutorSourceAnalyzer implements SourceAnalyzer {

    private final SearchConfiguration searchConfiguration;

    public ExecutorSourceAnalyzer(SearchConfiguration searchConfiguration) {
        this.searchConfiguration = searchConfiguration;
    }

    public static RecursiveTask<Report> fromResource(Resource resource, SearchInstance searchInstance) {
        return resource instanceof Directory ?
                new DirectoryAnalyzerTask((Directory) resource, searchInstance) :
                new SourceFileAnalyzerTask((SourceFile) resource, searchInstance);
    }

    @Override
    public Future<Report> getReport(Directory directory) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.submit(new DirectoryAnalyzerTask(
                directory,
                new SearchInstance(this.searchConfiguration)));
    }

    @Override
    public ObservableReport analyzeSources(Directory directory) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CompletableReport completableReport = new ObservableReportImpl(this.searchConfiguration);
        final SearchInstance searchInstance = new SearchInstance(this.searchConfiguration, completableReport);
        var future = forkJoinPool.submit(new DirectoryAnalyzerTask(directory, searchInstance));
        completableReport.addOnAbortHandler(() -> {
            forkJoinPool.shutdownNow();
            forkJoinPool.close();
            completableReport.notifyCompletion();
        });
        new Thread(() -> {
            try {
                future.get();
                completableReport.notifyCompletion();
                forkJoinPool.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
        return completableReport;
    }
}