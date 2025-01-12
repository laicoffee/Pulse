package com.awstan.timetaskutil.timeTask;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author pw7563
 * @Date 2025/1/10 13:44
 * usage
 */
 @Data
public class TimeTaskContext implements Serializable {

    /**
     * 任务名称
     */
    private String name;

    /**
     * 延时时间，单位毫秒
     */
    private long delayTime;

    /**
     * 任务队列类型
     */
    private TimeTaskQueueType taskQueueType;

    /**
     * 任务参数
     */
    private Map<String, Object> taskparams = new HashMap<>();

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 执行次数
     */
    private int needExecuteCount;

    /**
     * 已经执行次数
     */
    private int executedCountNum;

    /**
     * 任务提交时间， 单位毫秒
     */
    private long submitTime;

    public TimeTaskContext(String name, long delayTime, TimeTaskQueueType taskQueueType, Map<String, Object> taskparams, String taskType, int needExecuteCount, int executedCountNum, long submitTime) {
        this.name = name;
        this.delayTime = delayTime;
        this.taskQueueType = taskQueueType;
        this.taskparams = taskparams;
        this.taskType = taskType;
        this.needExecuteCount = needExecuteCount;
        this.executedCountNum = executedCountNum;
        this.submitTime = submitTime;
    }

    public TimeTaskContext addTaskParam(String key, String value) {
        this.taskparams.put(key, value);
        return this;
    }

    /**
     * 任务是否执行完成
     * @return
     */
    public boolean isCompleted() {
        return executedCountNum >= needExecuteCount;
    }

    /**
     * 递增执行次数
     */
    public void incrementExecutedCount() {
        this.executedCountNum++;
    }

    // todo:构造内存方法

}
