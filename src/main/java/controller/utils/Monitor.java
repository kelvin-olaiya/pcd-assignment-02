package controller.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    private final ReentrantLock lock = new ReentrantLock();

    public <T> T on(Callable<T> callable) {
        lock.lock();
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void on(Runnable runnable) {
        try {
            lock.lock();
            runnable.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
