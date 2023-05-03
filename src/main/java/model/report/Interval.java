package model.report;

import java.util.Objects;

public class Interval {

    private final int lowerBound;
    private final int upperBound;

    public Interval(int lowerBound, int upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public boolean accept(long lines) {
        return lines >= lowerBound && lines < upperBound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return lowerBound == interval.lowerBound && upperBound == interval.upperBound;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lowerBound, upperBound);
    }
}
