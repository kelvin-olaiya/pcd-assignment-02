package controller.executors;

import model.resources.Directory;
import model.report.Report;
import model.report.ReportImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import static controller.executors.SourceAnalyzerExecutor.fromResource;

public class DirectoryAnalyzerTask extends RecursiveTask<Report> {

    private final Directory directory;
    private final SearchConfiguration searchConfiguration;

    DirectoryAnalyzerTask(Directory directory, SearchConfiguration searchConfiguration) {
        this.directory = directory;
        this.searchConfiguration = searchConfiguration;
    }

    @Override
    protected Report compute() {
        List<RecursiveTask<Report>> tasks = new ArrayList<>();
        var report = new ReportImpl(searchConfiguration);
        for (var resource : directory.getResources()) {
            var task = fromResource(resource, searchConfiguration);
            tasks.add(task);
            task.fork();
        }
        for (RecursiveTask<Report> task : tasks) {
            report.aggregate(task.join());
        }
        return report;
    }
}
