package model;

import java.util.ArrayList;
import java.util.List;

public class SourceFileImpl implements SourceFile {

    private final List<String> lines;

    public SourceFileImpl(List<String> lines) {
        this.lines = new ArrayList<>(lines);
    }

    @Override
    public long linesCount() {
        return lines.size();
    }
}
