package com.awstan.litepulse.timeTask;

/**
 * @Author pw7563
 * @Date 2025/1/10 13:32
 * usage
 */
public interface TimeTask {

    /**
     * 获取任务类型
     * @return
     */
    String getType();

    /**
     * 任务是否到达执行时间, 当前时间 >= 提交时间+延迟时间
     * @param context
     * @return
     */
    boolean isCanRunnable(TimeTaskContext context);

    /**
     * 重置任务，也就是把任务重新归队，用于当任务未到执行时间时
     * @param context
     */
    void resetTask(TimeTaskContext context);

    /**
     * 执行任务
     * @param context
     */
    void run(TimeTaskContext context);


}
