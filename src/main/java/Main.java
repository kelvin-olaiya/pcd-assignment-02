import controller.SourceAnalyzer;
import controller.SearchConfiguration;
import controller.event_loop.SourceAnalyzerEventLoop;
import controller.executors.SourceAnalyzerExecutor;
import view.CLI;
import view.GUI;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String... args) throws IOException, ExecutionException, InterruptedException {
        if (args.length == 0) {
            new GUI();
            // new SourceAnalyzerExecutor()
        } else {
            String path = args[0];
            int maxLines = Integer.parseInt(args[1]);
            int numIntervals = Integer.parseInt(args[2]);
            int numLongestFiles = Integer.parseInt(args[3]);
            SourceAnalyzer sourceAnalyzer = new SourceAnalyzerExecutor();
            //SourceAnalyzer sourceAnalyzer = new SourceAnalyzerEventLoop(new SearchConfiguration(numIntervals, maxLines));
            System.out.println(path);
            new CLI(sourceAnalyzer).start(path);
        }
    }
}