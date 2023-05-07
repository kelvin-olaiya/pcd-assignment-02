package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface Directory extends Resource {

    List<Resource> getResources();

    @Override
    default boolean isDirectory() {
        return true;
    }

    static Directory fromFile(File file) throws IOException {
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("The provided file is not a directory");
        }
        List<Directory> directories = new ArrayList<>();
        List<SourceFile> sourceFiles = new ArrayList<>();
        for (File entry : Objects.requireNonNull(file.listFiles())) {
            if (entry.isDirectory()) {
                directories.add(Directory.fromFile(entry));
            } else if (entry.getName().endsWith(".java")) {
                sourceFiles.add(SourceFile.fromFile(entry));
            }
        }
        return new DirectoryImpl(directories, sourceFiles);
    }
}
