import controller.SourceAnalyzer;
import controller.executors.SourceAnalyzerExecutor;
import view.CLI;

import java.io.IOException;

public class Main {
    public static void main(String... args) throws InterruptedException, IOException {
        SourceAnalyzer sourceAnalyzer = new SourceAnalyzerExecutor();
        var view = new CLI(sourceAnalyzer);
        view.start();
    }
}