package model.report;

public interface CompletableReport extends ObservableReport {

    void notifyUpdate();

    void notifyCompletion();
}
