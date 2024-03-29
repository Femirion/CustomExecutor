package com.femirion.ce.executor;

import com.femirion.ce.task.ExecutingTask;
import com.femirion.ce.task.Task;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;

public class CustomExecutor<T> implements Executor<T> {

    private final BlockingQueue<ExecutingTask<T>> taskQueue;
    private final List<Worker<T>> workerList = new CopyOnWriteArrayList<>();

    public CustomExecutor(int countOfWorkers, int initialCapacity) {
        if (countOfWorkers <= 0) {
            throw new IllegalArgumentException("you should set positive count of workers");
        }

        taskQueue = new PriorityBlockingQueue<>(
                initialCapacity,
                Comparator.comparing(ExecutingTask::getTimeMark)

        );

        for (int i = 0; i < countOfWorkers; i++) {
            Worker<T> worker = new Worker<>("worker" + i, taskQueue);
            workerList.add(worker);
            worker.start();
        }
    }

    @Override
    public Future<T> execute(Task<T> task) {
        ExecutingTask<T> executingTask = new ExecutingTask<>(task);
        taskQueue.add(executingTask);
        return executingTask.getTask();
    }

    @Override
    public void shutdown() {
        workerList.forEach(Thread::interrupt);
    }

    /**
     * for JUnit
     */
    List<Worker<T>> getWorkerList() {
        return workerList;
    }

    /**
     * for JUnit
     */
    int getQueueSize() {
        return taskQueue.size();
    }

}
