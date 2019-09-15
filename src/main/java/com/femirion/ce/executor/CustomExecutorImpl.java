package com.femirion.ce.executor;

import com.femirion.ce.exception.ExecutorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public class CustomExecutorImpl<T> implements CustomExecutor<T> {

    private final static Logger logger = LoggerFactory.getLogger(CustomExecutorImpl.class);

    @Override
    public CompletableFuture<T> execute(LocalDateTime timeMark, Callable<T> c) {
        try {
            CompletableFuture<T> future = new CompletableFuture<>();
            future.complete(c.call());
            return future;
        } catch (Exception ex) {
            logger.debug("error with calling task, timeMark=" + timeMark);
            throw new ExecutorException(ex.getMessage());
        }
    }

}
