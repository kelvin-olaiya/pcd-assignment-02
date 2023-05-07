package view;

import controller.SourceAnalyzer;
import model.report.Report;
import model.resources.Directory;

import java.io.File;
import java.io.IOException;

public class CLI {

    private SourceAnalyzer sourceAnalyzer;
    public CLI(SourceAnalyzer sourceAnalyzer) {
        this.sourceAnalyzer = sourceAnalyzer;
    }

    public void start() throws IOException {
        Report report = this.sourceAnalyzer.getReport(Directory.fromFile(new File("/home/kelvin/")));
        System.out.println(report);
    }
}
