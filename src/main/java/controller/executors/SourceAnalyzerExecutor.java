package controller.executors;

import model.*;
import model.report.Report;
import model.resources.Directory;

import java.util.concurrent.ForkJoinPool;

public class SourceAnalyzerExecutor implements SourceAnalyzer {

    private final ForkJoinPool forkJoinPool = new ForkJoinPool();
    private final SearchConfiguration configuration;

    public SourceAnalyzerExecutor() {
        this.configuration = new SearchConfiguration(5, 1000);
    }

    @Override
    public Report getReport(Directory directory) {
        return forkJoinPool.invoke(new DirectoryAnalyzerTask(directory, this.configuration));
    }

    @Override
    public void analyzeSources(Directory directory) {
        // TODO
    }
}
