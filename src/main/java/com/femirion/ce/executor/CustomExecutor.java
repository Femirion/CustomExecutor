package com.femirion.ce.executor;

import com.femirion.ce.task.Task;

import java.util.concurrent.Future;

public interface CustomExecutor<T> {

    Future<T> execute(Task<T> call);

    void shutdown();
}
