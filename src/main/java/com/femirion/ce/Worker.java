package com.femirion.ce;

import java.util.concurrent.Callable;

public class Worker<T> extends Thread {

    private volatile Callable<T> task;

    public Worker() {
    }


    @Override
    public void start() {
    }

    public void setTask(Callable<T> task) {
        this.task = task;
    }

    /**
     * for JUnit
     */
    Callable<T> getTask() {
        return task;
    }

}
