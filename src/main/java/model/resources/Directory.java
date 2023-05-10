package model.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Directory implements Resource {

    private final static Set<String> EXTENSIONS = Set.of("java");
    private final File directory;

    public Directory(File directory) {
        this.directory = directory;
    }

    public List<Resource> getResources() throws IOException {
        return Files.list(directory.toPath())
                .map(Path::toFile)
                .map(f -> Resource.fromFile(f, EXTENSIONS))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public String getName() {
        return this.directory.getName();
    }
}
