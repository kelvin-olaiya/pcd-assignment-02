package controller.event_loop;

import controller.utils.SearchConfiguration;
import io.vertx.core.AbstractVerticle;
import model.resources.Directory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class AbstractVerticleSourceAnalyzer extends AbstractVerticle {

    private final Directory directory;
    protected final SearchConfiguration configuration;
    private final ResourceCounter counter = new ResourceCounter();
    protected boolean stopped = false;

    public AbstractVerticleSourceAnalyzer(Directory directory, SearchConfiguration configuration) {
        this.directory = directory;
        this.configuration = configuration;
    }

    @Override
    public void start() {
        this.walk(this.directory.getAbsolutePath());
    }

    private void onNewResourcesToAnalyze() {
        counter.inc();
    }

    private void onFolderVisiting() {
        counter.dec();
    }

    protected abstract void aggregateResult(String file, int lines);
    protected abstract void complete();

    private void analyzeFile(String file, String content) {
        Matcher m = Pattern.compile("\r\n|\r|\n").matcher(content);
        int lines = 0;
        while (m.find()) {
            lines++;
        }
        aggregateResult(file, lines);
    }

    private void onFileAnalyzed() {
        counter.dec();
        if (counter.noMoreResources()) {
            complete();
            this.vertx.close();
        }
    }

    private void walk(String resource) {
        if (stopped) {
            return;
        }
        this.onNewResourcesToAnalyze();
        vertx.fileSystem().props(resource, props -> {
            if (props.result() != null && props.result().isDirectory()) {
                vertx.fileSystem().readDir(resource, list -> {
                    if (list.result() != null) {
                        this.onFolderVisiting();
                        for (var sub : list.result()) {
                            walk(sub);
                        }
                    }
                });
            } else if (props.result() != null && props.result().isRegularFile() && resource.endsWith(".java")) { // TODO: extract
                vertx.fileSystem().readFile(resource, r -> {
                    analyzeFile(resource, r.result().toString());
                    onFileAnalyzed();
                });
            } else {
                onFileAnalyzed();
            }
        });
    }

}
