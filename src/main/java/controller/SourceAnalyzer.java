package controller;

import model.report.ObservableReport;
import model.report.Report;
import model.resources.Directory;

public interface SourceAnalyzer {

    Report getReport(Directory directory);

    ObservableReport analyzeSources(Directory directory);
}
