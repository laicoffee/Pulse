package com.awstan.litepulse.timeTask.timeTaskImpl;

import com.awstan.litepulse.timeTask.TimeTask;
import com.awstan.litepulse.timeTask.TimeTaskContext;
import org.springframework.stereotype.Component;

/**
 * @Author awstan
 * @Date 2025/1/12 15:46
 * usage 具体任务的实现
 */
@Component
public class OrdinaryTask implements TimeTask {
    @Override
    public String getType() {
        return "ordinatyTask";
    }

    @Override
    public boolean isCanRunnable(TimeTaskContext context) {
        long submitTime = context.getSubmitTime();
        long delayTime = context.getDelayTime();
        return System.currentTimeMillis() - submitTime >= delayTime;
    }

    @Override
    public void resetTask(TimeTaskContext context) {

    }

    @Override
    public void run(TimeTaskContext context) {

        System.out.println("执行任务啦   " + System.currentTimeMillis() + "任务名称" + context.getName());

    }
}
