package model.resources;

import com.google.common.io.Files;

import java.io.File;
import java.util.Optional;
import java.util.Set;

public interface Resource {

    String getName();

    static Optional<Resource> fromFile(File file, Set<String> extensions) {
        if (file.isDirectory()) {
            return Optional.of(new Directory(file));
        } else if (file.isFile() && extensions.contains(Files.getFileExtension(file.getAbsolutePath()))) {
            return Optional.of(new SourceFile(file));
        }
        return Optional.empty();
    }
}
