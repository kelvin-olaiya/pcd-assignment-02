package controller.executors;

import model.resources.Directory;
import model.resources.SourceFile;
import model.report.Report;
import model.report.ReportImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

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
        var report = new ReportImpl(searchConfiguration, "", 0L);
        for (var resource : directory.getResources()) {
            var task = resource.isDirectory() ?
                    new DirectoryAnalyzerTask((Directory) resource, searchConfiguration) :
                    new SourceFileAnalyzerTask((SourceFile) resource, searchConfiguration);
            tasks.add(task);
            task.fork();
        }
        for (RecursiveTask<Report> task : tasks) {
            report.aggregate(task.join());
        }
        return report;
    }
}
