package com.femirion.ce.task;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

@Data
@AllArgsConstructor
public class Task<T> {

    private LocalDateTime timeMark;
    private Callable<T> executedTask;

}
