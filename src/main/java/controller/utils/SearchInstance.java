package controller.utils;

import controller.utils.SearchConfiguration;
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

    public Optional<CompletableReport> getReport() {
        return Optional.ofNullable(observableReport);
    }

    public SearchConfiguration getConfiguration() {
        return configuration;
    }
}
