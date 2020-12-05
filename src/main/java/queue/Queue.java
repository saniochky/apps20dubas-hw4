package queue;

import immutable.ImmutableLinkedList;

public class Queue {
    private ImmutableLinkedList queue = new ImmutableLinkedList();

    public Object peek() {
        if (this.queue.size() > 0) {
            return this.queue.getFirst();
        }

        return null;
    }

    public Object dequeue() {
        if (this.queue.size() > 0) {
            Object dequeued = this.peek();
            this.queue = this.queue.removeFirst();

            return dequeued;
        }

        return null;
    }

    public void enqueue(Object e) {
        this.queue = this.queue.add(e);
    }
}
