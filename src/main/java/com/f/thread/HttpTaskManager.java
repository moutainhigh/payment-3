package com.f.thread;
/*
 *@ClassName HttpTaskManager
 *@Author 建国
 *@Date 1/16/19 4:27 PM
 */

import com.f.request.task.RequestTask;
import com.f.thread.schedule.ThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpTaskManager {

    public  static final HttpTaskManager instance = new HttpTaskManager();

    private static ExecutorService taskPoolExcutors;

    @Autowired
    private ThreadPoolManager threadPoolManager;

    //static  Map<Integer,? extends  RequestTask> processorMap = new HashMap<>();

    private HttpTaskManager(){
        initTaskPool();
    }

    static void initTaskPool(){
        taskPoolExcutors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }


    public static void destroyTaskPool(){
        try {
            if(!taskPoolExcutors.isShutdown())
                taskPoolExcutors.shutdown();
        } catch (Exception e){

        }
    }

    public static <T extends RequestTask> void addTask(T task){
        taskPoolExcutors.execute(task);
    }
}
