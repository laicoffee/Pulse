package com.awstan.litepulse.timeTask;

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
    private int needExecuteCount = 1;

    /**
     * 已经执行次数
     */
    private int executedCountNum = 0;

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

    public TimeTaskContext(String name, long delayTime, TimeTaskQueueType taskQueueType, Map<String, Object> taskparams, String taskType) {
        new TimeTaskContext(name, delayTime, taskQueueType, taskparams, taskType, 1, 0, System.currentTimeMillis());
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


    /**
     * 增加任务参数, 如果任务参数已经存在，则会覆盖
     * @param key
     * @param value
     */
    public void addParam(String key, Object value){
        this.taskparams.put(key, value);
    }

    /**
     * 增加任务参数, 如果任务参数已经存在，则会覆盖
     * @param params
     */
    public void addParams(Map<String, Object> params){
        this.taskparams.putAll(params);
    }


    /**
     * 构造一个内存队列任务
     * @param name
     * @param delayTime
     * @param params
     * @param taskType
     * @return
     */
    public static TimeTaskContext buildMemoryQueueTask(String name, long delayTime, Map<String, Object> params, String taskType){
        return new TimeTaskContext(name,delayTime, TimeTaskQueueType.MEMORY, params, taskType);
    }



}
