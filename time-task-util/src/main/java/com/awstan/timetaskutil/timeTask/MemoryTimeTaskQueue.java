package com.awstan.timetaskutil.timeTask;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author pw7563
 * @Date 2025/1/10 14:08
 * usage 基于内存的任务队列
 */
@Component
public class MemoryTimeTaskQueue implements TimeTaskQueue{

    private final Queue<TimeTaskContext> queue = new LinkedBlockingQueue<>();

    @Override
    public TimeTaskQueueType getType() {
        return TimeTaskQueueType.MEMORY;
    }

    @Override
    public void push(TimeTaskContext taskContext) {
        queue.offer(taskContext);
    }

    @Override
    public TimeTaskContext poll() {
        return queue.poll();
    }

    @Override
    public List<TimeTaskContext> poll(int size) {
        List<TimeTaskContext> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TimeTaskContext taskContext = queue.poll();
            if (taskContext!= null) {
                list.add(taskContext);
            }
        }
        return list;
    }

    @Override
    public int size() {
        return queue.size();
    }
}
