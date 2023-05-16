package controller.event_loop;

import controller.SearchConfiguration;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import model.report.CompletableReport;
import model.report.Report;
import model.report.ReportImpl;
import model.resources.Directory;

import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObservableVerticleSourceAnalyzer extends AbstractVerticleSourceAnalyzer {

    private final CompletableReport report;

    public ObservableVerticleSourceAnalyzer(Directory directory, SearchConfiguration configuration, CompletableReport report) {
        super(directory, configuration);
        this.report = report;
    }

    @Override
    public void start() {
        EventBus eb = this.getVertx().eventBus();
        eb.consumer("shutdown-topic", message -> {
            stopped = true;
            report.notifyCompletion();
            this.vertx.close();
        });
        super.start();
    }

    @Override
    protected void aggregateResult(String file, int lines) {
        this.report.aggregate(new ReportImpl(this.configuration, file, lines));
        this.report.notifyUpdate();
    }

    @Override
    protected void complete() {
        this.report.notifyCompletion();
    }

}
