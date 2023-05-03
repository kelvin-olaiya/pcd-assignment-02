package model;

public interface SourceFile extends Resource {

    long linesCount();

    @Override
    default boolean isDirectory() {
        return false;
    }
}
