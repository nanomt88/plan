package com.nanomt88.demo.dubbo.async;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsyncTaskData<T>{

    private long start;

    private T data;
}
