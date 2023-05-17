package controller.virtual_threads;

import model.report.Report;
import model.resources.Directory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class VTDirectoryCallable implements Callable<Report> {

    private final Directory directory;
    private final ExecutorService executor;

    VTDirectoryCallable(Directory directory, ExecutorService executor) {
        this.directory = directory;
        this.executor = executor;
    }

    @Override
    public Report call() throws Exception {
        return null;
    }
}
