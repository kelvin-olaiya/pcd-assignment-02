package model;

import java.util.List;

public interface Directory extends Resource {

    List<Resource> getResources();

    @Override
    default boolean isDirectory() {
        return true;
    }
}
