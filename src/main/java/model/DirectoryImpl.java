package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DirectoryImpl implements Directory {

    private final List<Directory> subDirectories;
    private final List<SourceFile> sourceFiles;

    public DirectoryImpl(List<Directory> subDirectories, List<SourceFile> sourceFiles) {
        this.subDirectories = new ArrayList<>(subDirectories);
        this.sourceFiles = new ArrayList<>(sourceFiles);
    }

    @Override
    public List<Resource> getResources() {
        return Stream.concat(this.sourceFiles.stream(), this.subDirectories.stream()).toList();
    }
}
