package it.cosenonjaviste.mvp.base;

import java.util.LinkedList;

/**
 * Created by fabiocollini on 26/09/14.
 */
public class ErrorsQueue {
    LinkedList<Throwable> queue = new LinkedList<>();

    public void add(Throwable throwable) {
        queue.add(throwable);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public Throwable removeFirst() {
        return queue.removeFirst();
    }
}
