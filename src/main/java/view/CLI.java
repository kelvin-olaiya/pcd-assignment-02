package view;

import controller.SourceAnalyzer;
import model.report.Report;
import model.resources.Directory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CLI {

    private SourceAnalyzer sourceAnalyzer;
    public CLI(SourceAnalyzer sourceAnalyzer) {
        this.sourceAnalyzer = sourceAnalyzer;
    }

    public void start(String directory) throws IOException, ExecutionException, InterruptedException {
        Future<Report> futureResult = this.sourceAnalyzer.getReport(new Directory(new File(directory)));
        System.out.println(futureResult.get());
    }
}
