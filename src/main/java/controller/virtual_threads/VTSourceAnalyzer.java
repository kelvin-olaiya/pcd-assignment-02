package controller.virtual_threads;

import controller.utils.SearchConfiguration;
import controller.SourceAnalyzer;
import controller.utils.SearchInstance;
import model.report.CompletableReport;
import model.report.CompletableReportImpl;
import model.report.ObservableReport;
import model.report.Report;
import model.resources.Directory;

import java.util.concurrent.ExecutionException;
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
        return executor.submit(new VTDirectoryTask(directory, new SearchInstance(configuration), executor));
    }

    @Override
    public ObservableReport analyzeSources(Directory directory) {
        CompletableReport completableReport = new CompletableReportImpl(configuration);
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        completableReport.addOnAbortHandler(() -> {
            executor.shutdownNow();
            completableReport.notifyCompletion();
            executor.close();
        });
        SearchInstance searchInstance = new SearchInstance(configuration, completableReport);
        Future<Report> future = executor.submit(new VTDirectoryTask(directory, searchInstance, executor));
        new Thread(getTerminationWaiter(future, completableReport, executor), "AwaitTermination").start();
        return completableReport;
    }

    private Runnable getTerminationWaiter(Future<Report> masterTask, CompletableReport report, ExecutorService executor) {
        return () -> {
            try {
                masterTask.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            report.notifyCompletion();
            executor.close();
        };
    }
}
