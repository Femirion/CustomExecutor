package com.femirion.ce.task;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.concurrent.FutureTask;

@Getter
public class ExecutedTask<T> {

    private volatile LocalDateTime timeMark;
    private volatile FutureTask<T> task;

    public ExecutedTask(Task<T> task) {
        timeMark = task.getTimeMark();
        this.task = new FutureTask<>(task.getTask());
    }

}
