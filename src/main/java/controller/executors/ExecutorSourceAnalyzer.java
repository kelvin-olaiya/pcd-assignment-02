package controller.executors;

import model.*;
import model.report.Report;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ExecutorSourceAnalyzer implements SourceAnalyzer {

    private final ForkJoinPool forkJoinPool = new ForkJoinPool();
    private final SearchConfiguration configuration;

    public ExecutorSourceAnalyzer() {
        this.configuration = new SearchConfiguration(5, 1000);
    }

    @Override
    public Report getReport(Directory directory) {
        var tasks = new ArrayList<RecursiveTask<Report>>();
        for (var resource : directory.getResources()) {
            RecursiveTask<Report> task;
            if (resource.isDirectory()) {
                task = new DirectoryAnalyzerTask(resource);
            } else {
                task = new SourceFileAnalyzerTask(resource, this.configuration);
            }
            tasks.add(task);
        }

        Report report = null;
        for (var task : tasks) {
            if (report == null) {
                report = task.join();
            } else {
                report.aggregate(task.join());
            }
        }
        return report;
    }

    @Override
    public void analyzeSources(Directory directory) {
        // TODO
    }
}
