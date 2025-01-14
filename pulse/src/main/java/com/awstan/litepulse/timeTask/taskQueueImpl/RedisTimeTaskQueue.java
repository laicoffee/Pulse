package com.awstan.litepulse.timeTask.taskQueueImpl;

import com.awstan.litepulse.timeTask.TimeTaskContext;
import com.awstan.litepulse.timeTask.TimeTaskQueue;
import com.awstan.litepulse.timeTask.TimeTaskQueueType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author pw7563
 * @Date 2025/1/14 11:01
 * usage
 */
@Component
public class RedisTimeTaskQueue implements TimeTaskQueue {

    @Autowired
    private RedisTemplate redisTemplate;

    // redis队列名称
    private static final String redis_queue_key = "pulse_time_task_queue";

    @Override
    public TimeTaskQueueType getType() {
        return TimeTaskQueueType.REDIS;
    }

    @Override
    public void push(TimeTaskContext taskContext) {
        redisTemplate.opsForList().rightPush(redis_queue_key, taskContext);
    }

    @Override
    public TimeTaskContext poll() {
        return (TimeTaskContext) redisTemplate.opsForList().leftPop(redis_queue_key);
    }

    @Override
    public List<TimeTaskContext> poll(int size) {
        List<TimeTaskContext> taskContexts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TimeTaskContext task = poll();
            if(task == null){
                break;
            }
            taskContexts.add(task);
        }
        return taskContexts;
    }

    @Override
    public int size() {
        return Math.toIntExact(redisTemplate.opsForList().size(redis_queue_key));
    }
}
