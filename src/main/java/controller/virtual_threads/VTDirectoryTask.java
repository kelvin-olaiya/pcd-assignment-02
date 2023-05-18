package controller.virtual_threads;

import controller.SearchConfiguration;
import model.report.Report;
import model.report.ReportImpl;
import model.resources.Directory;
import model.resources.Resource;
import model.resources.SourceFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class VTDirectoryTask implements Callable<Report> {

    private final Directory directory;
    private final ExecutorService executor;
    private final SearchConfiguration configuration;

    VTDirectoryTask(Directory directory, SearchConfiguration configuration, ExecutorService executor) {
        this.directory = directory;
        this.configuration = configuration;
        this.executor = executor;
    }

    private Callable<Report> fromResource(Resource resource) {
        if (resource instanceof Directory directoryResource) {
            return new VTDirectoryTask(directoryResource, configuration, executor);
        }
        return new VTSourceFileTask((SourceFile) resource, configuration);
    }

    @Override
    public Report call() throws Exception {
        List<Future<Report>> results = new ArrayList<>();
        try {
            for (var resource : directory.getResources()) {
                Future<Report> future = executor.submit(fromResource(resource));
                results.add(future);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var report = new ReportImpl(configuration);
        for (var result: results) {
            report.aggregate(result.get());
        }
        return report;
    }
}
