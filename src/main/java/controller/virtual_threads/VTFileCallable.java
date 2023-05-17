package controller.virtual_threads;

import controller.SearchConfiguration;
import model.report.Report;
import model.report.ReportImpl;
import model.resources.SourceFile;

import java.util.concurrent.Callable;

public class VTFileCallable implements Callable<Report> {

    private final SourceFile file;
    private final SearchConfiguration configuration;

    VTFileCallable(SourceFile file, SearchConfiguration configuration) {
        this.file = file;
        this.configuration = configuration;
    }

    @Override
    public Report call() throws Exception {
        return new ReportImpl(configuration, file.getName(), file.linesCount());
    }
}
