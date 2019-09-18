package com.femirion.ce.executor;

import com.femirion.ce.task.Task;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class MyTaskExecutorTest {


    @Test
    public void executeInTwoThread() {
        // given
        MyTaskExecutor<String> subj = new MyTaskExecutor<>(2);

        // then
        String result1 = subj.execute(new Task<>(LocalDateTime.now(),
                () -> {
                    Thread.sleep(10);
                    return "test1";
                })
        );
        String result2 = subj.execute(new Task<>(LocalDateTime.now(),
                () -> {
                    Thread.sleep(10);
                    return "test2";
                })
        );


        // when
        assertEquals("test1", result1);
        assertEquals("test2", result2);
    }

    @Test
    public void execute() {
        // given
        MyTaskExecutor<String> subj = new MyTaskExecutor<>(1);


        // then
        String result = subj.execute(new Task<>(LocalDateTime.now(), () -> "test"));


        // when
        assertEquals("test", result);
    }
}