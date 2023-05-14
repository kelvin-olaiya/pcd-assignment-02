package controller.event_loop;

import controller.executors.SearchConfiguration;
import io.vertx.core.AbstractVerticle;
import model.report.Report;
import model.report.ReportImpl;
import model.resources.Directory;

import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerticleSourceAnalyzer extends AbstractVerticle {


    private final Directory directory;
    private final SearchConfiguration configuration;
    private final CompletableFuture<Report> futureResult;
    private final Report report;
    private final ResourceCounter counter = new ResourceCounter();

    public VerticleSourceAnalyzer(Directory directory, SearchConfiguration configuration, CompletableFuture<Report> futureResult) {
        this.directory = directory;
        this.configuration = configuration;
        this.futureResult = futureResult;
        this.report = new ReportImpl(this.configuration);
    }

    @Override
    public void start() {
        this.walk(this.directory.getName());
    }

    private void onNewResourcesToAnalyze() {
        counter.inc();
    }

    private void onFolderVisiting() {
        counter.dec();
    }

    private void analyzeFile(String file, String content) {
        Matcher m = Pattern.compile("\r\n|\r|\n").matcher(content);
        long lines = 0;
        while (m.find()) {
            lines++;
        }
        report.aggregate(new ReportImpl(this.configuration, file, lines));
    }

    private void onFileAnalyzed() {
        counter.dec();
        if (counter.noMoreResources()) {
            futureResult.complete(report);
            this.vertx.close();
        }
    }

    private void walk(String resource) {
        this.onNewResourcesToAnalyze();
        vertx.fileSystem().props(resource, props -> {
            if (props.result().isDirectory()) {
                vertx.fileSystem().readDir(resource, list -> {
                    this.onFolderVisiting();
                    for (var sub : list.result()) {
                        walk(sub);
                    }
                });
            } else if (props.result().isRegularFile() && resource.endsWith(".java")) { // TODO: extract
                vertx.fileSystem().readFile(resource, r -> {
                    analyzeFile(resource, r.result().toString());
                    onFileAnalyzed();
                });
            } else {
                onFileAnalyzed();
            }
        });
    }

}
