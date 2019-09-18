package com.femirion.ce.task;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.concurrent.FutureTask;

@Value
public class ExecutedTask<T> {

    private LocalDateTime timeMark;
    private FutureTask<T> task;

    public ExecutedTask(Task<T> task) {
        timeMark = task.getTimeMark();
        this.task = new FutureTask<>(task.getTask());
    }

}
