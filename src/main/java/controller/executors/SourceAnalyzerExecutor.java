package controller.executors;

import controller.SourceAnalyzer;
import model.report.Report;
import model.resources.Directory;
import model.resources.Resource;
import model.resources.SourceFile;

import java.util.concurrent.ForkJoinPool;
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
    public Report getReport(Directory directory) {
        return forkJoinPool.invoke(new DirectoryAnalyzerTask(directory, this.configuration));
    }

    @Override
    public void analyzeSources(Directory directory) {
        // TODO
    }
}
