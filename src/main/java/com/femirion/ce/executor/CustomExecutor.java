package com.femirion.ce.executor;

import com.femirion.ce.task.Task;

public interface CustomExecutor<T> {

    T execute(Task<T> call);

}
