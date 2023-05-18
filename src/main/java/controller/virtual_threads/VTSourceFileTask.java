package controller.virtual_threads;

import controller.utils.SearchConfiguration;
import model.report.Report;
import model.report.ReportImpl;
import model.resources.SourceFile;

import java.util.concurrent.Callable;

public class VTSourceFileTask implements Callable<Report> {

    private final SourceFile file;
    private final SearchConfiguration configuration;

    VTSourceFileTask(SourceFile file, SearchConfiguration configuration) {
        this.file = file;
        this.configuration = configuration;
    }

    @Override
    public Report call() throws Exception {
        return new ReportImpl(configuration, file.getName(), file.linesCount());
    }
}
