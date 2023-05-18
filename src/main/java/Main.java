import controller.event_loop.EventLoopSourceAnalyzer;
import controller.executors.ExecutorSourceAnalyzer;
import controller.reactive.RxSourceAnalyzer;
import controller.utils.SearchConfiguration;
import controller.SourceAnalyzer;
import controller.virtual_threads.VTSourceAnalyzer;
import view.CLI;
import view.GUI;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String... args) throws ExecutionException, InterruptedException {
        System.out.println(Arrays.toString(args));
        if (args.length == 1) {
            Class<?> sourceAnalyzer = switch (args[0]) {
                case "executors" -> ExecutorSourceAnalyzer.class;
                case "virtual" -> VTSourceAnalyzer.class;
                case "eventLoop" -> EventLoopSourceAnalyzer.class;
                case "reactive" -> RxSourceAnalyzer.class;
                default -> throw new IllegalStateException("Unexpected value: " + args[0]);
            };
            new GUI(sourceAnalyzer);
        } else {
            var arguments = args[1].split(" ");
            String path = arguments[0];
            int maxLines = Integer.parseInt(arguments[1]);
            int numIntervals = Integer.parseInt(arguments[2]);
            int numLongestFiles = Integer.parseInt(arguments[3]);
            SearchConfiguration searchConfiguration = new SearchConfiguration(numIntervals, maxLines, numLongestFiles);
            SourceAnalyzer sourceAnalyzer = switch (args[0]) {
                case "executors" -> new ExecutorSourceAnalyzer(searchConfiguration);
                case "virtual" -> new VTSourceAnalyzer(searchConfiguration);
                case "eventLoop" -> new EventLoopSourceAnalyzer(searchConfiguration);
                case "reactive" -> new RxSourceAnalyzer(searchConfiguration);
                default -> throw new IllegalStateException("Unexpected value: " + args[0]);
            };
            new CLI(sourceAnalyzer).start(path);
        }
    }
}