package controller.event_loop;

import controller.utils.SearchConfiguration;
import model.report.Report;
import model.report.ReportImpl;
import model.resources.Directory;

import java.util.concurrent.CompletableFuture;

public class VerticleSourceAnalyzer extends AbstractVerticleSourceAnalyzer {

    private final CompletableFuture<Report> futureResult;
    private final Report report;
    private final ResourceCounter counter = new ResourceCounter();

    public VerticleSourceAnalyzer(Directory directory, SearchConfiguration configuration, CompletableFuture<Report> futureResult) {
        super(directory, configuration);
        this.futureResult = futureResult;
        this.report = new ReportImpl(configuration);
    }

    @Override
    protected void aggregateResult(String file, int lines) {
        report.aggregate(new ReportImpl(this.configuration, file, lines));
    }

    @Override
    protected void complete() {
        futureResult.complete(report);
    }

}
