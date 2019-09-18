package com.femirion.ce.executor;

import com.femirion.ce.exception.ExecutorException;
import com.femirion.ce.task.ExecutedTask;
import com.femirion.ce.task.Task;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomExecutorTest {

    @Test
    public void createExecutor() {
        // given
        int workersCount = 5;

        // then
        CustomExecutor<String> subj = new CustomExecutor<>(workersCount, 10);

        // when
        assertEquals(workersCount, subj.getWorkerList().size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void createExecutorWithWrongCount() {
        // given
        int workersCount = -1;

        // then
        CustomExecutor<String> subj = new CustomExecutor<>(workersCount, 10);

        // when
        assertEquals(workersCount, subj.getWorkerList().size());
    }

    @Test
    public void shutdownExecutor() throws Exception {
        // given
        int workersCount = 5;
        CustomExecutor<String> executor = new CustomExecutor<>(workersCount, 10);

        List<Worker<String>> workers = executor.getWorkerList();
        // check that all workers are prepare for work
        assertEquals(false, workers.get(0).isFinish());
        assertEquals(false, workers.get(1).isFinish());
        assertEquals(false, workers.get(2).isFinish());
        assertEquals(false, workers.get(3).isFinish());
        assertEquals(false, workers.get(4).isFinish());


        // time for starting executor
        Thread.sleep(1000);

        // then
        executor.shutdown();

        // time for turning down executor
        Thread.sleep(1000);

        // when
        // check that all workers are finished
        assertEquals(true, workers.get(0).isFinish());
        assertEquals(true, workers.get(1).isFinish());
        assertEquals(true, workers.get(2).isFinish());
        assertEquals(true, workers.get(3).isFinish());
        assertEquals(true, workers.get(4).isFinish());
    }

    @Test
    public void execute() throws Exception {
        // given
        CustomExecutor<String> subj = new CustomExecutor<>(1, 10);


        // then
        Future<String> result = subj.execute(new Task<>(LocalDateTime.now(), () -> "test"));

        // when
        assertEquals("test", result.get());
    }

    @Test(expected = ExecutionException.class)
    public void handleError() throws Exception {
        // given
        CustomExecutor<String> subj = new CustomExecutor<>(1, 10);


        // then
        Future<String> result = subj.execute(new Task<>(LocalDateTime.now(), () -> {
            throw new ExecutorException("");
        }));

        // when
        assertEquals("test", result.get());
    }

    /**
     * We should check priority
     *
     * There is ONLY ONE thread!!!!
     * We create 5 tasks with different time. Fist task has delay,
     * another 4 tasks will be added in queue, and it will sort them by the time
     */
    @Test
    public void checkPriority() throws Exception {
        // given
        CustomExecutor<String> subj = new CustomExecutor<>(1, 10);

        LocalDateTime time1 = LocalDateTime.of(1999, 12, 21, 14, 21, 19);
        LocalDateTime time2 = LocalDateTime.of(2001, 10, 2, 3, 15, 15);
        LocalDateTime time3 = LocalDateTime.of(1990, 3, 20, 17, 14, 12);
        LocalDateTime time4 = LocalDateTime.of(2017, 5, 11, 2, 4, 5);
        LocalDateTime time5 = LocalDateTime.of(2011, 12, 1, 23, 0, 2);


        // then
        Future<String> result1 = subj.execute(new Task<>(time1, () -> {
            Thread.sleep(200);
            return "test1";
        }));
        // add timeout that worker start to do first task
        Thread.sleep(100);

        // this task will be sorted by the time
        Future<String> result2 = subj.execute(new Task<>(time2, () -> "test2"));
        Future<String> result3 = subj.execute(new Task<>(time3, () -> "test3"));
        Future<String> result4 = subj.execute(new Task<>(time4, () -> "test4"));
        Future<String> result5 = subj.execute(new Task<>(time5, () -> "test5"));

        // timout that worker finish all tasks
        Thread.sleep(300);

        List<ExecutedTask<String>> tasks = subj.getWorkerList().get(0).getFinishedTasks();

        // when
        // time1 - first task
        assertEquals(time1, tasks.get(0).getTimeMark());
        // time3, time2, time5, time4
        assertEquals(time3, tasks.get(1).getTimeMark());
        assertEquals(time2, tasks.get(2).getTimeMark());
        assertEquals(time5, tasks.get(3).getTimeMark());
        assertEquals(time4, tasks.get(4).getTimeMark());
    }

    @Test
    public void executeTwoTaskInTwoThread() throws Exception {
        // given
        CustomExecutor<String> subj = new CustomExecutor<>(2, 10);

        // then
        Future<String> result1 = subj.execute(new Task<>(LocalDateTime.now(), () -> "test1"));
        Future<String> result2 = subj.execute(new Task<>(LocalDateTime.now(), () -> "test2"));

        // when
        assertEquals("test1", result1.get());
        assertEquals("test2", result2.get());
    }

    @Test
    public void executeTwoTaskInOneThread() throws Exception {
        // given
        CustomExecutor<String> subj = new CustomExecutor<>(1, 10);

        // then
        Future<String> result1 = subj.execute(new Task<>(LocalDateTime.now(), () -> "test1"));
        Future<String> result2 = subj.execute(new Task<>(LocalDateTime.now(), () -> "test2"));

        // when
        assertEquals("test1", result1.get());
        assertEquals("test2", result2.get());
    }

    @Test
    public void execute10TaskInTwoThread() throws Exception {
        // given
        CustomExecutor<String> subj = new CustomExecutor<>(2, 10);

        int[] trick = new int[1];
        List<Future<String>> results = new ArrayList<>();

        // then
        for (int i = 0; i < 10; i++) {
            int value = trick[0];
            Future<String> result = subj.execute(new Task<>(LocalDateTime.now(), () -> "test" + value));
            trick[0] = value + 1;
            results.add(result);
        }

        // when
        for (int i = 0; i < 10; i++) {
            assertEquals("test" + i, results.get(i).get());
        }
    }

    /**
     * We should check that tasks will be add in queue if there will slow tasks
     *
     * Each task has timeout (10 ms) therefore queue will be grow,
     * but in the end, all tasks will be finished, and queue should be empty
     *
     */
    @Test
    public void executeSlowTasks() throws Exception {
        // given
        CustomExecutor<String> subj = new CustomExecutor<>(3, 10);

        int[] trick = new int[1];
        List<Future<String>> results = new ArrayList<>();
        List<Integer> countTaskInQueue =  new ArrayList<>();

        // then
        for (int i = 0; i < 100; i++) {
            int value = trick[0];
            Future<String> result = subj.execute(new Task<>(LocalDateTime.now(), () -> {
                // small sleep
                Thread.sleep(10);
                return "test" + value;
            }));
            trick[0] = value + 1;
            results.add(result);
            countTaskInQueue.add(subj.getQueueSize());
        }

        // when
        for (int i = 0; i < 100; i++) {
            assertEquals("test" + i, results.get(i).get());
        }
        // max count of elements in once of time
        int maxValue = countTaskInQueue.stream()
                .max(Integer::compareTo)
                .orElseThrow(RuntimeException::new);


        assertTrue(maxValue > 1);
        // queue is empty
        assertEquals(0, subj.getQueueSize());
    }

}