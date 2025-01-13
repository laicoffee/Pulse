package com.awstan.timetaskutil;

import com.awstan.timetaskutil.timeTask.TimeTaskContext;
import com.awstan.timetaskutil.timeTask.TimeTaskExecutor;
import com.awstan.timetaskutil.timeTask.TimeTaskQueueType;
import com.awstan.timetaskutil.timeTask.annotation.Description;
import com.awstan.timetaskutil.timeTask.timeTaskImpl.OrdinaryTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;

/**
 * @Author awstan
 * @Date 2025/1/12 15:45
 * usage
 */
@SpringBootTest(classes = TimeTaskUtilApplication.class)
public class TimeTaskTest {

    @Autowired
    private TimeTaskExecutor timeTaskExecutor;

    @Autowired
    private OrdinaryTask ordinaryTask;

    @Test
    @Description(description = "任务测试")
    public void submitTask() throws IOException {
        TimeTaskContext taskContext = new TimeTaskContext("测试任务",3000, TimeTaskQueueType.MEMORY,new HashMap<>(), ordinaryTask.getType(), 3,3,System.currentTimeMillis());
        timeTaskExecutor.submit(taskContext);

        System.in.read();

    }

    @Test
    @Description(description = "执行n次任务测试")
    public void submitCountTask() throws IOException {
        TimeTaskContext taskContext = new TimeTaskContext("测试任务",3000, TimeTaskQueueType.MEMORY,new HashMap<>(), ordinaryTask.getType(), 3,0,System.currentTimeMillis());
        timeTaskExecutor.submit(taskContext);

        System.in.read();


    }






}
