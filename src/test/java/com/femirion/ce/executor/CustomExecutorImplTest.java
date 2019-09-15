package com.femirion.ce.executor;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class CustomExecutorImplTest {

    @Test
    public void execute() throws ExecutionException, InterruptedException {
        // given
        CustomExecutorImpl<String> subj = new CustomExecutorImpl<>();


        // then
        CompletableFuture<String> result = subj.execute(LocalDateTime.now(), () -> "test");


        // when
        assertEquals("test", result.get());
    }
}