package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public interface SourceFile extends Resource {

    long linesCount();

    @Override
    default boolean isDirectory() {
        return false;
    }

    static SourceFile fromFile(File file) throws IOException {
        if (!file.isFile()) {
            throw new IllegalArgumentException("The provided file is not a source file");
        }
        return new SourceFileImpl(file.getName(), Files.readAllLines(file.toPath()));
    }
}
