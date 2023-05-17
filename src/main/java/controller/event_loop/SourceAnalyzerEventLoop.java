package controller.event_loop;

import controller.SearchConfiguration;
import controller.SourceAnalyzer;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import model.report.CompletableReport;
import model.report.ObservableReport;
import model.report.ObservableReportImpl;
import model.report.Report;
import model.resources.Directory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class SourceAnalyzerEventLoop implements SourceAnalyzer {

    private final SearchConfiguration configuration;

    public SourceAnalyzerEventLoop(SearchConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Future<Report> getReport(Directory directory) {
        CompletableFuture<Report> futureResult = new CompletableFuture<>();
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new VerticleSourceAnalyzer(directory, this.configuration, futureResult));
        return futureResult;
    }

    @Override
    public ObservableReport analyzeSources(Directory directory) {
        CompletableReport completableReport = new ObservableReportImpl(this.configuration);
        Vertx vertx = Vertx.vertx();
        var verticle = new ObservableVerticleSourceAnalyzer(directory, this.configuration, completableReport);
        vertx.deployVerticle(verticle);
        completableReport.addOnAbortHandler(() -> {
            EventBus eb = vertx.eventBus();
            eb.publish("shutdown-topic", "ciao");
        });
        return completableReport;
    }
}
