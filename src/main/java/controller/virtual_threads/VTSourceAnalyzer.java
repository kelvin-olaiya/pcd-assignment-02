package controller.virtual_threads;

import controller.utils.SearchConfiguration;
import controller.SourceAnalyzer;
import model.report.ObservableReport;
import model.report.Report;
import model.resources.Directory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VTSourceAnalyzer implements SourceAnalyzer {

    private final SearchConfiguration configuration;

    public VTSourceAnalyzer(SearchConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Future<Report> getReport(Directory directory) {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        return executor.submit(new VTDirectoryTask(directory, configuration, executor));
    }

    @Override
    public ObservableReport analyzeSources(Directory directory) {
        return null;
    }
}
