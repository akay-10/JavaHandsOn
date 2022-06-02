package akDoingHandsOn.ThreadingUsingExecutorService;

@FunctionalInterface
public interface ExecutorTask<V> {
    V task();
}
