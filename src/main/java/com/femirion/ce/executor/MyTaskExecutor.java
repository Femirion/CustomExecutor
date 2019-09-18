package com.femirion.ce.executor;

import com.femirion.ce.Worker;
import com.femirion.ce.exception.ExecutorException;
import com.femirion.ce.task.Task;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class MyTaskExecutor<T> implements CustomExecutor<T> {

    private BlockingQueue<Worker<T>> workers;
    // for unit-test
    private List<Worker<T>> workerList = new ArrayList<>();

    public MyTaskExecutor(int countOfWorkers) {
        if (countOfWorkers <= 0) {
            throw new IllegalArgumentException("you should set positive count of workers");
        }

        workers = new ArrayBlockingQueue<>(countOfWorkers);
        for (int i = 0; i < countOfWorkers; i++) {
            Worker<T> worker = new Worker<>();
            workers.add(worker);
            workerList.add(worker);
        }
    }


    @Override
    public T execute(Task<T> task) {
        try {
            CompletableFuture<T> future = new CompletableFuture<>();
            future.complete(task.getExecutedTask().call());
            return future.get();
        } catch (Exception ex) {
            log.debug("error with calling task, timeMark=" + task.getTimeMark());
            throw new ExecutorException(ex.getMessage());
        }
    }

    /**
     * for JUnit
     */
    List<Worker<T>> getWorkerList() {
        return workerList;
    }

}
