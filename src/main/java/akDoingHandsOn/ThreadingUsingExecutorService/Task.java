package akDoingHandsOn.ThreadingUsingExecutorService;

import lombok.AllArgsConstructor;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class Task<V> implements Callable<V> {
    private final ExecutorTask<V> executorTask;

    @Override
    public V call(){
        System.out.println("Executing the task: " + executorTask.task() + " in thread: " + Thread.currentThread().getId());
        return executorTask.task();
    }
}
