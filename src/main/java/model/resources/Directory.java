package model.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Directory implements Resource {

    private final static Set<String> EXTENSIONS = Set.of("java");
    private final String directoryPath;

    public Directory(File directory) {
        this.directoryPath = directory.getAbsolutePath();
    }

    public List<Resource> getResources() throws IOException {
        return Arrays.stream(Objects.requireNonNull(new File(directoryPath).listFiles()))
                .map(file -> Resource.fromFile(file, EXTENSIONS))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public String getName() {
        return Path.of(directoryPath).getFileName().toString();
    }
}
