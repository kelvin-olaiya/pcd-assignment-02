package view;

import controller.SourceAnalyzer;
import model.report.Interval;
import model.report.Pair;
import model.report.Report;
import model.resources.Directory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class CLI {

    private SourceAnalyzer sourceAnalyzer;
    public CLI(SourceAnalyzer sourceAnalyzer) {
        this.sourceAnalyzer = sourceAnalyzer;
    }

    public void start(String directory) throws IOException, ExecutionException, InterruptedException {
        Future<Report> futureResult = this.sourceAnalyzer.getReport(new Directory(new File(directory)));
        var report = futureResult.get();
        System.out.println(report);
        var files = report.filesInInterval(new Interval(0, Integer.MAX_VALUE)).stream()
                .map(Pair::getX).collect(Collectors.toList());
        files.forEach(System.out::println);
        System.out.println(files.size());

    }
}
