package model.report;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ObservableReport extends Report {

    void addUpdateHandler(BiConsumer<List<StatLine>, List<String>> onUpdateHandler);

    void addOnAbortHandler(Runnable onAbortHandler);

    void addOnCompleteHandler(Runnable onCompleteHandler);

    void abort();

}
