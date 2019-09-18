package com.femirion.ce;


import com.femirion.ce.executor.CustomExecutor;
import com.femirion.ce.executor.Executor;
import com.femirion.ce.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {

    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LocalDateTime time1 = LocalDateTime.of(1999, 11, 21, 14, 21, 19);
        LocalDateTime time2 = LocalDateTime.of(2001, 10, 2, 3, 15, 15);
        LocalDateTime time3 = LocalDateTime.of(1990, 3, 20, 17, 14, 12);

        Task<String> task1 = new Task<>(time1, () -> "task1");
        Task<String> task2 = new Task<>(time2, () -> "task2");
        Task<String> task3 = new Task<>(time3, () -> "task3");


        Executor<String> executor = new CustomExecutor<>(1, 5);


        Future<String> result1 = executor.execute(task1);
        Future<String> result2 = executor.execute(task2);
        Future<String> result3 = executor.execute(task3);

        String r1 = "result1=" + result1.get();
        String r2 = "result2=" + result2.get();
        String r3 = "result3=" + result3.get();

        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
        logger.info(r1);
        logger.info(r2);
        logger.info(r3);

        executor.shutdown();
    }


}
