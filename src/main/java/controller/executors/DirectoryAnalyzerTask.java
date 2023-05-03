package controller.executors;

import model.report.Report;

import java.io.File;
import java.util.concurrent.RecursiveTask;

public class DirectoryAnalyzerTask extends RecursiveTask<Report> {

    private final File directory;

    DirectoryAnalyzerTask(File directory) {
        this.directory = directory;
    }

    @Override
    protected Report compute() {
        // TODO
        return null;
    }
}
