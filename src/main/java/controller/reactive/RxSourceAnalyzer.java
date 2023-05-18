package controller.reactive;

import controller.SearchConfiguration;
import controller.SourceAnalyzer;
import io.reactivex.rxjava3.core.Observable;
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

public class RxSourceAnalyzer implements SourceAnalyzer {

    private final SearchConfiguration configuration;

    public RxSourceAnalyzer(SearchConfiguration configuration) {
        this.configuration = configuration;
    }

    private Observable<SourceFile> fromDirectory(Directory directory) {
        return fromDirectory(directory, new Flag());
    }

    private Observable<SourceFile> fromDirectory(Directory directory, Flag stopFlag) {
        Observable<SourceFile> observable = Observable.create(emitter -> {
            new Thread(() -> {
                try(var filesStream = Files.walk(Path.of(directory.getAbsolutePath()))) {
                    filesStream.filter(path -> !Files.isDirectory(path))
                            .map(p -> Resource.fromFile(p.toFile(), Set.of("java")))
                            .filter(Optional::isPresent)
                            .map(o -> (SourceFile) o.get())
                            .forEach(s -> {
                                if(stopFlag.isSet()) {
                                    throw new RuntimeException();
                                }
                                emitter.onNext(s);
                            });
                } catch (IOException ignore) {
                } finally {
                    emitter.onComplete();
                }
            }).start();
        });
        return observable;
    }
    @Override
    public Future<Report> getReport(Directory directory) {
        CompletableFuture<Report> future = new CompletableFuture<>();
        CompletableReport report = new ObservableReportImpl(this.configuration);
        fromDirectory(directory)
                .map(resource -> new ReportImpl(this.configuration, resource.getName(), resource.linesCount()))
                .doOnNext(report::aggregate)
                .doOnComplete(() -> future.complete(report))
                .subscribe();
        return future;
    }

    @Override
    public ObservableReport analyzeSources(Directory directory) {
        CompletableReport report = new ObservableReportImpl(this.configuration);
        Flag stopFlag = new Flag();
        report.addOnAbortHandler(stopFlag::set);
        fromDirectory(directory, stopFlag).subscribeOn(Schedulers.io())
            .map(resource -> new ReportImpl(this.configuration, resource.getName(), resource.linesCount()))
            .doOnNext(r -> {
                report.aggregate(r);
                report.notifyUpdate();
            })
            .doOnComplete(report::notifyCompletion)
            .subscribe();
        return report;
    }
}
