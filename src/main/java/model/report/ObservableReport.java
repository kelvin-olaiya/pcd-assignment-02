package model.report;

import java.util.function.Consumer;

public interface ObservableReport extends Report {

    void addUpdateHandler(Consumer<Report> onUpdateHandler);

    void addOnAbortHandler(Runnable onAbortHandler);

    void addOnCompleteHandler(Runnable onCompleteHandler);

    void abort();

}
