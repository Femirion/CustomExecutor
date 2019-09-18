package com.femirion.ce.task;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

@Value
public class Task<T> {

    private final LocalDateTime timeMark;
    private final Callable<T> task;

    public Task(LocalDateTime timeMark, Callable<T> task) {
        this.timeMark = timeMark;
        this.task = task;
    }

}
