package com.awstan.litepulse.timeTask;

import com.awstan.litepulse.timeTask.properties.TimeTaskConfig;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author pw7563
 * @Date 2025/1/10 15:16
 * usage
 */
@Component
public class TimeTaskExecutor implements InitializingBean, DisposableBean {

    /**
     * 存放定时任务具体实现的map
     */
    private final Map<String, TimeTask> timeTaskMap = new ConcurrentHashMap<>();

    /**
     * 存放定时任务队列的map
     */
    private final Map<TimeTaskQueueType, TimeTaskQueue> timeTaskQueueMap = new ConcurrentHashMap<>();

    private final Executor executor;

    private volatile boolean state = true;

    private final TimeTaskConfig timeTaskConfig = new TimeTaskConfig();


    public TimeTaskExecutor(@Autowired List<TimeTask> timeTasks,
                            @Autowired List<TimeTaskQueue> timeTaskQueues) {
        this.executor = new ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        timeTasks.forEach(this::addTimeTask);
        timeTaskQueues.forEach(this::addTimeTaskQueue);
    }

    public void addTimeTask(TimeTask timeTask) {
        String type = timeTask.getType();
        if(type == null || type.isEmpty()){
            throw new IllegalArgumentException("timeTask type can not be null or empty");
        }
        timeTaskMap.putIfAbsent(type, timeTask);
    }

    public void addTimeTaskQueue(TimeTaskQueue timeTaskQueue) {
        TimeTaskQueueType type = timeTaskQueue.getType();
        if(type == null){
            throw new IllegalArgumentException("timeTaskQueue type can not be null");
        }
        timeTaskQueueMap.putIfAbsent(type, timeTaskQueue);
    }

    @Override
    public void destroy() throws Exception {
        state = false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (TimeTaskQueueType taskQueueType : timeTaskQueueMap.keySet()) {
            for(int i=0; i<this.timeTaskConfig.getConcurrency(); i++){
                this.executor.execute(new DisPatcher(taskQueueType,i));
            }

        }
    }

    /**
     * 将任务提交到任务队列中
     * @param timeTaskContext
     */
    public void submit(TimeTaskContext timeTaskContext){
        TimeTaskQueueType taskQueueType = timeTaskContext.getTaskQueueType();
        TimeTaskQueue timeTaskQueue = timeTaskQueueMap.get(taskQueueType);
        if(timeTaskQueue == null){
            throw new IllegalArgumentException("timeTaskQueue not found for taskType: " + taskQueueType);
        }
        String taskType = timeTaskContext.getTaskType();
        TimeTask timeTask = timeTaskMap.get(taskType);
        if(timeTask == null){
            throw new IllegalArgumentException("timeTask not found for taskType: " + taskType);
        }
        timeTaskQueue.push(timeTaskContext);

    }

    class DisPatcher implements Runnable{

        private final TimeTaskQueueType taskQueueType;

        private final int index;

        public DisPatcher(TimeTaskQueueType taskQueueType, int index) {
            this.taskQueueType = taskQueueType;
            this.index = index;
        }

        @Override
        public void run() {
            if(!TimeTaskExecutor.this.state){
                // 停止状态，退出线程
                return;
            }
            try{
                ReentrantLock lock = new ReentrantLock();
                lock.lock();
                TimeTaskQueue timeTaskQueue = TimeTaskExecutor.this.timeTaskQueueMap.get(taskQueueType);
                List<TimeTaskContext> tasks = timeTaskQueue.poll(timeTaskConfig.getBatchSize());
                List<TimeTaskContext> rebackTasks = new ArrayList<>();
                System.out.println("线程池任务开始调度" + index);
                for(TimeTaskContext taskContext : tasks){

                    TimeTask timeTask = TimeTaskExecutor.this.timeTaskMap.get(taskContext.getTaskType());
                    if(timeTask == null){
                        throw new IllegalArgumentException("timeTask not found for taskType: " + taskContext.getTaskType());
                    }else {
                        // 实现任务执行count次
                        while(!taskContext.isCompleted()){
                            if(timeTask.isCanRunnable(taskContext)){
                                timeTask.run(taskContext);
                                taskContext.incrementExecutedCount();
                            }else{
                                rebackTasks.add(taskContext);
                            }
                        }
                    }
                }
                // 将不需要执行的任务重新入队
                rebackTasks.forEach(timeTaskQueue::push);
            }catch (Exception e){
                System.out.println("exception occurs during task execution");
            }finally {
                try {
                    // 这里的sleep只是用于测试，实际场景下不需要sleep
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                executor.execute(this);
            }
        }

        /**
         * 判断任务是否到达执行时间
         * @param taskContext
         * @return
         */
        public boolean isCanRun(TimeTaskContext taskContext){
            long submitTime = taskContext.getSubmitTime();
            long delayTime = taskContext.getDelayTime();
            return System.currentTimeMillis() - submitTime >= delayTime;
        }
    }
}
