package com.awstan.timetaskutil.timeTask.timeTaskImpl;

import com.awstan.timetaskutil.timeTask.TimeTask;
import com.awstan.timetaskutil.timeTask.TimeTaskContext;
import org.springframework.stereotype.Component;

/**
 * @Author awstan
 * @Date 2025/1/12 15:46
 * usage
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

        System.out.println("执行任务啦   " + System.currentTimeMillis());

    }
}
