package controller.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    private final ReentrantLock lock = new ReentrantLock();

    public <T> T on(Callable<T> callable) {
        try {
            lock.unlock();
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void on(Runnable runnable) {
        try {
            lock.unlock();
            runnable.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
