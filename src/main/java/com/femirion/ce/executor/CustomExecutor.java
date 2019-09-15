package com.femirion.ce.executor;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public interface CustomExecutor<T> {

    CompletableFuture<T> execute(LocalDateTime timeMark, Callable<T> call);

}
