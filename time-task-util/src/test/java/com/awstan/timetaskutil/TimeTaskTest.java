package com.awstan.timetaskutil;

import com.awstan.timetaskutil.timeTask.TimeTaskContext;
import com.awstan.timetaskutil.timeTask.TimeTaskExecutor;
import com.awstan.timetaskutil.timeTask.TimeTaskQueueType;
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
    public void submitTask() throws IOException {
        TimeTaskContext taskContext = new TimeTaskContext("测试任务",3000, TimeTaskQueueType.MEMORY,new HashMap<>(), ordinaryTask.getType(), 3,3,System.currentTimeMillis());
        timeTaskExecutor.submit(taskContext);

        System.in.read();

    }



}
