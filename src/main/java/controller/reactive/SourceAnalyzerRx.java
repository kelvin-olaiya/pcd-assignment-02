package controller.reactive;

import controller.SearchConfiguration;
import controller.SourceAnalyzer;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.report.*;
import model.resources.Directory;
import model.resources.Resource;
import model.resources.SourceFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class SourceAnalyzerRx implements SourceAnalyzer {

    private final SearchConfiguration configuration;

    public SourceAnalyzerRx(SearchConfiguration configuration) {
        this.configuration = configuration;
    }
    @Override
    public Future<Report> getReport(Directory directory) {
        CompletableFuture<Report> futureResult = new CompletableFuture<>();
        try {
            CompletableReport report = new ObservableReportImpl(this.configuration);
            Observable.fromStream(Files.walk(Path.of(directory.getAbsolutePath())))
                    .filter(path -> !Files.isDirectory(path))
                    .map(p -> Resource.fromFile(p.toFile(), Set.of("java")))
                    .filter(Optional::isPresent)
                    .map(o -> (SourceFile) o.get())
                    .subscribeOn(Schedulers.newThread())
                    .map(resource -> new ReportImpl(this.configuration, resource.getName(), resource.linesCount()))
                    .doOnNext(report::aggregate)
                    .doOnComplete(() -> futureResult.complete(report))
                    .subscribe();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return futureResult;
    }

    @Override
    public ObservableReport analyzeSources(Directory directory) {
        CompletableReport report = new ObservableReportImpl(this.configuration);
        try {
            Observable.fromStream(Files.walk(Path.of(directory.getAbsolutePath())))
                    .filter(path -> !Files.isDirectory(path))
                    .map(p -> Resource.fromFile(p.toFile(), Set.of("java")))
                    .filter(Optional::isPresent)
                    .map(o -> (SourceFile) o.get())
                    .subscribeOn(Schedulers.newThread())
                    .map(resource -> new ReportImpl(this.configuration, resource.getName(), resource.linesCount()))
                    .doOnNext(r -> {
                        report.aggregate(r);
                        report.notifyUpdate();
                    })
                    .doOnComplete(report::notifyCompletion)
                    .subscribe();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return report;
    }
}
