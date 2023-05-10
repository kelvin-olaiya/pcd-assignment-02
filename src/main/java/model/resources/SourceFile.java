package model.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class SourceFile implements Resource {

    private final File file;

    SourceFile(File file) {
        this.file = file;
    }

    public long linesCount() {
        long count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (Exception ignored) {}
        return count;
    }

    @Override
    public String getName() {
        return this.file.getName();
    }
}
