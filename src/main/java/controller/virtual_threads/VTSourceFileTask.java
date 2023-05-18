package controller.virtual_threads;

import controller.utils.SearchConfiguration;
import controller.utils.SearchInstance;
import model.report.Report;
import model.report.ReportImpl;
import model.resources.SourceFile;

import java.util.concurrent.Callable;

public class VTSourceFileTask implements Callable<Report> {

    private final SourceFile file;
    private final SearchInstance searchInstance;

    VTSourceFileTask(SourceFile file, SearchInstance searchInstance) {
        this.file = file;
        this.searchInstance = searchInstance;
    }

    @Override
    public Report call() {
        Report localReport = new ReportImpl(searchInstance.getConfiguration(), file.getName(), file.linesCount());
        searchInstance.getReport().ifPresent(r -> {
            r.aggregate(localReport);
            r.notifyUpdate();
        });
        return localReport;
    }
}
