package controller.event_loop;

import controller.executors.SearchConfiguration;
import io.vertx.core.AbstractVerticle;
import model.report.Report;
import model.resources.Directory;

import java.util.concurrent.CompletableFuture;

public class VerticleSourceAnalyzer extends AbstractVerticle {


    private final Directory directory;
    private final SearchConfiguration configuration;
    private final CompletableFuture<Report> futureResult;

    public VerticleSourceAnalyzer(Directory directory, SearchConfiguration configuration, CompletableFuture<Report> futureResult) {
        this.directory = directory;
        this.configuration = configuration;
        this.futureResult = futureResult;
    }

    @Override
    public void start() throws Exception {
        super.start();
    }
}
