package controller;

import model.report.ObservableReport;
import model.report.Report;
import model.resources.Directory;

import java.util.concurrent.Future;

public interface SourceAnalyzer {

    Future<Report> getReport(Directory directory);

    ObservableReport analyzeSources(Directory directory);
}
