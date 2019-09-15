package com.femirion.ce.executor;

import com.femirion.ce.exception.ExecutorException;
import com.femirion.ce.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class MyTaskExecutor<T> implements CustomExecutor<T> {

    private final static Logger logger = LoggerFactory.getLogger(MyTaskExecutor.class);

    @Override
    public T execute(Task<T> task) {
        try {
            CompletableFuture<T> future = new CompletableFuture<>();
            future.complete(task.getExecutedTask().call());
            return future.get();
        } catch (Exception ex) {
            logger.debug("error with calling task, timeMark=" + task.getTimeMark());
            throw new ExecutorException(ex.getMessage());
        }
    }

}
