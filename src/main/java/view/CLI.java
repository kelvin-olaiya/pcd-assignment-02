package view;

import controller.SourceAnalyzer;
import model.report.Report;
import model.resources.Directory;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CLI {

    private final SourceAnalyzer sourceAnalyzer;
    public CLI(SourceAnalyzer sourceAnalyzer) {
        this.sourceAnalyzer = sourceAnalyzer;
    }

    public void start(String directory) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        Future<Report> futureResult = this.sourceAnalyzer.getReport(new Directory(new File(directory)));
        Report report = futureResult.get();
        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println(report);
        System.out.println();
        System.out.println("The longest files are:");
        for (var line : report.longestFiles()) {
            System.out.println(line.getX() + "\t" + line.getY() + " lines");
        }
        System.out.println();
        System.out.println("Done in " + totalTime + " ms");
    }
}
