package model;

import java.io.File;

public interface Directory extends Resource {

    String getPath();

    File[] getResources();

    @Override
    default boolean isDirectory() {
        return true;
    }
}
