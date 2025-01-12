package com.awstan.timetaskutil.timeTask.properties;

import lombok.Data;

import java.io.Serializable;
/**
 * @Author pw7563
 * @Date 2025/1/10 15:24
 * usage
 */


/**
 * 时间任务的配置
 */
@Data
public class TimeTaskConfig implements Serializable {

    /**
     * 拉取任务列表为空时的间隔时间，单位毫秒
     */
    private int interval = 200;
    /**
     * 时间任务的并发度
     */
    private int concurrency = 3;
    /**
     * 每次拉取的任务数量
     */
    private int batchSize = 300;
}
