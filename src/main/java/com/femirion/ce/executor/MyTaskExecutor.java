package com.femirion.ce.executor;

import com.femirion.ce.exception.ExecutorException;
import com.femirion.ce.task.Task;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class MyTaskExecutor<T> implements CustomExecutor<T> {

    private final int countOfWorkers;

    public MyTaskExecutor(int countOfWorkers) {
        this.countOfWorkers = countOfWorkers;
    }


    @Override
    public T execute(Task<T> task) {
        try {
            CompletableFuture<T> future = new CompletableFuture<>();
            future.complete(task.getExecutedTask().call());
            return future.get();
        } catch (Exception ex) {
            log.debug("error with calling task, timeMark=" + task.getTimeMark());
            throw new ExecutorException(ex.getMessage());
        }
    }

}
