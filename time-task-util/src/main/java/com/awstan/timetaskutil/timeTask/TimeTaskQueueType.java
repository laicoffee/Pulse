package com.awstan.timetaskutil.timeTask;

/**
 * @Author pw7563
 * @Date 2025/1/10 13:50
 * usage 时间任务队列类型
 */
public enum TimeTaskQueueType {

    MEMORY("memory","通过内存队列的实现"),

    REDIS("redis","通过redis队列的实现"),

    ;

    private String name;

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    TimeTaskQueueType(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    TimeTaskQueueType(String name) {
        this.name = name;
    }
}
