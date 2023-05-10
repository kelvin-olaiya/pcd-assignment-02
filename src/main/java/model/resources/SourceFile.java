package model.resources;

import java.io.*;
import java.nio.file.Path;

public class SourceFile implements Resource {

    private final String filePath;

    SourceFile(File file) {
        this.filePath = file.getAbsolutePath();
    }

    public long linesCount() {
        long count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (Exception ignored) {}
        return count;
    }

    @Override
    public String getName() {
        return Path.of(filePath).getFileName().toString();
    }
}
