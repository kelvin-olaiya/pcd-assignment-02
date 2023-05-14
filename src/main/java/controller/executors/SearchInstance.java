package controller.executors;

import controller.SearchConfiguration;
import model.report.CompletableReport;

import java.util.Optional;

public class SearchInstance {

    private final CompletableReport observableReport;
    private final SearchConfiguration configuration;

    public SearchInstance(SearchConfiguration configuration, CompletableReport observableReport) {
        this.observableReport = observableReport;
        this.configuration = configuration;
    }
    public SearchInstance(SearchConfiguration configuration) {
        this(configuration, null);
    }

    public SearchInstance() {
        this(new SearchConfiguration(5, 1000));
    }

    public Optional<CompletableReport> getReport() {
        return Optional.ofNullable(observableReport);
    }

    public SearchConfiguration getConfiguration() {
        return configuration;
    }
}
