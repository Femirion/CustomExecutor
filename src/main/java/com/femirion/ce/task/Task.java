package com.femirion.ce.task;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

public class Task<T> {

    private final LocalDateTime timeMark;
    private final Callable<T> task;

    public Task(LocalDateTime timeMark, Callable<T> task) {
        this.timeMark = timeMark;
        this.task = task;
    }

    public LocalDateTime getTimeMark() {
        return timeMark;
    }

    public Callable<T> getTask() {
        return task;
    }
}
