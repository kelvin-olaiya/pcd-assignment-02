package model.report;

public class StatLine {
    private final Interval interval;
    private final long numLines;

    public StatLine(Interval interval, long numLines) {
        this.interval = interval;
        this.numLines = numLines;
    }

    public Interval getInterval() {
        return interval;
    }

    public long getNumLines() {
        return numLines;
    }

    @Override
    public String toString() {
        return interval + ": " + numLines;
    }
}
