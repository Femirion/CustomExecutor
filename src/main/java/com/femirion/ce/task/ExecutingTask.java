package com.femirion.ce.task;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.concurrent.FutureTask;

@Value
public class ExecutingTask<T> {

    private LocalDateTime timeMark;
    private FutureTask<T> task;

    public ExecutingTask(Task<T> task) {
        timeMark = task.getTimeMark();
        this.task = new FutureTask<>(task.getTask());
    }

}
