package org.ryderrobot.env;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Manages queue for transport.
 *
 * @param <T> what type of event, either Action or Observation
 */
public class Queue<T> {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition available = lock.newCondition();
    private final List<T> queue = new ArrayList<>();

    /**
     * places an event on the queue so that it can be consumed later.
     *
     * @param event to put on queue
     */
    public void push(T event) {
        try {
            lock.lock();
            queue.add(event);
        } finally {
            available.signalAll();
            lock.unlock();
        }
    }

    /**
     * removes the first element of the queue and returns it as Optional, note that
     * isEmpty() and isPresent() will set when return, and should be checked.
     *
     * @return first element on queue
     */
    public Optional<T> pop() {
        T event = null;
        if (!queue.isEmpty()) {
            lock.lock();
            event = queue.removeFirst();
            lock.unlock();
        }
        return Optional.ofNullable(event);
    }

    /**
     * how many elements are on the queue which have not been consumed yet.
     *
     * @return number of elements.
     */
    public int size() {
        return queue.size();
    }

    /**
     * used to block long-lived threads from executing until data is available.
     *
     * @return thread conditional.
     */
    public Condition available() {
        return available;
    }
}
