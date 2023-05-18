import controller.SearchConfiguration;
import controller.SourceAnalyzer;
import controller.executors.ExecutorSourceAnalyzer;
import controller.event_loop.EventLoopSourceAnalyzer;
import controller.virtual_threads.VirtualThreadSourceAnalyzer;
import controller.reactive.RxSourceAnalyzer;
import view.CLI;
import view.GUI;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String... args) throws IOException, ExecutionException, InterruptedException {
        if (args.length == 0) {
            new GUI();
        } else {
            String path = args[0];
            int maxLines = Integer.parseInt(args[1]);
            int numIntervals = Integer.parseInt(args[2]);
            int numLongestFiles = Integer.parseInt(args[3]);
            SearchConfiguration searchConfiguration = new SearchConfiguration(numIntervals, maxLines, numLongestFiles);
            SourceAnalyzer sourceAnalyzer = new VirtualThreadSourceAnalyzer(searchConfiguration);
            // SourceAnalyzer sourceAnalyzer = new EventLoopSourceAnalyzer(searchConfiguration);
            // SourceAnalyzer sourceAnalyzer = new ExecutorSourceAnalyzer(searchConfiguration);
            // SourceAnalyzer sourceAnalyzer = new RxSourceAnalyzer(searchConfiguration);
            System.out.println(path);
            new CLI(sourceAnalyzer).start(path);
        }
    }
}