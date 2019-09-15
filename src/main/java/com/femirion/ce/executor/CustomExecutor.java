package com.femirion.ce.executor;

import com.femirion.ce.task.Task;

import java.util.concurrent.CompletableFuture;

public interface CustomExecutor<T> {

    CompletableFuture<T> execute(Task<T> call);

}
