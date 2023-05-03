package model;

import java.io.File;

public class DirectoryImpl implements Directory {

    private final String path;

    public DirectoryImpl(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public File[] getResources() {
        return new File(path).listFiles();
    }
}
