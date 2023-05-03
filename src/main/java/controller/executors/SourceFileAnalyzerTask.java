package controller.executors;

import model.report.Report;
import model.report.ReportImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.RecursiveTask;

public class SourceFileAnalyzerTask extends RecursiveTask<Report> {

    private final File sourceFile;
    private final SearchConfiguration configuration;

    SourceFileAnalyzerTask(File sourceFile, SearchConfiguration configuration) {
        this.sourceFile = sourceFile;
        this.configuration = configuration;
    }

    @Override
    protected Report compute() {
        long lines = 0;
        try (var reader = new BufferedReader(new FileReader(sourceFile))) {
            while (reader.readLine() != null) {
                lines++;
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return new ReportImpl(configuration, sourceFile.getName(), lines);
    }
}
