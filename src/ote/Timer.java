
package ote;

import java.util.concurrent.TimeUnit;

public class Timer {

    long starts;

    private Timer() {
        reset();
    }

    public static Timer start() {
        return new Timer();
    }

    public Timer reset() {
        starts = System.nanoTime();
        return this;
    }

    // Elapsed time in default format
    public long elapsed_time() {
        long ends = System.nanoTime();
        return ends - starts;
    }

    // Elapsed Time in custom format (i.e TimeUnit.SECONDS, TimeUnit.NANOSECONDS)
    public long elapsed_time(TimeUnit unit) {
        return unit.convert(elapsed_time(), TimeUnit.NANOSECONDS);
    }

    public long nanoToMillis() {
        return (elapsed_time() / 1000) / 1000;
    }

    public long nanoToSeconds() {
        return ((elapsed_time() / 1000) / 1000) / 1000;
    }

    // Elapsed time in minutes and seconds
    public String toMinuteSeconds() {
        return String.format("%d min, %d sec", elapsed_time(TimeUnit.MINUTES), elapsed_time(TimeUnit.SECONDS) - elapsed_time(TimeUnit.MINUTES));
    }

}