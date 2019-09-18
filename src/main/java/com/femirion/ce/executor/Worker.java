package com.femirion.ce.executor;

import com.femirion.ce.task.ExecutingTask;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Slf4j
public class Worker<T> extends Thread {

    private final String workerName;
    private final BlockingQueue<ExecutingTask<T>> taskQueue;
    private volatile boolean finish = false;
    // for JUnit
    private List<ExecutingTask<T>> finishedTask = new ArrayList<>();

    public Worker(String workerName, BlockingQueue<ExecutingTask<T>> taskQueue) {
        this.workerName = workerName;
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        log.debug("worker=" + workerName + " has just started to do work, my name is " + workerName);
        while (!isInterrupted()) {
            try {
                ExecutingTask<T> executingTask = taskQueue.take();
                log.debug("worker=" + workerName + " run task, taskTimeMark=" + executingTask.getTimeMark());
                executingTask.getTask().run();
                finishedTask.add(executingTask);
            } catch (InterruptedException ex) {
                // if we catch InterruptedException we should stop the thread
                log.debug("worker=" + workerName + " has finished!");
                finish = true;
                return;
            }
        }
    }

    /**
     * for JUnit
     */
    boolean isFinish() {
        return finish;
    }

    /**
     * for JUnit
     */
    List<ExecutingTask<T>> getFinishedTasks() {
        return finishedTask;
    }

}
