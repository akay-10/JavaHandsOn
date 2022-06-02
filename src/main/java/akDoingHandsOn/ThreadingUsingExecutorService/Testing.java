package akDoingHandsOn.ThreadingUsingExecutorService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Testing {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadExecutor<Integer> threadExecutor = new ThreadExecutor<>();
        List<Task<Integer>> tasks = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            int finalI = i+1;
            Task<Integer> task = new Task<>(new ExecutorTask<Integer>() {
                @Override
                public Integer task() {
                    return finalI;
                }
            });
            tasks.add(task);
        }

        List<Future<Integer>> ans = threadExecutor.submit(tasks);

        List<Integer> toShow = new ArrayList<>();
        for (Future<Integer> ele: ans) {
            toShow.add(ele.get());
        }

        System.out.println(toShow);
        threadExecutor.shutdown();
    }
}
