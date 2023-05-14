package controller.executors;

import controller.SearchConfiguration;
import model.report.CompletableReport;
import model.report.ObservableReport;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchInstance {

    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    private final ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
    private final CompletableReport observableReport;

    private final SearchConfiguration configuration = new SearchConfiguration(5, 1000);

    public SearchInstance(CompletableReport observableReport) {
        this.observableReport = observableReport;
    }
    public ExecutorService getExecutorService() {
        return executorService;
    }

    public CompletableReport getObservableReport() {
        return observableReport;
    }

    public SearchConfiguration getConfiguration() {
        return configuration;
    }
}
