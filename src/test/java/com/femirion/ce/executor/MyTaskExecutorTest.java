package com.femirion.ce.executor;

import com.femirion.ce.task.Task;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class MyTaskExecutorTest {

    @Test
    public void execute() throws ExecutionException, InterruptedException {
        // given
        MyTaskExecutor<String> subj = new MyTaskExecutor<>();


        // then
        CompletableFuture<String> result = subj.execute(new Task<>(LocalDateTime.now(), () -> "test"));


        // when
        assertEquals("test", result.get());
    }
}