package akDoingHandsOn.ThreadingUsingExecutorService;

import lombok.Getter;
import lombok.NonNull;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static akDoingHandsOn.ThreadingUsingExecutorService.ThreadType.EXECUTOR_SERVICE_FIXED_THREAD_POOL;

public class ThreadExecutor<V> {
    @Getter
    private ThreadType threadType;
    @Getter
    private int nThreads;

    private ExecutorService executorService;

    public ThreadExecutor(ThreadType threadType, int nThreads){
        this.threadType = threadType;
        this.nThreads = nThreads;
        init();
    }

    private void init(){
        this.threadType = threadType!=null ? threadType : EXECUTOR_SERVICE_FIXED_THREAD_POOL;
        this.nThreads = nThreads>0 ? nThreads : 10;
        setExecutorService();
    }

    private void setExecutorService(){
        if(threadType == ThreadType.EXECUTOR_SERVICE_SINGLE_THREAD_POOL){
            executorService = Executors.newSingleThreadExecutor();
            this.nThreads = 1;
        }else {
            executorService = Executors.newFixedThreadPool(nThreads);
        }
    }

    public ThreadExecutor(ThreadType threadType){
        this.threadType = threadType;
        init();
    }

    public ThreadExecutor(){
        init();
    }

    public List<Future<V>> submit(List<Task<V>> tasks){
        List<Future<V>> futureList = new LinkedList<>();
        for(@NonNull Task task: tasks){
            futureList.add(executorService.submit(task));
        }
        return futureList;
    }

    public void shutdown(){
        executorService.shutdown();
        awaitExecution();
    }

    public boolean awaitExecution(){
        try {
            return executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e){
            System.err.println("Interrupted Exception during AwaitExecution for threading" + e.getMessage());
            Thread.currentThread().interrupt();
        }
        return false;
    }


    public List<Runnable> shutdownNow(){
        System.err.println("Executing Shutting Down Immediately");
        return executorService.shutdownNow();
    }
}
