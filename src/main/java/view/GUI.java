package view;

import controller.SourceAnalyzer;
import model.report.ObservableReport;
import model.report.Report;
import model.resources.Directory;

import java.io.File;
import java.io.IOException;

public class GUI {
    private SourceAnalyzer sourceAnalyzer;
    public GUI(SourceAnalyzer sourceAnalyzer) {
        this.sourceAnalyzer = sourceAnalyzer;
    }

    public void start(String directory) throws IOException {
        ObservableReport report = this.sourceAnalyzer.analyzeSources(new Directory(new File(directory)));
        report.addUpdateHandler(System.out::println);
    }
}
