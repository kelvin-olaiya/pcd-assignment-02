package model;

import java.util.ArrayList;
import java.util.List;

public class SourceFileImpl implements SourceFile {

    private final List<String> lines;
    private final String name;

    SourceFileImpl(String name, List<String> lines) {
        this.name = name;
        this.lines = new ArrayList<>(lines);
    }

    @Override
    public long linesCount() {
        return lines.size();
    }

    @Override
    public String getName() {
        return this.name;
    }
}
