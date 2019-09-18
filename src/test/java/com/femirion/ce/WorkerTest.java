package com.femirion.ce;

import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {

    @Test
    public void setTask() throws Exception {
        // given
        Worker<String> subj = new Worker<>();

        // then
        subj.setTask(() -> "test task");

        // when
        assertEquals("test task", subj.getTask().call());
    }
}