package com.femirion.ce.task;

import java.time.LocalDateTime;
import java.util.concurrent.FutureTask;

public class ExecutingTask<T> {

    private LocalDateTime timeMark;
    private FutureTask<T> task;

    public ExecutingTask(Task<T> task) {
        timeMark = task.getTimeMark();
        this.task = new FutureTask<>(task.getTask());
    }

    public LocalDateTime getTimeMark() {
        return timeMark;
    }

    public FutureTask<T> getTask() {
        return task;
    }
}
