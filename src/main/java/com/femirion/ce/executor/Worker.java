package com.femirion.ce.executor;

import com.femirion.ce.task.ExecutedTask;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Slf4j
public class Worker<T> extends Thread {

    private final String workerName;
    private final BlockingQueue<ExecutedTask<T>> taskQueue;
    private volatile boolean finish = false;
    // for JUnit
    private List<ExecutedTask<T>> finishedTask = new ArrayList<>();

    public Worker(String workerName, BlockingQueue<ExecutedTask<T>> taskQueue) {
        this.workerName = workerName;
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        log.debug("worker=" + workerName + " has just started to do work, my name is " + workerName);
        while (!isInterrupted()) {
            try {
                ExecutedTask<T> executedTask = taskQueue.take();
                log.debug("worker=" + workerName + " run task, taskTimeMark=" + executedTask.getTimeMark());
                executedTask.getTask().run();
                finishedTask.add(executedTask);
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
    List<ExecutedTask<T>> getFinishedTasks() {
        return finishedTask;
    }

}
