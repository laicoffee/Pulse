package com.awstan.litepulse.timeTask;

import java.util.List;

/**
 * @Author pw7563
 * @Date 2025/1/10 14:03
 * usage 时间任务队列接口
 */
public interface TimeTaskQueue {

    /**
     *  任务类型
     */
    TimeTaskQueueType getType();

    /**
     *  向队列添加任务
     */
    void push(TimeTaskContext taskContext);

    /**
     *  弹出队列头部任务
     * @return
     */
    TimeTaskContext poll();

    /**
     *  从队列中弹出指定数量的任务
     *  如果队列大小小于指定数量，则返回队列中所有任务
     * @param size
     * @return
     */
    List<TimeTaskContext> poll(int size);

    /**
     * 返回队列大小
     * @return
     */
    int size();

    // todo: 任务订阅

}
