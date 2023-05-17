package controller.virtual_threads;

import model.report.Report;
import model.resources.SourceFile;

import java.util.concurrent.Callable;

public class VTFileCallable implements Callable<Report> {

    private final SourceFile file;

    VTFileCallable(SourceFile file) {
        this.file = file;
    }

    @Override
    public Report call() throws Exception {
        return null;
    }
}
