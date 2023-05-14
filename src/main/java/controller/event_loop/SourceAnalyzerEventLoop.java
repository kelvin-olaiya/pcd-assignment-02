package controller.event_loop;

import controller.SearchConfiguration;
import controller.SourceAnalyzer;
import io.vertx.core.Vertx;
import model.report.ObservableReport;
import model.report.Report;
import model.resources.Directory;

import java.util.concurrent.CompletableFuture;

public class SourceAnalyzerEventLoop implements SourceAnalyzer {

    private final SearchConfiguration configuration;

    public SourceAnalyzerEventLoop(SearchConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public CompletableFuture<Report> getReport(Directory directory) {
        CompletableFuture<Report> futureResult = new CompletableFuture<>();
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new VerticleSourceAnalyzer(directory, this.configuration, futureResult));
        return futureResult;
    }

    @Override
    public ObservableReport analyzeSources(Directory directory) {
        // TODO
        return null;
    }
}
