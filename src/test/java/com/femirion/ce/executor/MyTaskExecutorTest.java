package com.femirion.ce.executor;

import com.femirion.ce.task.Task;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;

public class MyTaskExecutorTest {


    @Test
    public void executeInTwoThread() throws Exception {
        // given
        MyTaskExecutor<String> subj = new MyTaskExecutor<>();


        // then
        CompletableFuture<String> result1 = subj.execute(new Task<>(LocalDateTime.now(), () -> "test1"));
        CompletableFuture<String> result2 = subj.execute(new Task<>(LocalDateTime.now(), () -> "test2"));


        // when
        assertEquals("test1", result1.get());
        assertEquals("test2", result2.get());
    }

    @Test
    public void execute() throws Exception {
        // given
        MyTaskExecutor<String> subj = new MyTaskExecutor<>();


        // then
        CompletableFuture<String> result = subj.execute(new Task<>(LocalDateTime.now(), () -> "test"));


        // when
        assertEquals("test", result.get());
    }
}