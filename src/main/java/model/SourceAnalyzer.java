package model;

import model.report.Report;
import model.resources.Directory;

public interface SourceAnalyzer {

    Report getReport(Directory directory);

    void analyzeSources(Directory directory);
}
