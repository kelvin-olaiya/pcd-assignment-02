package model;

import model.report.Report;

public interface SourceAnalyzer {

    Report getReport(Directory directory);

    void analyzeSources(Directory directory);
}
